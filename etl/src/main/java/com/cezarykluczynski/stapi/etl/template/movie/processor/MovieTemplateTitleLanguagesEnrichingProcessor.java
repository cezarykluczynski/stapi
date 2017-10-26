package com.cezarykluczynski.stapi.etl.template.movie.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieTemplateTitleLanguagesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, MovieTemplate>> {

	private final WikitextApi wikitextApi;

	private final PageSectionExtractor pageSectionExtractor;

	public MovieTemplateTitleLanguagesEnrichingProcessor(WikitextApi wikitextApi,
			PageSectionExtractor pageSectionExtractor) {
		this.wikitextApi = wikitextApi;
		this.pageSectionExtractor = pageSectionExtractor;
	}

	@Override
	public void enrich(EnrichablePair<Page, MovieTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		MovieTemplate movieTemplate = enrichablePair.getOutput();

		PageSection externalLinksPageSection = pageSectionExtractor.extractLastPageSection(page);

		if (externalLinksPageSection == null) {
			return;
		}

		List<String> pageLinkList = wikitextApi.getPageTitlesFromWikitext(externalLinksPageSection.getWikitext());

		pageLinkList.forEach(pageLink -> {
			String titleCandidate = pageLink.length() > 2 ? TitleUtil.getNameFromTitle(pageLink.substring(3)) : null;

			if (StringUtils.startsWith(pageLink, "bg:")) {
				movieTemplate.setTitleBulgarian(titleCandidate);
			} else if (StringUtils.startsWith(pageLink, "ca:")) {
				movieTemplate.setTitleCatalan(titleCandidate);
			} else if (StringUtils.startsWith(pageLink, "zh-cn:")) {
				movieTemplate.setTitleChineseTraditional(TitleUtil.getNameFromTitle(pageLink.substring(6)));
			} else if (StringUtils.startsWith(pageLink, "de:")) {
				movieTemplate.setTitleGerman(titleCandidate);
			} else if (StringUtils.startsWith(pageLink, "it:")) {
				movieTemplate.setTitleItalian(titleCandidate);
			} else if (StringUtils.startsWith(pageLink, "ja:")) {
				movieTemplate.setTitleJapanese(titleCandidate);
			} else if (StringUtils.startsWith(pageLink, "pl:")) {
				movieTemplate.setTitlePolish(titleCandidate);
			} else if (StringUtils.startsWith(pageLink, "ru:")) {
				movieTemplate.setTitleRussian(titleCandidate);
			} else if (StringUtils.startsWith(pageLink, "sr:")) {
				movieTemplate.setTitleSerbian(titleCandidate);
			} else if (StringUtils.startsWith(pageLink, "es:")) {
				movieTemplate.setTitleSpanish(titleCandidate);
			}
		});
	}
}
