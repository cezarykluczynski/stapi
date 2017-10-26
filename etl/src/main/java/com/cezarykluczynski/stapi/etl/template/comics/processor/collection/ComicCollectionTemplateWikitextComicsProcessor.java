package com.cezarykluczynski.stapi.etl.template.comics.processor.collection;

import com.cezarykluczynski.stapi.etl.common.dto.WikitextList;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.etl.common.service.WikitextListsExtractor;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComicCollectionTemplateWikitextComicsProcessor implements ItemProcessor<Page, Set<Comics>> {

	private static final String ISSUES = "Issues";
	private static final String ISSUES_COLLECTED = "Issues collected";
	private static final String ISSUES_COLLECTED_UC = "Issues Collected";
	private static final String CONTENTS = "Contents";
	private static final String CHAPTERS = "Chapters";
	private static final String BACKGROUND_INFORMATION = "Background information";

	private final PageSectionExtractor pageSectionExtractor;

	private final WikitextApi wikitextApi;

	private final WikitextListsExtractor wikitextListsExtractor;

	private final EntityLookupByNameService entityLookupByNameService;

	public ComicCollectionTemplateWikitextComicsProcessor(PageSectionExtractor pageSectionExtractor, WikitextApi wikitextApi,
			WikitextListsExtractor wikitextListsExtractor, EntityLookupByNameService entityLookupByNameService) {
		this.pageSectionExtractor = pageSectionExtractor;
		this.wikitextApi = wikitextApi;
		this.wikitextListsExtractor = wikitextListsExtractor;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public Set<Comics> process(Page item) throws Exception {
		Set<Comics> comicsSet = Sets.newHashSet();
		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(item, ISSUES, ISSUES_COLLECTED, ISSUES_COLLECTED_UC, CONTENTS, CHAPTERS,
				BACKGROUND_INFORMATION);

		if (pageSectionList.isEmpty()) {
			log.info("No comics containing sections were found for comic collection \"{}\"", item.getTitle());
			return comicsSet;
		}

		if (pageSectionList.size() > 1) {
			pageSectionList = pageSectionList.stream()
					.filter(pageSection -> !BACKGROUND_INFORMATION.equals(pageSection.getText()))
					.collect(Collectors.toList());
		}

		PageSection pageSection = pageSectionList.get(0);

		if (pageSectionList.size() > 1) {
			log.info("Page \"{}\" contains more than one section, using the first one: {}", item.getTitle(), pageSection.getText());
		}

		String pageSectionWikitext = pageSection.getWikitext();

		comicsSet.addAll(extractComics(wikitextListsExtractor.extractListsFromWikitext(pageSectionWikitext)));
		comicsSet.addAll(extractComics(wikitextListsExtractor.extractDefinitionsFromWikitext(pageSectionWikitext)));

		if (comicsSet.isEmpty()) {
			log.info("No comics could be extracted from page \"{}\"", item.getTitle());
		}

		return comicsSet;
	}

	private Set<Comics> extractComics(List<WikitextList> wikitextListList) {
		Set<Comics> comicsSet = Sets.newHashSet();

		wikitextListsExtractor.flattenWikitextListList(wikitextListList).forEach(wikitextList -> {
			List<String> lines = extractLines(wikitextList.getText());

			lines.forEach(line -> {
				List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(line);

				if (pageTitleList.isEmpty()) {
					return;
				}

				entityLookupByNameService
						.findComicsByName(pageTitleList.get(0), MediaWikiSource.MEMORY_ALPHA_EN)
						.ifPresent(comicsSet::add);
			});
		});

		return comicsSet;
	}

	private List<String> extractLines(String wikitext) {
		return Lists.newArrayList(wikitext.split("\n"));
	}

}
