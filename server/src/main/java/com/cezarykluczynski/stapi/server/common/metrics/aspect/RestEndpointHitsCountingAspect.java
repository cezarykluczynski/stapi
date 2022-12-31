package com.cezarykluczynski.stapi.server.common.metrics.aspect;

import com.cezarykluczynski.stapi.server.common.metrics.service.EndpointHitsCountingService;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Aspect
@Service
@Profile(SpringProfile.HITS)
public class RestEndpointHitsCountingAspect {

	private final EndpointHitsCountingService endpointHitsCountingService;

	public RestEndpointHitsCountingAspect(EndpointHitsCountingService endpointHitsCountingService) {
		this.endpointHitsCountingService = endpointHitsCountingService;
	}

	@Before("execution(* com.cezarykluczynski.stapi.server..endpoint.*RestEndpoint.*(..))")
	public void restEndpointAdvice(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		endpointHitsCountingService.recordEndpointHit(signature.getDeclaringType().getSimpleName(), signature.getName());
	}

}
