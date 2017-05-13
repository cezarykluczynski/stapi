package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiRequestModelDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.ws.rs.Produces;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApiRequestModelProvider {

	private final ApplicationContext applicationContext;

	public ApiRequestModelProvider(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApiRequestModelDTO provide() {
		Map<String, Object> restEndpoints = applicationContext.getBeansWithAnnotation(Produces.class);
		Map<String, Object> soapEndpoints = applicationContext.getBeansWithAnnotation(WebService.class);

		ApiRequestModelDTO apiRequestModelDTO = new ApiRequestModelDTO();
		apiRequestModelDTO.setRestRequests(getClasses(restEndpoints));
		apiRequestModelDTO.setSoapRequests(getClasses(soapEndpoints));

		return apiRequestModelDTO;
	}

	private Set<Class> getClasses(Map<String, Object> endpoints) {
		return endpoints.entrySet().stream()
				.map(this::extractTargetClass)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

	private Class extractTargetClass(Map.Entry<String, Object> entry) {
		Class proxyClass = entry.getValue().getClass();
		String className = StringUtils.substringBefore(proxyClass.getCanonicalName(), "$$");
		if (!className.endsWith("RestEndpoint") && !className.endsWith("SoapEndpoint") || className.contains("Common")) {
			return null;
		}

		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

}
