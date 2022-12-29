package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;

@Service
@Slf4j
public class DocumentationProvider {

	private static final String SWAGGER_ATTACHMENT_NAME = "stapi_swagger_specs.zip";
	private static final String WSDL_ATTACHMENT_NAME = "stapi_wsdl_contracts.zip";

	private final DocumentationReader documentationReader;

	private final DocumentationZipper documentationZipper;

	private final DocumentationDirectoryProvider documentationDirectoryProvider;

	private DocumentationDTO documentationDTO;

	public DocumentationProvider(DocumentationReader documentationReader, DocumentationZipper documentationZipper,
			DocumentationDirectoryProvider documentationDirectoryProvider) {
		this.documentationReader = documentationReader;
		this.documentationZipper = documentationZipper;
		this.documentationDirectoryProvider = documentationDirectoryProvider;
	}

	@SuppressFBWarnings("EI_EXPOSE_REP")
	public synchronized DocumentationDTO provideDocumentation() {
		if (documentationDTO == null) {
			documentationDTO = new DocumentationDTO();
			documentationDTO.setRestDocuments(documentationReader.readDirectory(documentationDirectoryProvider.getSwaggerDirectory()));
			documentationDTO.setSoapDocuments(documentationReader.readDirectory(documentationDirectoryProvider.getWsdlDirectory()));
		}

		return documentationDTO;
	}

	public Response provideRestSpecsZip() {
		File soapContractsZip = new File(documentationDirectoryProvider.getTemporaryDirectory() + SWAGGER_ATTACHMENT_NAME);
		return createFromDirectoryOrRead(soapContractsZip, documentationDirectoryProvider.getSwaggerDirectory(), SWAGGER_ATTACHMENT_NAME);
	}

	public Response provideSoapContractsZip() {
		File soapContractsZip = new File(documentationDirectoryProvider.getTemporaryDirectory() + WSDL_ATTACHMENT_NAME);
		return createFromDirectoryOrRead(soapContractsZip, documentationDirectoryProvider.getWsdlDirectory(), WSDL_ATTACHMENT_NAME);
	}

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

	private Response createFromDirectoryOrRead(File zip, String directory, String attachmentName) {
		if (!zip.exists()) {
			synchronized (this) {
				documentationZipper.zipDirectoryToFile(directory, zip);
			}
		}

		return toResponse(zip, attachmentName);
	}

	private Response toResponse(Object entity, String fileName) {
		return Response.ok(entity, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
				.header("Connection", "Close")
				.build();
	}

}
