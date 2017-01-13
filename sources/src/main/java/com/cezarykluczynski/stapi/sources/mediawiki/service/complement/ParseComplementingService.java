package com.cezarykluczynski.stapi.sources.mediawiki.service.complement;

import com.cezarykluczynski.stapi.sources.mediawiki.api.ParseApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.JsonTemplateParser;
import com.cezarykluczynski.stapi.sources.mediawiki.service.wikia.WikiaWikisDetector;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ParseComplementingService {

	private WikiaWikisDetector wikiaWikisDetector;

	private ParseApi parseApi;

	@Inject
	ParseComplementingService(WikiaWikisDetector wikiaWikisDetector, ParseApi parseApi) {
		this.wikiaWikisDetector = wikiaWikisDetector;
		this.parseApi = parseApi;
	}

	public void complement(Page page) {
		if (wikiaWikisDetector.isWikiaWiki(page.getMediaWikiSource())) {
			String xmlParseTreeContent = parseWikitextToXml(page.getWikitext());
			page.setTemplates(new JsonTemplateParser(xmlParseTreeContent).getTemplates());
		}
	}

	private String parseWikitextToXml(String wikitext) {
		return parseApi.parseWikitextToXmlTree(wikitext);
	}

}
