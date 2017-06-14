package com.cezarykluczynski.stapi.server.configuration.interceptor;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.API_THROTTLE_NOT)
public class ApiThrottleLimitHeadersBindingInterceptorNoopImpl extends AbstractPhaseInterceptor<Message>
		implements ApiThrottleLimitHeadersBindingInterceptor {

	public ApiThrottleLimitHeadersBindingInterceptorNoopImpl() {
		super(Phase.PRE_STREAM);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		// do nothing
	}

	@Override
	public void handleFault(Message message) {
		// do nothing
	}

}
