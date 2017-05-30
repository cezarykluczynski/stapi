package com.cezarykluczynski.stapi.server.common.documentation.service;

public interface DocumentationDirectoryProvider {

	String getSwaggerDirectory();

	String getWsdlDirectory();

	String getTemporaryDirectory();

}
