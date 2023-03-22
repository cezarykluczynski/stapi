package com.cezarykluczynski.stapi.server.common.documentation.service;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DocumentationProvider {

	public Response provideFile(String path, String name) {
		File file = new File(path);
		return toResponse(file, name);
	}

	public Response provideFile(ClassPathResource resource, String name) {
		try {
			return toResponse(resource.getInputStream(), name);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("MultipleStringLiterals")
	public Response provideStapiYaml() {
		try {
			return Response.ok(new ClassPathResource("openapi/stapi.yaml").getInputStream())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "*")
					.header("Access-Control-Allow-Methods", "GET")
					.header("Access-Control-Allow-Credentials", "true")
					.header("Content-Type", "text/plain") // "text/yaml" or "text/vnd.yaml" makes Firefox download the file
					.header("Content-Disposition", "inline; filename=stapi.yaml")
					.build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Response toResponse(Object entity, String fileName) {
		return Response.ok(entity, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
				.header("Connection", "Close")
				.build();
	}

}
