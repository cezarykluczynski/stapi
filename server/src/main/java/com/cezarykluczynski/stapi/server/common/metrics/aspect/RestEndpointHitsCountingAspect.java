package com.cezarykluczynski.stapi.server.common.metrics.aspect;

import com.cezarykluczynski.stapi.server.common.metrics.filter.RestEndpointApiBrowserFilter;
import com.cezarykluczynski.stapi.server.common.metrics.service.EndpointHitsCountingService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class RestEndpointHitsCountingAspect {

	private final EndpointHitsCountingService endpointHitsCountingService;

	private final RestEndpointApiBrowserFilter restEndpointApiBrowserFilter;

	public RestEndpointHitsCountingAspect(EndpointHitsCountingService endpointHitsCountingService,
			RestEndpointApiBrowserFilter restEndpointApiBrowserFilter) {
		this.endpointHitsCountingService = endpointHitsCountingService;
		this.restEndpointApiBrowserFilter = restEndpointApiBrowserFilter;
	}

	@Before("execution(* com.cezarykluczynski.stapi.server..endpoint.*RestEndpoint.*(..))")
	public void restEndpointAdvice(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		endpointHitsCountingService.recordEndpointHit(signature.getDeclaringType().getSimpleName(), signature.getName(),
				restEndpointApiBrowserFilter.isApiBrowser());
	}

}
