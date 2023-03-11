package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.DOCKER_NOT)
public class LocalDocumentationDirectoryProvider implements DocumentationDirectoryProvider {

	private static final String SWAGGER_DIRECTORY = "./contract/src/main/resources/swagger";
	private static final String BUILD_DIRECTORY = "./build/";

	@Override
	public String getSwaggerDirectory() {
		return SWAGGER_DIRECTORY;
	}

	@Override
	public String getTemporaryDirectory() {
		return BUILD_DIRECTORY;
	}

}
