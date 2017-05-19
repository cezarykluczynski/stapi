package com.cezarykluczynski.stapi.server.configuration.interceptor;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptor;

public interface ApiThrottleLimitHeadersBindingInterceptor extends PhaseInterceptor<Message> {
}
