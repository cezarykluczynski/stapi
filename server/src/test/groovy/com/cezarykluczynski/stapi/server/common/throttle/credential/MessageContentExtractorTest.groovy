package com.cezarykluczynski.stapi.server.common.throttle.credential

import org.apache.commons.io.IOUtils
import org.apache.cxf.message.Message
import org.apache.cxf.message.MessageImpl
import spock.lang.Specification

class MessageContentExtractorTest extends Specification {

	private static final String CONTENT = 'CONTENT'

	private MessageContentExtractor messageContentExtractor

	void setup() {
		messageContentExtractor = new MessageContentExtractor()
	}

	void "extracts content"() {
		given:
		Message message = new MessageImpl()
		InputStream inputStream = IOUtils.toInputStream(CONTENT, 'UTF-8')
		message.setContent(InputStream, inputStream)

		when:
		String content = messageContentExtractor.extract(message)

		then: 'content is extracted'
		content == CONTENT

		then: 'input stream still has content'
		message.getContent(InputStream).available() == CONTENT.length()
	}

}
