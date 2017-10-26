package com.cezarykluczynski.stapi.etl.comic_series.link.processor;

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository;
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
public class ComicSeriesLinkProcessor implements ItemProcessor<ComicSeries, ComicSeries> {

	private final MediaWikiSourceMapper mediaWikiSourceMapper;

	private final PageApi pageApi;

	private final TemplateFinder templateFinder;

	private final WikitextApi wikitextApi;

	private final ComicSeriesRepository comicSeriesRepository;

	public ComicSeriesLinkProcessor(MediaWikiSourceMapper mediaWikiSourceMapper, PageApi pageApi, TemplateFinder templateFinder,
			WikitextApi wikitextApi, ComicSeriesRepository comicSeriesRepository) {
		this.mediaWikiSourceMapper = mediaWikiSourceMapper;
		this.pageApi = pageApi;
		this.templateFinder = templateFinder;
		this.wikitextApi = wikitextApi;
		this.comicSeriesRepository = comicSeriesRepository;
	}

	@Override
	public ComicSeries process(ComicSeries item) throws Exception {
		doProcess(item);
		return item;
	}

	private void doProcess(ComicSeries item) throws Exception {
		com.cezarykluczynski.stapi.sources.mediawiki.dto.Page page = pageFromComicSeries(item);

		if (page == null) {
			return;
		}

		Optional<Template> templateOptional = templateFinder.findTemplate(page, TemplateTitle.SIDEBAR_COMIC_SERIES);

		if (!templateOptional.isPresent()) {
			return;
		}

		tryAddSeriesFromTemplate(item, templateOptional.get());
	}

	private com.cezarykluczynski.stapi.sources.mediawiki.dto.Page pageFromComicSeries(ComicSeries item) {
		Page modelPage = item.getPage();
		String pageTitle = modelPage.getTitle();
		MediaWikiSource mediaWikiSource = modelPage.getMediaWikiSource();

		return findPageByTitleAndMediaWikiSource(pageTitle, mediaWikiSource);
	}

	private void tryAddSeriesFromTemplate(ComicSeries item, Template template) {
		template.getParts()
				.stream()
				.filter(this::isSeries)
				.findFirst()
				.ifPresent(part -> enrichWithParentSeriesFromWikitext(item, part.getValue()));
	}

	private boolean isSeries(Template.Part part) {
		return ComicSeriesTemplateParameter.SERIES.equals(part.getKey());
	}

	private void enrichWithParentSeriesFromWikitext(ComicSeries item, String wikitext) {
		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitext);
		Set<ComicSeries> parentSeriesSet = item.getParentSeries();

		pageTitleList.forEach(pageTitle -> {
			MediaWikiSource mediaWikiSource = item.getPage().getMediaWikiSource();
			Optional<ComicSeries> parentComicSeriesOptional = findComicSeriesByPageTitleAndPageMediaWikiSource(pageTitle, mediaWikiSource);

			if (!parentComicSeriesOptional.isPresent()) {
				com.cezarykluczynski.stapi.sources.mediawiki.dto.Page page = findPageByTitleAndMediaWikiSource(pageTitle, mediaWikiSource);

				if (page != null) {
					parentComicSeriesOptional = findComicSeriesByPageTitleAndPageMediaWikiSource(page.getTitle(), mediaWikiSource);
				}
			}

			parentComicSeriesOptional.ifPresent(parentSeriesSet::add);
		});
	}

	private com.cezarykluczynski.stapi.sources.mediawiki.dto.Page findPageByTitleAndMediaWikiSource(String pageTitle,
			MediaWikiSource mediaWikiSource) {
		return pageApi.getPage(pageTitle, mediaWikiSourceMapper.fromEntityToSources(mediaWikiSource));
	}

	private Optional<ComicSeries> findComicSeriesByPageTitleAndPageMediaWikiSource(String pageTitle, MediaWikiSource mediaWikiSource) {
		return comicSeriesRepository.findByPageTitleAndPageMediaWikiSource(pageTitle, mediaWikiSource);
	}

}
