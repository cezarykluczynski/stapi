package com.cezarykluczynski.stapi.server.common.throttle;

import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;

@Service
public class ThrottleValidator {

	ThrottleResult validate(Message message) {
		// TODO
		ThrottleResult throttleResult = new ThrottleResult();
		return throttleResult;
	}

}
