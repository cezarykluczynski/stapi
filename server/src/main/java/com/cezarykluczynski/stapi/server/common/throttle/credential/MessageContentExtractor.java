package com.cezarykluczynski.stapi.server.common.throttle.credential;

import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Service
class MessageContentExtractor {

	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(MessageContentExtractor.class);

	String extract(Message message) {
		InputStream inputStream = message.getContent(InputStream.class);
		String content = fromInputStream(inputStream);
		message.setContent(InputStream.class, toInputStream(content));
		return content;
	}

	private Object toInputStream(String content) {
		return new ByteArrayInputStream(CHARSET.encode(content).array());
	}

	private String fromInputStream(InputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			FileCopyUtils.copy(inputStream, byteArrayOutputStream);
		} catch (IOException e) {
			LOG.error("Could not copy input stream {}", inputStream);
		}
		return new String(byteArrayOutputStream.toByteArray(), CHARSET);
	}

}
