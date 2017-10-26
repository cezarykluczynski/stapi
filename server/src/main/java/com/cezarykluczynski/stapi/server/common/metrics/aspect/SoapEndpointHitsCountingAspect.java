package com.cezarykluczynski.stapi.server.common.metrics.aspect;

import com.cezarykluczynski.stapi.server.common.metrics.service.EndpointHitsCountingService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Aspect
@Service
@Slf4j
public class SoapEndpointHitsCountingAspect {

	private final EndpointHitsCountingService endpointHitsCountingService;

	public SoapEndpointHitsCountingAspect(EndpointHitsCountingService endpointHitsCountingService) {
		this.endpointHitsCountingService = endpointHitsCountingService;
	}

	@Before("execution(* com.cezarykluczynski.stapi.server..endpoint.*SoapEndpoint.*(..))")
	public void soapEndpointAdvice(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		endpointHitsCountingService.recordEndpointHit(signature.getDeclaringType().getSimpleName(), signature.getName());
	}

}
