package com.cezarykluczynski.stapi.server.common.documentation.service;

import com.cezarykluczynski.stapi.contract.documentation.dto.DocumentationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Service
@Slf4j
public class DocumentationProvider {

	private static final String ZIP_TARGET_DIRECTORY = "./build/";
	private static final String SWAGGER_ATTACHMENT_NAME = "stapi_swagger_specs.zip";
	private static final String SWAGGER_ZIP_TARGET_FILE = ZIP_TARGET_DIRECTORY + SWAGGER_ATTACHMENT_NAME;
	private static final String WSDL_ATTACHMENT_NAME = "stapi_wsdl_contracts.zip";
	private static final String WSDL_ZIP_TARGET_FILE = ZIP_TARGET_DIRECTORY + WSDL_ATTACHMENT_NAME;

	private final DocumentationReader documentationReader;

	private final DocumentationZipper documentationZipper;

	private final DocumentationDirectoryProvider documentationDirectoryProvider;

	private DocumentationDTO documentationDTO;

	@Inject
	public DocumentationProvider(DocumentationReader documentationReader, DocumentationZipper documentationZipper,
			DocumentationDirectoryProvider documentationDirectoryProvider) {
		this.documentationReader = documentationReader;
		this.documentationZipper = documentationZipper;
		this.documentationDirectoryProvider = documentationDirectoryProvider;
	}

	public DocumentationDTO provideDocumentation() {
		if (documentationDTO == null) {
			synchronized (this) {
				if (documentationDTO == null) {
					documentationDTO = new DocumentationDTO();
					documentationDTO.setRestDocuments(documentationReader.readDirectory(documentationDirectoryProvider.getSwaggerDirectory()));
					documentationDTO.setSoapDocuments(documentationReader.readDirectory(documentationDirectoryProvider.getWsdlDirectory()));
				}
			}
		}

		return documentationDTO;
	}

	public Response provideRestSpecsZip() {
		File soapContractsZip = new File(SWAGGER_ZIP_TARGET_FILE);
		return createFromDirectoryOrRead(soapContractsZip, documentationDirectoryProvider.getSwaggerDirectory(), SWAGGER_ATTACHMENT_NAME);
	}

	public Response provideSoapContractsZip() {
		File soapContractsZip = new File(WSDL_ZIP_TARGET_FILE);
		return createFromDirectoryOrRead(soapContractsZip, documentationDirectoryProvider.getWsdlDirectory(), WSDL_ATTACHMENT_NAME);
	}

	private Response createFromDirectoryOrRead(File zip, String directory, String attachmentName) {
		if (!zip.exists()) {
			synchronized (this) {
				documentationZipper.zipDirectoryToFile(directory, zip);
			}
		}

		return Response.ok(zip, MediaType.APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=\"" + attachmentName + "\"")
				.header("Connection", "Close")
				.build();
	}

}
