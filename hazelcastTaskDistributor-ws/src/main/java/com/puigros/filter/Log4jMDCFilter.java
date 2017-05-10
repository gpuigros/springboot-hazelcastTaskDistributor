package com.puigros.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * <p>
 * This class generates an UUID clave "Log4UUIDFilter.UUID" and saves it into org.slf4j.MDC for every service request.
 * <p>
 * This class extends from @see
 * <a href="https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/filter/OncePerRequestFilter.html">
 * </a>
 * <p>
 *
 * @author
 * @see
 * @since JDK1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component(value = "Log4UUIDFilter")
@Log4j2
public class Log4jMDCFilter extends OncePerRequestFilter {

    public static final String MDC_HTTP_SESSIONID = "httpsessionid";
    public static final String MDC_X_B3_TRACEID = "x-b3-traceid";
    public static final String MDC_QUERY_STRING = "querystring";

    @Autowired
    private SpanAccessor spanAccessor;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
        throws java.io.IOException,
        ServletException {
        try {
            Span span = null;
            if (spanAccessor != null && spanAccessor.isTracing()) {
                span = spanAccessor.getCurrentSpan();
            }
            String path = request.getMethod() + " - " + getURL(request);
            MDC.put(MDC_QUERY_STRING, path);


            HttpSession ses = request.getSession();
            MDC.put(MDC_HTTP_SESSIONID, ses.getId());

            if (span != null) {
                span = spanAccessor.getCurrentSpan();
                String traceId = Span.idToHex(span.getTraceId());
                MDC.put(MDC_X_B3_TRACEID, traceId);
                response.setHeader("X-TraceId", traceId);
            }

            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_QUERY_STRING);
            MDC.remove(MDC_HTTP_SESSIONID);
            MDC.remove(MDC_X_B3_TRACEID);
        }
    }

    private static String getURL(HttpServletRequest request) {
        String queryString = request.getQueryString();
        String ret = "";
        if (StringUtils.isBlank(queryString)) {
            ret = request.getRequestURL().toString();
        } else {
            ret = request.getRequestURL().toString() + "?" + queryString;
        }

        if (ret.contains("/hazelcastTaskDistributor/1.0")) {
            ret = ret.split("/hazelcastTaskDistributor/1.0")[1];
        }
        return ret;
    }

    @Override
    protected boolean isAsyncDispatch(final HttpServletRequest request) {
        return false;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
