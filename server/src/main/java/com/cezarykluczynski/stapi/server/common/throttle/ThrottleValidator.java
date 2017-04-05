package com.cezarykluczynski.stapi.server.common.throttle;

import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;

@Service
public class ThrottleValidator {

	public ThrottleResult isValid(Message message) {
		// TODO
		ThrottleResult throttleResult = new ThrottleResult();
		return throttleResult;
	}

}
