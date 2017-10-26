package com.cezarykluczynski.stapi.etl.book_series.link.processor;


import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookSeriesLinkProcessor implements ItemProcessor<BookSeries, BookSeries> {

	private final MediaWikiSourceMapper mediaWikiSourceMapper;

	private final PageApi pageApi;

	private final TemplateFinder templateFinder;

	private final WikitextApi wikitextApi;

	private final BookSeriesRepository bookSeriesRepository;

	public BookSeriesLinkProcessor(MediaWikiSourceMapper mediaWikiSourceMapper, PageApi pageApi, TemplateFinder templateFinder,
			WikitextApi wikitextApi, BookSeriesRepository bookSeriesRepository) {
		this.mediaWikiSourceMapper = mediaWikiSourceMapper;
		this.pageApi = pageApi;
		this.templateFinder = templateFinder;
		this.wikitextApi = wikitextApi;
		this.bookSeriesRepository = bookSeriesRepository;
	}

	@Override
	public BookSeries process(BookSeries item) throws Exception {
		doProcess(item);
		return item;
	}

	private void doProcess(BookSeries item) throws Exception {
		com.cezarykluczynski.stapi.sources.mediawiki.dto.Page page = pageFromBookSeries(item);

		if (page == null) {
			return;
		}

		Optional<Template> templateOptional = templateFinder.findTemplate(page, TemplateTitle.SIDEBAR_NOVEL_SERIES);

		if (!templateOptional.isPresent()) {
			return;
		}

		tryAddSeriesFromTemplate(item, templateOptional.get());
	}

	private com.cezarykluczynski.stapi.sources.mediawiki.dto.Page pageFromBookSeries(BookSeries item) {
		Page modelPage = item.getPage();
		String pageTitle = modelPage.getTitle();
		MediaWikiSource mediaWikiSource = modelPage.getMediaWikiSource();

		return findPageByTitleAndMediaWikiSource(pageTitle, mediaWikiSource);
	}

	private void tryAddSeriesFromTemplate(BookSeries item, Template template) {
		template.getParts()
				.stream()
				.filter(this::isSeries)
				.findFirst()
				.ifPresent(part -> enrichWithParentSeriesFromWikitext(item, part.getValue()));
	}

	private boolean isSeries(Template.Part part) {
		return BookSeriesTemplateParameter.SERIES.equals(part.getKey());
	}

	private void enrichWithParentSeriesFromWikitext(BookSeries item, String wikitext) {
		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitext);
		Set<BookSeries> parentSeriesSet = item.getParentSeries();

		pageTitleList.forEach(pageTitle -> {
			MediaWikiSource mediaWikiSource = item.getPage().getMediaWikiSource();
			Optional<BookSeries> parentBookSeriesOptional = findBookSeriesByPageTitleAndPageMediaWikiSource(pageTitle, mediaWikiSource);

			if (!parentBookSeriesOptional.isPresent()) {
				com.cezarykluczynski.stapi.sources.mediawiki.dto.Page page = findPageByTitleAndMediaWikiSource(pageTitle, mediaWikiSource);

				if (page != null) {
					parentBookSeriesOptional = findBookSeriesByPageTitleAndPageMediaWikiSource(page.getTitle(), mediaWikiSource);
				}
			}

			parentBookSeriesOptional.ifPresent(parentSeriesSet::add);
		});
	}

	private com.cezarykluczynski.stapi.sources.mediawiki.dto.Page findPageByTitleAndMediaWikiSource(String pageTitle,
			MediaWikiSource mediaWikiSource) {
		return pageApi.getPage(pageTitle, mediaWikiSourceMapper.fromEntityToSources(mediaWikiSource));
	}

	private Optional<BookSeries> findBookSeriesByPageTitleAndPageMediaWikiSource(String pageTitle, MediaWikiSource mediaWikiSource) {
		return bookSeriesRepository.findByPageTitleAndPageMediaWikiSource(pageTitle, mediaWikiSource);
	}

}
