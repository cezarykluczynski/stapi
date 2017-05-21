package com.cezarykluczynski.stapi.server.common.documentation.service

import org.apache.commons.io.FileUtils
import spock.lang.Specification

class DocumentationZipperTest extends Specification {

	private static final String TEMPORARY_TARGET_FILE = 'build/tmp/DocumentationZipperTest_temp_file.zip'

	private DocumentationZipper documentationZipper

	void setup() {
		documentationZipper = new DocumentationZipper()
	}

	void "zips directory to file"() {
		given:
		FileUtils.deleteQuietly(new File(TEMPORARY_TARGET_FILE))
		String rootDirectory = '../contract/src/main/resources/v1/swagger/book'
		String contractDirectory = 'contract/src/main/resources/v1/swagger/book'
		String directory = new File(rootDirectory).isDirectory() ? rootDirectory : contractDirectory

		when:
		documentationZipper.zipDirectoryToFile(directory, new File(TEMPORARY_TARGET_FILE))

		then:
		new File(TEMPORARY_TARGET_FILE).size() > 3000

		cleanup:
		FileUtils.deleteQuietly(new File(TEMPORARY_TARGET_FILE))
	}

}
