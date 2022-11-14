package com.cezarykluczynski.stapi.sources.mediawiki.service.complement;

import com.cezarykluczynski.stapi.sources.mediawiki.api.ParseApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.JsonTemplateParser;
import com.cezarykluczynski.stapi.sources.mediawiki.service.fandom.FandomWikisDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ParseComplementingService {

	private final FandomWikisDetector fandomWikisDetector;

	private final ParseApi parseApi;

	private final JsonTemplateParser jsonTemplateParser;

	ParseComplementingService(FandomWikisDetector fandomWikisDetector, ParseApi parseApi, JsonTemplateParser jsonTemplateParser) {
		this.fandomWikisDetector = fandomWikisDetector;
		this.parseApi = parseApi;
		this.jsonTemplateParser = jsonTemplateParser;
	}

	public void complement(Page page) {
		if (fandomWikisDetector.isFandomWiki(page.getMediaWikiSource())) {
			String xmlParseTreeContent = parseApi.parseWikitextToXmlTree(page.getWikitext());
			if (xmlParseTreeContent == null) {
				log.warn("Could not extract XML tree from wikitext of page {}, skipping templates parsing", page.getTitle());
			} else {
				page.setTemplates(jsonTemplateParser.parse(xmlParseTreeContent));
			}
		}
	}

}
