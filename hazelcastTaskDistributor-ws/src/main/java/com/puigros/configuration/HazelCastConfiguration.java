package com.puigros.configuration;

import java.net.InetAddress;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.context.SpringManagedContext;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConfigurationProperties(prefix = "hazelcast")
@Slf4j
@Data
public class HazelCastConfiguration implements ApplicationContextAware, EnvironmentAware {
    public static final String HAZELCAST_INSTANCE = "INSTANCE_NAME";

    @Value("${hazelcast.port:5701}")
    private int port;

    private String clusterTop;
    @Value("${hazelcast.baseGroupName:example-group}")
    private String baseGroupName;

    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = HAZELCAST_INSTANCE)
    public HazelcastInstance buildHazelcast() {
        Config cfg = new XmlConfigBuilder().build();
        cfg.setProperty("hazelcast.logging.type", "slf4j");
        cfg.setInstanceName(HAZELCAST_INSTANCE);
        SpringManagedContext smc = new SpringManagedContext();
        smc.setApplicationContext(applicationContext);
        cfg.setManagedContext(smc);
        StringBuilder groupName = new StringBuilder(baseGroupName);
        boolean local = false;
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            groupName.append("-");
            groupName.append(activeProfiles[0].toUpperCase());
            local = Arrays.stream(activeProfiles).anyMatch(profile -> "LOCAL".equalsIgnoreCase(profile));
        }
        if (local) {
            try {
                final String hostname = InetAddress.getLocalHost().getHostName();
                groupName.append('-');
                groupName.append(hostname);
            } catch (final Exception e) {
                log.error("Local hostname could not be resolved ", e);
            }
        } else {
            NetworkConfig network = cfg.getNetworkConfig();
            JoinConfig join = network.getJoin();
            join.getMulticastConfig().setEnabled(true);

//Uncoment for tcpIpConfig as for Consul or Eureka known hosts

//            join.getMulticastConfig().setEnabled(false);
//            join.getAwsConfig().setEnabled(false);
//            log.info("Multicast disabled, joining cluster controlled by {}", clusterTop);
//            String address = "";
//            TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
//            tcpIpConfig.setEnabled(true);
//            try {
//                address = InetAddress.getLocalHost().getHostAddress();
//                log.info("Using public address {}", address);
//                network.setPublicAddress(address);
//                network.getInterfaces().setEnabled(true).addInterface(address);
//                tcpIpConfig.addMember(address);
//            } catch (final Exception e) {
//                log.error("Host address could not be resolved ", e);
//            }
//            if (StringUtils.isNotBlank(clusterTop)) {
//                for (String member : clusterTop.split(",")) {
//                    log.info("Connecting to {}:{}", member, port);
//                    tcpIpConfig.addMember(member + ":" + port);
//                }
//            }
        }
        log.info("Hazelcast joining group {}", groupName);
        cfg.getNetworkConfig().setPort(port);
        cfg.getGroupConfig().setName(groupName.toString());
        return Hazelcast.newHazelcastInstance(cfg);
    }
}
