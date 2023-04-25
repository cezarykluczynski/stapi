package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper
import com.cezarykluczynski.stapi.etl.common.service.CategoryFinder
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource as ModelMediaWikiSource
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository
import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import spock.lang.Specification

class VideoTemplateCategoriesEnrichingProcessorTest extends Specification {

	private static final String PAGE_TITLE = 'PAGE_TITLE (DVD)'
	private static final String PAGE_TITLE_NO_BRACKETS = 'PAGE_TITLE'
	private static final ModelMediaWikiSource MODEL_MEDIA_WIKI_SOURCE = ModelMediaWikiSource.MEMORY_ALPHA_EN
	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN

	private CategoryFinder categoryFinderMock

	private MediaWikiSourceMapper mediaWikiSourceMapperMock

	private VideoReleaseRepository videoReleaseRepositoryMock

	private PageApi pageApiMock

	private VideoTemplateCategoriesEnrichingProcessor videoTemplateCategoriesEnrichingProcessor

	void setup() {
		categoryFinderMock = Mock()
		mediaWikiSourceMapperMock = Mock()
		videoReleaseRepositoryMock = Mock()
		pageApiMock = Mock()
		videoTemplateCategoriesEnrichingProcessor = new VideoTemplateCategoriesEnrichingProcessor(categoryFinderMock, mediaWikiSourceMapperMock,
				videoReleaseRepositoryMock, pageApiMock)
	}

	void "sets flags from categories"() {
		given:
		final Page page = new Page(title: PAGE_TITLE, mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		final VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateCategoriesEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.DOCUMENTARIES]) >> true
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.SPECIAL_FEATURES]) >> true
		0 * _
		videoTemplate.documentary
		videoTemplate.specialFeatures
	}

	void "sets flags from existing video releases"() {
		given:
		final Page page = new Page(title: PAGE_TITLE, mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		final VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)
		VideoRelease existingVideoRelease = new VideoRelease(documentary: true, specialFeatures: true)

		when:
		videoTemplateCategoriesEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.DOCUMENTARIES]) >> false
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.SPECIAL_FEATURES]) >> false
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * videoReleaseRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_NO_BRACKETS, MODEL_MEDIA_WIKI_SOURCE) >> Optional
				.of(existingVideoRelease)
		0 * _
		videoTemplate.documentary
		videoTemplate.specialFeatures
	}

	void "sets flags from Memory Alpha pages"() {
		given:
		final Page page = new Page(title: PAGE_TITLE, mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		final Page basePage = new Page(title: PAGE_TITLE_NO_BRACKETS, mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		final VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateCategoriesEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.DOCUMENTARIES]) >> false
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.SPECIAL_FEATURES]) >> false
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * videoReleaseRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_NO_BRACKETS, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_NO_BRACKETS, SOURCES_MEDIA_WIKI_SOURCE) >> basePage
		1 * categoryFinderMock.hasAnyCategory(basePage, [CategoryTitle.DOCUMENTARIES]) >> true
		1 * categoryFinderMock.hasAnyCategory(basePage, [CategoryTitle.SPECIAL_FEATURES]) >> true
		0 * _
		videoTemplate.documentary
		videoTemplate.specialFeatures
	}

	void "does not set flags"() {
		given:
		final Page page = new Page(title: PAGE_TITLE, mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		final Page basePage = new Page(title: PAGE_TITLE_NO_BRACKETS, mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		final VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateCategoriesEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.DOCUMENTARIES]) >> false
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.SPECIAL_FEATURES]) >> false
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * videoReleaseRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_NO_BRACKETS, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_NO_BRACKETS, SOURCES_MEDIA_WIKI_SOURCE) >> basePage
		1 * categoryFinderMock.hasAnyCategory(basePage, [CategoryTitle.DOCUMENTARIES]) >> false
		1 * categoryFinderMock.hasAnyCategory(basePage, [CategoryTitle.SPECIAL_FEATURES]) >> false
		0 * _
		!videoTemplate.documentary
		!videoTemplate.specialFeatures
	}

	void "uses cache for MA calls"() {
		given:
		final Page page = new Page(title: PAGE_TITLE, mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		final Page basePage = new Page(title: PAGE_TITLE_NO_BRACKETS, mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE)
		final VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateCategoriesEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.DOCUMENTARIES]) >> false
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.SPECIAL_FEATURES]) >> false
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * videoReleaseRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_NO_BRACKETS, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PAGE_TITLE_NO_BRACKETS, SOURCES_MEDIA_WIKI_SOURCE) >> basePage
		1 * categoryFinderMock.hasAnyCategory(basePage, [CategoryTitle.DOCUMENTARIES]) >> false
		1 * categoryFinderMock.hasAnyCategory(basePage, [CategoryTitle.SPECIAL_FEATURES]) >> false
		0 * _
		!videoTemplate.documentary
		!videoTemplate.specialFeatures

		when:
		videoTemplateCategoriesEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.DOCUMENTARIES]) >> false
		1 * categoryFinderMock.hasAnyCategory(page, [CategoryTitle.SPECIAL_FEATURES]) >> false
		1 * mediaWikiSourceMapperMock.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * videoReleaseRepositoryMock.findByPageTitleAndPageMediaWikiSource(PAGE_TITLE_NO_BRACKETS, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * categoryFinderMock.hasAnyCategory(basePage, [CategoryTitle.DOCUMENTARIES]) >> false
		1 * categoryFinderMock.hasAnyCategory(basePage, [CategoryTitle.SPECIAL_FEATURES]) >> false
		0 * _
		!videoTemplate.documentary
		!videoTemplate.specialFeatures
	}

}
