package com.cezarykluczynski.stapi.etl.template.comics.processor.collection;

import com.cezarykluczynski.stapi.etl.common.dto.WikitextList;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.etl.common.service.WikitextListsExtractor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionContents;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComicCollectionTemplateWikitextComicsProcessor implements ItemProcessor<Page, ComicCollectionContents> {

	private static final String ISSUES = "Issues";
	private static final String ISSUES_COLLECTED = "Issues collected";
	private static final String ISSUES_COLLECTED_UC = "Issues Collected";
	private static final String CONTENTS = "Contents";
	private static final String CHAPTERS = "Chapters";
	private static final String BACKGROUND_INFORMATION = "Background information";
	private static final String TITLES = "Titles";
	private static final List<String> PAGES_NO_CONTENTS = Lists.newArrayList(
			// all fives are comic strips collections
			"Star Trek: The Classic UK Comics, Volume 1",
			"Star Trek: The Classic UK Comics, Volume 2",
			"Star Trek: The Classic UK Comics, Volume 3",
			"Star Trek: The Newspaper Comics, Volume 1",
			"Star Trek: The Newspaper Comics, Volume 2"
	);

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
	@NonNull
	public ComicCollectionContents process(Page item) throws Exception {
		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(item, ISSUES, ISSUES_COLLECTED, ISSUES_COLLECTED_UC, CONTENTS, CHAPTERS,
				BACKGROUND_INFORMATION, TITLES);

		ComicCollectionContents comicCollectionContents = new ComicCollectionContents();
		if (pageSectionList.isEmpty()) {
			log.info("No comics containing sections were found for comic collection \"{}\"", item.getTitle());
			return comicCollectionContents;
		}

		if (pageSectionList.size() > 1) {
			pageSectionList = pageSectionList.stream()
					.filter(pageSection -> !BACKGROUND_INFORMATION.equals(pageSection.getText()))
					.collect(Collectors.toList());
		}

		PageSection pageSection = pageSectionList.get(0);

		String pageTitle = item.getTitle();
		if (pageSectionList.size() > 1) {
			log.info("Page \"{}\" contains more than one section, using the first one: {}.", pageTitle, pageSection.getText());
		}

		String pageSectionWikitext = pageSection.getWikitext();
		List<WikitextList> wikitextListList = Lists.newArrayList();
		wikitextListList.addAll(wikitextListsExtractor.extractListsFromWikitext(pageSectionWikitext));
		wikitextListList.addAll(wikitextListsExtractor.extractDefinitionsFromWikitext(pageSectionWikitext));
		Set<String> pageLinkTitles = extractPageTitles(wikitextListList);

		comicCollectionContents.getComics().addAll(extractComics(pageLinkTitles));
		comicCollectionContents.getComicSeries().addAll(extractComicSeries(pageLinkTitles));
		if (comicCollectionContents.getComics().isEmpty() && comicCollectionContents.getComicSeries().isEmpty()) {
			if (!PAGES_NO_CONTENTS.contains(pageTitle)) {
				log.info("Could not extract any comic collection contents from page \"{}\" section \"{}\".", pageTitle, pageSection.getAnchor());
			}
		}

		return comicCollectionContents;
	}

	private Set<String> extractPageTitles(List<WikitextList> wikitextListList) {
		Set<String> titles = Sets.newHashSet();

		wikitextListsExtractor.flattenWikitextListList(wikitextListList).forEach(wikitextList -> {
			List<String> lines = extractLines(wikitextList.getText());

			lines.forEach(line -> {
				List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(line);

				if (pageTitleList.isEmpty()) {
					return;
				}

				titles.add(pageTitleList.get(0));
			});
		});

		return titles;
	}

	private Set<Comics> extractComics(Set<String> pageTitles) {
		return pageTitles.stream()
				.map(pageTitle -> entityLookupByNameService.findComicsByName(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

	private Set<ComicSeries> extractComicSeries(Set<String> pageTitles) {
		return pageTitles.stream()
				.map(pageTitle -> entityLookupByNameService.findComicSeriesByName(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

	private List<String> extractLines(String wikitext) {
		return wikitext != null ? Lists.newArrayList(wikitext.split("\n")) : Lists.newArrayList();
	}

}
