package com.cezarykluczynski.stapi.sources.mediawiki.parser;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import info.bliki.api.AbstractXMLParser;
import lombok.Getter;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XMLParseParser extends AbstractXMLParser {

	private static final String API = "api";
	private static final String PARSE = "parse";
	private static final String CATEGORIES = "categories";
	private static final String WIKITEXT = "wikitext";
	private static final String CL = "cl";
	private static final String TITLE = "title";
	private static final String PAGE_ID = "pageid";

	@Getter
	private Page page;

	private boolean hasAPI;

	private boolean hasParse;

	private boolean isCL;

	private boolean isWikitext;

	private String xmlTextContent;

	public XMLParseParser(String xmlText) throws SAXException {
		super(xmlText);
		setParsetreeXml(xmlText);
		try {
			parse();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (API.equals(qName)) {
			hasAPI = true;
		}

		if (hasAPI && PARSE.equals(qName)) {
			page = new Page();
			page.setTitle(attributes.getValue(TITLE));
			page.setPageId(Long.valueOf(attributes.getValue(PAGE_ID)));
			hasParse = true;
		}

		if (hasParse && CATEGORIES.equals(qName)) {
			page.setCategories(Lists.newArrayList());
		}

		if (hasParse && WIKITEXT.equals(qName)) {
			isWikitext = true;
		}

		if (hasParse && CL.equals(qName)) {
			isCL = true;
		}
	}

	@Override
	public void endDocument() {
		if (page == null || xmlTextContent == null) {
			return;
		}

		page.setTemplates(new JsonTemplateParser(xmlTextContent).getTemplates());
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (isCL) {
			fData = null;
			super.characters(ch, start, length);
			CategoryHeader categoryHeader = new CategoryHeader();
			categoryHeader.setTitle(fData.toString());
			page.getCategories().add(categoryHeader);
		}

		if (isWikitext) {
			fData = null;
			super.characters(ch, 0, length);
			String wikitext = page.getWikitext() == null ? "" : page.getWikitext();
			page.setWikitext(wikitext + fData.toString());
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		isCL = false;
		isWikitext = false;
	}

	private void setParsetreeXml(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		mayBeSetXmlTextContent(xml, factory);
	}

	private void mayBeSetXmlTextContent(String xml, DocumentBuilderFactory factory) {
		DocumentBuilder builder;

		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile("/api/parse/parsetree/text()");
			String textContent = (String) expr.evaluate(doc, XPathConstants.STRING);
			if (textContent != null) {
				xmlTextContent = textContent;
			}
		} catch (ParserConfigurationException | SAXException | IOException | XPathException e) {
		}
	}

}
