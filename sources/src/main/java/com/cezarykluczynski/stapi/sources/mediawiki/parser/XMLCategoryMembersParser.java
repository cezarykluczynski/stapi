package com.cezarykluczynski.stapi.sources.mediawiki.parser;

import info.bliki.api.AbstractXMLParser;
import info.bliki.api.PageInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

// Copy of original class from bliki wiki with fixed "continue" tag
// TODO: remove entire class once this PR is merged and published in Maven Central
// https://bitbucket.org/axelclk/info.bliki.wiki/pull-requests/11/fix-for-xmlcategorymembersparser-continue/diff
public class XMLCategoryMembersParser extends AbstractXMLParser {
	private static final String CM_TAG = "cm";
	private static final String CONTINUE_TAG = "continue";
	private static final String CATEGORYMEMBERS_TAG = "categorymembers";
	private static final String CMCONTINUE_ID = "cmcontinue";

	private PageInfo fPage;

	private List<PageInfo> pagesList;

	private String cmContinue;

	public XMLCategoryMembersParser(String xmlText) throws SAXException {
		super(xmlText);
		this.pagesList = new ArrayList<>();
		this.cmContinue = "";
	}

	@Override
	public void endElement(String uri, String name, String qName) {
		try {
			if (CM_TAG.equals(qName)) {// ||
				// CATEGORY_ID.equals(qName))
				// {
				if (fPage != null) {
					pagesList.add(fPage);
				}
				// System.out.println(getString());
			}

			fData = null;
			fAttributes = null;

		} catch (RuntimeException re) {
			re.printStackTrace();
		}
	}

	/**
	 * @return the cmContinue
	 */
	public String getCmContinue() {
		if (cmContinue == null) {
			return "";
		}
		return cmContinue;
	}

	public List<PageInfo> getPagesList() {
		return pagesList;
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
		fAttributes = atts;

		if (CM_TAG.equals(qName)) {
			fPage = new PageInfo();
			fPage.setPageid(fAttributes.getValue(AbstractXMLParser.PAGE_ID));
			fPage.setNs(fAttributes.getValue(AbstractXMLParser.NS_ID));
			fPage.setTitle(fAttributes.getValue(AbstractXMLParser.TITLE_ID));
			// CONTINUE_TAG is for latest versions of MediaWiki, CATEGORYMEMBERS_TAG for older ones, like Wikia's wikis
		} else if (CONTINUE_TAG.equals(qName) || CATEGORYMEMBERS_TAG.equals(qName)) {
			String value = fAttributes.getValue(CMCONTINUE_ID);
			if (value != null) {
				cmContinue = value;
			}
		}
		fData = null;
	}

}
