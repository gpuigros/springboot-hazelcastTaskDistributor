package com.puigros.logging;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.stereotype.Component;

/**
 * Created by guillem.puigros on 23/09/2016.
 */
@Component
public class MDCInjector {
    public static final String MDC_HTTP_SESSIONID = "httpsessionid";
    public static final String MDC_X_B3_TRACEID = "x-b3-traceid";
    public static final String MDC_QUERY_STRING = "querystring";


    @Autowired
    private SpanAccessor spanAccessor;

    /**
     * setMDC
     */

    public void setMDC() {
        Span span=null;
        if (spanAccessor != null && spanAccessor.isTracing()) {
            span = spanAccessor.getCurrentSpan();
            String traceId = Span.idToHex(span.getTraceId());
            MDC.put(MDC_X_B3_TRACEID, traceId);

        }
    }
}
