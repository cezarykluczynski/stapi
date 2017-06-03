package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import org.apache.cxf.helpers.FileUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Profile(SpringProfile.WAR)
public class WarDocumentationDirectoryProvider implements DocumentationDirectoryProvider {

	private static final String SWAGGER_CLASSPATH_DIRECTORY = "/contract/src/main/resources/v1/swagger";

	private static final String WSDL_CLASSPATH_DIRECTORY = "/contract/src/main/resources/v1/wsdl";

	private static final String TMP_DIRECTORY = "/tmp/" + System.currentTimeMillis() + "/";

	@Override
	public String getSwaggerDirectory() {
		return getAbsoluteDirectoryFromClassPathDirectory(SWAGGER_CLASSPATH_DIRECTORY);
	}

	@Override
	public String getWsdlDirectory() {
		return getAbsoluteDirectoryFromClassPathDirectory(WSDL_CLASSPATH_DIRECTORY);
	}

	private String getAbsoluteDirectoryFromClassPathDirectory(String classPathDirectory) {
		try {
			return new File(getClass().getResource(classPathDirectory).toURI()).getAbsolutePath();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getTemporaryDirectory() {
		FileUtils.mkDir(new File(TMP_DIRECTORY));
		return TMP_DIRECTORY;
	}

}
