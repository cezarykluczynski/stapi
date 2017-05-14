package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.google.common.collect.Lists;

import java.util.List;

public class Constants {

	public static final String BASE_REQUEST_SUFFIX = "BaseRequest";
	public static final String FULL_REQUEST_SUFFIX = "FullRequest";
	public static final String BASE_ENTITY_SUFFIX = "Base";
	public static final String FULL_ENTITY_SUFFIX = "Full";
	public static final String BASE_RESPONSE_SUFFIX = "BaseResponse";
	public static final String FULL_RESPONSE_SUFFIX = "FullResponse";
	public static final String REST_ENDPOINT_SUFFIX = "RestEndpoint";
	public static final String SOAP_ENDPOINT_SUFFIX = "SoapEndpoint";
	public static final String API_SUFFIX = "Api";
	public static final List<String> REQUEST_SUFFIXES = Lists.newArrayList(BASE_REQUEST_SUFFIX, FULL_REQUEST_SUFFIX, API_SUFFIX);
	public static final List<String> ENTITY_SUFFIXES = Lists.newArrayList(BASE_ENTITY_SUFFIX, FULL_ENTITY_SUFFIX);
	public static final List<String> RESPONSE_SUFFIXES = Lists.newArrayList(BASE_RESPONSE_SUFFIX, FULL_RESPONSE_SUFFIX);
	public static final List<String> ENDPOINT_SUFFIXES = Lists.newArrayList(REST_ENDPOINT_SUFFIX, SOAP_ENDPOINT_SUFFIX);

}
