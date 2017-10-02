package com.cezarykluczynski.stapi.sources.mediawiki.parser;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import info.bliki.api.AbstractXMLParser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class XMLParseParser extends AbstractXMLParser {

	private static final String API = "api";
	private static final String PARSE = "parse";
	private static final String WIKITEXT = "wikitext";
	private static final String SECTIONS = "sections";
	private static final String CL = "cl";
	private static final String S = "s";
	private static final String TITLE = "title";
	private static final String PAGE_ID = "pageid";
	private static final String LEVEL = "level";
	private static final String LINE = "line";
	private static final String ANCHOR = "anchor";
	private static final String NUMBER = "number";
	private static final String BYTE_OFFSET = "byteoffset";

	@Getter
	private Page page;

	private boolean hasAPI;

	private boolean hasParse;

	private boolean isCL;

	private boolean isWikitext;

	private boolean isSections;

	private String xmlTextContent;

	private JsonTemplateParser jsonTemplateParser = new JsonTemplateParser();

	public XMLParseParser(String xmlText) throws SAXException {
		super(xmlText);
		setParsetreeXml(xmlText);
		try {
			parse();
		} catch (Exception e) {
			throw new StapiRuntimeException(e);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException {
		if (API.equals(qualifiedName)) {
			hasAPI = true;
		}

		if (hasAPI && PARSE.equals(qualifiedName)) {
			page = new Page();
			page.setTitle(attributes.getValue(TITLE));
			try {
				page.setPageId(Long.valueOf(attributes.getValue(PAGE_ID)));
			} catch (NumberFormatException e) {
				// do nothing
			}
			hasParse = true;
		}

		if (hasParse) {
			if (WIKITEXT.equals(qualifiedName)) {
				isWikitext = true;
			} else if (CL.equals(qualifiedName)) {
				isCL = true;
			} else if (SECTIONS.equals(qualifiedName)) {
				isSections = true;
			}
		}

		if (isSections && S.equals(qualifiedName)) {
			PageSection pageSection = new PageSection();
			pageSection.setAnchor(attributes.getValue(ANCHOR));
			try {
				pageSection.setByteOffset(Integer.valueOf(attributes.getValue(BYTE_OFFSET)));
			} catch (NumberFormatException e) {
				pageSection.setByteOffset(0);
				log.warn("Page \"{}\" section \"{}\" does not have byte offset specified", page.getTitle(), pageSection.getAnchor());
			}
			pageSection.setLevel(Integer.valueOf(attributes.getValue(LEVEL)));
			pageSection.setNumber(attributes.getValue(NUMBER));
			pageSection.setText(attributes.getValue(LINE));
			page.getSections().add(pageSection);
		}
	}

	@Override
	public void endDocument() {
		if (page == null || xmlTextContent == null) {
			return;
		}

		if (StringUtils.isNotEmpty(xmlTextContent)) {
			page.setTemplates(jsonTemplateParser.parse(xmlTextContent));
		}
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
	public void endElement(String uri, String localName, String qualifiedName) throws SAXException {
		isCL = false;
		isWikitext = false;
		if (SECTIONS.equals(qualifiedName)) {
			isSections = false;
		}
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
			// do nothing
		}
	}

}
