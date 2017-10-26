package com.cezarykluczynski.stapi.server.configuration.interceptor;

import com.cezarykluczynski.stapi.model.throttle.dto.ThrottleStatistics;
import com.cezarykluczynski.stapi.server.common.throttle.RequestSpecificThrottleStatistics;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Profile(SpringProfile.API_THROTTLE)
public class ApiThrottleLimitHeadersBindingInterceptorImpl extends AbstractPhaseInterceptor<Message>
		implements ApiThrottleLimitHeadersBindingInterceptor {

	private static final String X_THROTTLE_LIMIT_TOTAL = "X-Throttle-Limit-Total";
	private static final String X_THROTTLE_LIMIT_REMAINING = "X-Throttle-Limit-Remaining";

	private final RequestSpecificThrottleStatistics requestSpecificThrottleStatistics;

	public ApiThrottleLimitHeadersBindingInterceptorImpl(RequestSpecificThrottleStatistics requestSpecificThrottleStatistics) {
		super(Phase.PRE_STREAM);
		this.requestSpecificThrottleStatistics = requestSpecificThrottleStatistics;
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		addHeaders(message);
	}

	@Override
	public void handleFault(Message message) {
		addHeaders(message);
	}

	private void addHeaders(Message message) {
		ThrottleStatistics throttleStatistics = requestSpecificThrottleStatistics.getThrottleStatistics();

		if (throttleStatistics == null) {
			return;
		}

		Map<String, List<String>> headers = Maps.newHashMap();
		headers.put(X_THROTTLE_LIMIT_TOTAL, Lists.newArrayList(String.valueOf(throttleStatistics.getTotal())));
		headers.put(X_THROTTLE_LIMIT_REMAINING, Lists.newArrayList(String.valueOf(throttleStatistics.getRemaining())));
		message.put(Message.PROTOCOL_HEADERS, headers);
	}

}
