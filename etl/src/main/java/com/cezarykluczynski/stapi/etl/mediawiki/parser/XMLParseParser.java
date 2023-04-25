package com.cezarykluczynski.stapi.etl.mediawiki.parser;

import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import info.bliki.api.AbstractXMLParser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
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
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@SuppressWarnings("ClassFanOutComplexity")
public class XMLParseParser extends AbstractXMLParser {
	// often no byte offset due to embedded templates or pages
	private static final List<String> IGNORABLE_SECTIONS_NO_BYTE_OFFSET = Lists.newArrayList(
			"Starring", "Also_starring", "Also_Starring", "Opening_credits", "Closing_Credits", "Closing_credits", "Cast", "Crew", "Second_Unit",
			"Songs"
	);
	private static final String API = "api";
	private static final String PARSE = "parse";
	private static final String WIKITEXT = "wikitext";
	private static final String TEXT = "text";
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

	private String htmlContext;

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
	@SuppressWarnings({"NPathComplexity"})
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
				String anchor = pageSection.getAnchor();
				if (!IGNORABLE_SECTIONS_NO_BYTE_OFFSET.contains(anchor)) {
					log.warn("Page \"{}\" section \"{}\" does not have byte offset specified", page.getTitle(), anchor);
				}
			}
			pageSection.setLevel(Integer.valueOf(attributes.getValue(LEVEL)));
			pageSection.setNumber(attributes.getValue(NUMBER));
			pageSection.setText(attributes.getValue(LINE));
			page.getSections().add(pageSection);
		}
	}

	@Override
	public void endDocument() {
		if (page == null) {
			return;
		}

		if (StringUtils.isNotEmpty(xmlTextContent)) {
			page.setTemplates(jsonTemplateParser.parse(xmlTextContent));
		}

		if (StringUtils.isNotEmpty(htmlContext)) {
			page.setHtmlDocument(Jsoup.parse(htmlContext));
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
		mayBeSetXmlAndHtmlTextContent(xml, factory);
	}

	private void mayBeSetXmlAndHtmlTextContent(String xml, DocumentBuilderFactory factory) {
		DocumentBuilder builder;

		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			String textContent = getTextFrom(doc, xpath, "/api/parse/parsetree/text()");
			String htmlContent = getTextFrom(doc, xpath, "/api/parse/text/text()");
			if (StringUtils.isNotBlank(textContent)) {
				xmlTextContent = textContent;
			}
			if (StringUtils.isNotBlank(htmlContent)) {
				this.htmlContext = htmlContent;
			}
		} catch (ParserConfigurationException | SAXException | IOException | XPathException e) {
			// do nothing
		}
	}

	private String getTextFrom(Document doc, XPath xpath, String expression) throws XPathExpressionException {
		XPathExpression expr = xpath.compile(expression);
		String textContent = (String) expr.evaluate(doc, XPathConstants.STRING);
		return textContent;
	}

}
