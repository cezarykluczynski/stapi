package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EpisodeTemplateTitleLanguagesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, EpisodeTemplate>> {

	private static final String[] JAPANESE_SERIES_PREFIXES = {"ENT:", "TNG:", "DS9:", "VOY:", "TAS:", "TOS:"};

	private final WikitextApi wikitextApi;

	private final PageSectionExtractor pageSectionExtractor;

	public EpisodeTemplateTitleLanguagesEnrichingProcessor(WikitextApi wikitextApi,
			PageSectionExtractor pageSectionExtractor) {
		this.wikitextApi = wikitextApi;
		this.pageSectionExtractor = pageSectionExtractor;
	}

	@Override
	public void enrich(EnrichablePair<Page, EpisodeTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		EpisodeTemplate episodeTemplate = enrichablePair.getOutput();

		PageSection externalLinksPageSection = pageSectionExtractor.extractLastPageSection(page);

		if (externalLinksPageSection == null) {
			return;
		}

		List<String> pageLinkList = wikitextApi.getPageTitlesFromWikitext(externalLinksPageSection.getWikitext());

		pageLinkList.forEach(pageLink -> {
			if (StringUtils.startsWith(pageLink, "de:")) {
				episodeTemplate.setTitleGerman(TitleUtil.getNameFromTitle(pageLink.substring(3)));
			}

			if (StringUtils.startsWith(pageLink, "it:")) {
				episodeTemplate.setTitleItalian(TitleUtil.getNameFromTitle(pageLink.substring(3)));
			}

			if (pageLink.startsWith("ja:")) {
				String titleJapanese = TitleUtil.getNameFromTitle(pageLink.substring(3));

				if (StringUtils.startsWithAny(titleJapanese, JAPANESE_SERIES_PREFIXES)) {
					titleJapanese = titleJapanese.substring(4);
				}

				episodeTemplate.setTitleJapanese(titleJapanese);
			}
		});
	}
}
