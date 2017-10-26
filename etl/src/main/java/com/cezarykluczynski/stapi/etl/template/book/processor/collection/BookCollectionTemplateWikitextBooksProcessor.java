package com.cezarykluczynski.stapi.etl.template.book.processor.collection;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookCollectionTemplateWikitextBooksProcessor implements ItemProcessor<Page, Set<Book>> {

	private static final String BOOKS_COLLECTED = "Books collected";
	private static final String SUMMARY = "Summary";
	private static final String STORIES = "Stories";
	private static final String BOOKS = "Books";
	private static final String CONTENTS = "Contents";

	private final PageSectionExtractor pageSectionExtractor;

	private final WikitextApi wikitextApi;

	private final EntityLookupByNameService entityLookupByNameService;

	public BookCollectionTemplateWikitextBooksProcessor(PageSectionExtractor pageSectionExtractor, WikitextApi wikitextApi,
			EntityLookupByNameService entityLookupByNameService) {
		this.pageSectionExtractor = pageSectionExtractor;
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public Set<Book> process(Page item) throws Exception {
		String pageTitle = item.getTitle();
		Set<Book> bookSet = Sets.newHashSet();

		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(item, BOOKS_COLLECTED, SUMMARY, STORIES, BOOKS, CONTENTS);

		if (pageSectionList.isEmpty()) {
			bookSet.addAll(extractBookFromPageIntro(item));
		}

		if (pageSectionList.isEmpty()) {
			log.warn("No books containing sections were found for book collection {}", pageTitle);
			return bookSet;
		}

		if (pageSectionList.size() > 1) {
			pageSectionList = pageSectionList.stream()
					.filter(pageSection -> !SUMMARY.equals(pageSection.getText()))
					.collect(Collectors.toList());
		}

		PageSection pageSection = pageSectionList.get(0);

		if (pageSectionList.size() > 1) {
			log.warn("Page \"{}\" contains more than one section, using the first one: \"{}\"", pageTitle, pageSection.getText());
		}

		bookSet.addAll(extractBook(pageSection.getWikitext()));

		if (bookSet.isEmpty()) {
			bookSet.addAll(extractBookFromPageIntro(item));
		}

		if (bookSet.isEmpty()) {
			log.warn("No books could be extracted for book collection \"{}\"", pageTitle);
		}

		return bookSet;
	}

	private Set<Book> extractBook(String wikitext) {
		Set<Book> comicsSet = Sets.newHashSet();

		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitext);

		pageTitleList.forEach(pageTitle -> {
			entityLookupByNameService
					.findBookByName(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN)
					.ifPresent(comicsSet::add);
		});

		return comicsSet;
	}

	private Set<Book> extractBookFromPageIntro(Page page) {
		Integer firstPageSectionIndex = page.getSections()
				.stream()
				.mapToInt(PageSection::getByteOffset)
				.min()
				.orElse(-1);
		String wikitext = page.getWikitext();

		return extractBook(firstPageSectionIndex > -1 ? wikitext.substring(0, firstPageSectionIndex) : wikitext);
	}

}
