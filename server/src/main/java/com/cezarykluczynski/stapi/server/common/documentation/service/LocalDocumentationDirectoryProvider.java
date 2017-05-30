package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.WAR_NOT)
public class LocalDocumentationDirectoryProvider implements DocumentationDirectoryProvider {

	private static final String SWAGGER_DIRECTORY = "./contract/src/main/resources/v1/swagger";
	private static final String WSDL_DIRECTORY = "./contract/src/main/resources/v1/wsdl";
	private static final String BUILD_DIRECTORY = "./build/";

	@Override
	public String getSwaggerDirectory() {
		return SWAGGER_DIRECTORY;
	}

	@Override
	public String getWsdlDirectory() {
		return WSDL_DIRECTORY;
	}

	@Override
	public String getTemporaryDirectory() {
		return BUILD_DIRECTORY;
	}

}
