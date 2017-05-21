package com.cezarykluczynski.stapi.server.common.documentation.service;

import org.springframework.stereotype.Service;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

@Service
class DocumentationZipper {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DocumentationZipper.class);

	void zipDirectoryToFile(String directory, File zip) {
		if (!zip.exists()) {
			try {
				zip.createNewFile();
			} catch (Exception e) {
				LOG.error("Error creating file {}, error was: {}", zip, e);
				return;
			}
			ZipUtil.pack(new File(directory), zip);
		}
	}

}
