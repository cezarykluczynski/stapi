package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.EndpointDocumentationDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.EndpointMethodDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.SwaggerMethodType;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import liquibase.util.StringUtils;
import org.reflections.ReflectionUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
class RestApiEntitiesFactory {

	private static final String SEARCH_GET = "SearchGet";

	private final RestEndpointMethodFactory restEndpointMethodFactory;

	@Inject
	RestApiEntitiesFactory(RestEndpointMethodFactory restEndpointMethodFactory) {
		this.restEndpointMethodFactory = restEndpointMethodFactory;
	}

	public EndpointDocumentationDTO create(ApiEntitiesDTO apiEntitiesDTO) {
		String entityNameLowerCase = StringUtils.lowerCaseFirst(apiEntitiesDTO.getEntityName());

		Set<Method> methodSet = ReflectionUtils.getAllMethods(apiEntitiesDTO.getBaseRequest(),
						ReflectionUtils.withModifier(Modifier.PUBLIC), ReflectionUtils.withPrefix(entityNameLowerCase))
				.stream()
				.filter(method -> !method.getName().endsWith("WithHttpInfo") && !method.getName().endsWith("Async"))
				.collect(Collectors.toSet());

		Preconditions.checkArgument(methodSet.size() == 3, "There should be exactly 3 methods on a Swagger API service");

		EndpointMethodDTO getMethod = restEndpointMethodFactory
				.create(getMethodUsing(methodSet, this::isGetMethod), SwaggerMethodType.GET, apiEntitiesDTO);
		EndpointMethodDTO searchGetMethod = restEndpointMethodFactory
				.create(getMethodUsing(methodSet, this::isSearchGetMethod), SwaggerMethodType.SEARCH_GET, apiEntitiesDTO);
		EndpointMethodDTO searchPostMethod = restEndpointMethodFactory
				.create(getMethodUsing(methodSet, this::isSearchPostMethod), SwaggerMethodType.SEARCH_POST, apiEntitiesDTO);

		EndpointDocumentationDTO endpointDocumentationDTO = new EndpointDocumentationDTO();
		endpointDocumentationDTO.setEndpointMethods(Lists.newArrayList(getMethod, searchGetMethod, searchPostMethod));
		return endpointDocumentationDTO;
	}

	private Method getMethodUsing(Set<Method> methodSet, Predicate<Method> methodPredicate) {
		return methodSet.stream().filter(methodPredicate).findAny().get();
	}

	private boolean isGetMethod(Method method) {
		return method.getName().endsWith("Get") && !method.getName().endsWith(SEARCH_GET);
	}

	private boolean isSearchGetMethod(Method method) {
		return method.getName().endsWith(SEARCH_GET);
	}

	private boolean isSearchPostMethod(Method method) {
		return method.getName().endsWith("SearchPost");
	}

}
