package com.cezarykluczynski.stapi.server.common.documentation.service

import org.apache.commons.io.FileUtils
import spock.lang.Requires
import spock.lang.Specification

@Requires({
	new File(ROOT_DIRECTORY).exists() || new File(CONTRACT_DIRECTORY).exists()
})
class DocumentationZipperTest extends Specification {

	private static final String ROOT_DIRECTORY = '../contract/src/main/resources/v1/swagger/book'
	private static final String CONTRACT_DIRECTORY = 'contract/src/main/resources/v1/swagger/book'
	private static final String TEMPORARY_TARGET_FILE = 'build/tmp/DocumentationZipperTest_temp_file.zip'

	private DocumentationZipper documentationZipper

	void setup() {
		documentationZipper = new DocumentationZipper()
	}

	void "zips directory to file"() {
		given:
		File temporaryTargetFile = new File(TEMPORARY_TARGET_FILE)
		FileUtils.deleteQuietly(temporaryTargetFile)
		temporaryTargetFile.parentFile.mkdirs()
		String directory = new File(ROOT_DIRECTORY).isDirectory() ? ROOT_DIRECTORY : CONTRACT_DIRECTORY

		when:
		documentationZipper.zipDirectoryToFile(directory, new File(TEMPORARY_TARGET_FILE))

		then:
		new File(TEMPORARY_TARGET_FILE).size() > 3000

		cleanup:
		FileUtils.deleteQuietly(new File(TEMPORARY_TARGET_FILE))
	}

}
