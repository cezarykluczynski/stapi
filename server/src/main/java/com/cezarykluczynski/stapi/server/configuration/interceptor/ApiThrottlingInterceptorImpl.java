package com.cezarykluczynski.stapi.server.configuration.interceptor;

import com.cezarykluczynski.stapi.server.common.throttle.ThrottleFacade;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.API_THROTTLE)
public class ApiThrottlingInterceptorImpl extends AbstractPhaseInterceptor<Message> implements ApiThrottlingInterceptor {

	private final ThrottleFacade throttleFacade;

	public ApiThrottlingInterceptorImpl(ThrottleFacade throttleFacade) {
		super(Phase.RECEIVE);
		this.throttleFacade = throttleFacade;
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		throttleFacade.validate(message);
	}

}
