package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.apache.cxf.helpers.FileUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Profile(SpringProfile.DOCKER)
public class DockerDocumentationDirectoryProvider implements DocumentationDirectoryProvider {

	private static final String SWAGGER_DIRECTORY = "/contract/src/main/resources/v1/swagger";
	private static final String BUILD_DIRECTORY = "/tmp/" + System.currentTimeMillis() + "/";

	@Override
	public String getSwaggerDirectory() {
		return SWAGGER_DIRECTORY;
	}

	@Override
	public String getTemporaryDirectory() {
		FileUtils.mkDir(new File(BUILD_DIRECTORY));
		return BUILD_DIRECTORY;
	}

}
