package com.puigros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The main application class
 * @author crequena
 */
@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = { "com.puigros", "com.hotelbeds.architecture.systemmetrics" })
@EnableAutoConfiguration(exclude = { AopAutoConfiguration.class })
@EnableSwagger2
@SuppressWarnings("squid:S1118")
public class MainApplication {
    private static Class<MainApplication> mainApplicationClass = MainApplication.class;

    /**
     * The main method.
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(mainApplicationClass, args); //NOSONAR
    }

}
