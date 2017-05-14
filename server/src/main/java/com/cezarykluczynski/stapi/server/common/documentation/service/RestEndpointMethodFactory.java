package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.ApiEntitiesDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.EndpointMethodDTO;
import com.cezarykluczynski.stapi.contract.documentation.dto.enums.SwaggerMethodType;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class RestEndpointMethodFactory {

	EndpointMethodDTO create(Method method, SwaggerMethodType swaggerMethodType, ApiEntitiesDTO apiEntitiesDTO) {
		// TODO
		return null;
	}

}
