package com.cezarykluczynski.stapi.server.common.throttle.credential;

import liquibase.util.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Service
class SoapApiKeyExtractor {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(SoapApiKeyExtractor.class);

	String extract(String requestContent) {
		Document document = toDocument(requestContent);
		return tryExtract(document);
	}

	private static Document toDocument(String requestContent) {
		if (StringUtils.isEmpty(requestContent)) {
			return null;
		}

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(requestContent));
			document = builder.parse(is);
		} catch (Exception e) {
			LOG.error("Could not parse xml string {}, exception was: {}", requestContent, e);
		}

		return document;
	}

	private String tryExtract(Document document) {
		if (document == null) {
			return null;
		}

		NodeList nodeList = document.getElementsByTagName("*");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (isApiKeyNode(node)) {
				return extractApiKey(node);
			}
		}

		return null;
	}

	private boolean isApiKeyNode(Node node) {
		return node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().endsWith(RequestCredential.API_KEY);
	}

	private String extractApiKey(Node node) {
		Node firstChild = node.getFirstChild();
		return firstChild != null ? firstChild.getNodeValue() : null;
	}

}
