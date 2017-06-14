package com.cezarykluczynski.stapi.sources.mediawiki.parser;

import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import info.bliki.api.AbstractXMLParser;
import info.bliki.api.PageInfo;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLQueryParser extends AbstractXMLParser {

	private static final String API = "api";
	private static final String QUERY = "query";
	private static final String PAGES = "pages";
	private static final String PAGE = "page";
	private static final String PAGE_ID = "pageid";
	private static final String NS = "ns";
	private static final String TITLE = "title";

	@Getter
	private PageInfo pageInfo;

	private boolean hasAPI;
	private boolean hasQuery;
	private boolean hasPages;

	public XMLQueryParser(String xmlText) throws SAXException {
		super(xmlText);
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

		if (hasAPI && QUERY.equals(qualifiedName)) {
			hasQuery = true;
		}

		if (hasQuery && PAGES.equals(qualifiedName)) {
			hasPages = true;
		}

		if (hasPages && PAGE.equals(qualifiedName)) {
			pageInfo = new PageInfo();
			pageInfo.setTitle(attributes.getValue(TITLE));
			pageInfo.setNs(attributes.getValue(NS));
			try {
				pageInfo.setPageid(attributes.getValue(PAGE_ID));
			} catch (NumberFormatException e) {
				// do nothing
			}
		}
	}

}
