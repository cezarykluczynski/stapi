package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData
import com.cezarykluczynski.stapi.etl.episode.creation.service.ModuleEpisodeDataProvider
import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodeLinkingWorkerComposite
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class ToEpisodeTemplateProcessorTest extends Specification {

	private static final Long PAGE_ID = 1L
	private static final String PAGE_TITLE = 'All Good Things... (episode)'
	private static final String EPISODE_TITLE = 'All Good Things...'
	private final Template sidebarEpisodeTemplate = new Template(title: TemplateTitle.SIDEBAR_EPISODE)
	private final Series series = Mock()
	private final Season season = Mock()

	private EpisodeLinkingWorkerComposite episodeLinkingWorkerCompositeMock

	private PageBindingService pageBindingServiceMock

	private EpisodeTemplateEnrichingProcessorComposite episodeTemplateEnrichingProcessorCompositeMock

	private TemplateFinder templateFinderMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private ModuleEpisodeDataEnrichingProcessor moduleEpisodeDataEnrichingProcessorMock

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessorMock

	private ModuleEpisodeDataProvider moduleEpisodeDataProviderMock

	private ToEpisodeTemplateProcessor toEpisodeTemplateProcessor

	void setup() {
		episodeLinkingWorkerCompositeMock = Mock()
		pageBindingServiceMock = Mock()
		episodeTemplateEnrichingProcessorCompositeMock = Mock()
		templateFinderMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		moduleEpisodeDataEnrichingProcessorMock = Mock()
		imageTemplateStardateYearEnrichingProcessorMock = Mock()
		moduleEpisodeDataProviderMock = Mock()
		toEpisodeTemplateProcessor = new ToEpisodeTemplateProcessor(episodeLinkingWorkerCompositeMock,
				pageBindingServiceMock, episodeTemplateEnrichingProcessorCompositeMock, templateFinderMock,
				categoryTitlesExtractingProcessorMock, moduleEpisodeDataEnrichingProcessorMock,
				imageTemplateStardateYearEnrichingProcessorMock, moduleEpisodeDataProviderMock)
	}

	void "does not interact with dependencies other than TemplateFinder when page does not have episode category"() {
		given:
		Page page = new Page()

		when:
		toEpisodeTemplateProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList()) >> Lists.newArrayList()
		0 * _
	}

	void "does not interact further with dependencies when page does have episode category, but the production lists category too"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryTitle.TOS_EPISODES),
				new CategoryHeader(title: CategoryTitle.PRODUCTION_LISTS)
		)
		Page page = new Page(
				categories: categoryHeaderList,
				templates: Lists.newArrayList(sidebarEpisodeTemplate)
		)

		when:
		EpisodeTemplate episodeTemplate = toEpisodeTemplateProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >>
				Lists.newArrayList(CategoryTitle.TOS_EPISODES, CategoryTitle.PRODUCTION_LISTS)
		0 * _
		episodeTemplate == null
	}

	void "page is processed using dependencies"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: CategoryTitle.TOS_EPISODES))
		Page page = new Page(
				pageId: PAGE_ID,
				title: PAGE_TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList(sidebarEpisodeTemplate))
		PageEntity pageEntity = Mock()
		ModuleEpisodeData moduleEpisodeData = Mock()

		when:
		EpisodeTemplate episodeTemplateOutput = toEpisodeTemplateProcessor.process(page)

		then:
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(
				CategoryTitle.TOS_EPISODES, CategoryTitle.DOUBLE_LENGTH_EPISODES)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.of(sidebarEpisodeTemplate)
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		1 * moduleEpisodeDataProviderMock.provideDataFor(PAGE_TITLE) >> moduleEpisodeData
		1 * moduleEpisodeDataEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<ModuleEpisodeData, EpisodeTemplate> enrichablePair ->
			enrichablePair.output.series = series
			enrichablePair.output.season = season
		}
		1 * episodeLinkingWorkerCompositeMock.link(page, _)
		1 * episodeTemplateEnrichingProcessorCompositeMock.enrich(_ as EnrichablePair) >> { EnrichablePair<Page, EpisodeTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		episodeTemplateOutput.title == EPISODE_TITLE
		episodeTemplateOutput.page == pageEntity
		episodeTemplateOutput.series == series
		episodeTemplateOutput.season == season
		episodeTemplateOutput.featureLength
	}

	void "returns null when EpisodeTemplateProcessor returns null"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: CategoryTitle.TOS_EPISODES))
		Page page = new Page(
				categories: categoryHeaderList,
				templates: Lists.newArrayList(sidebarEpisodeTemplate)
		)

		when:
		EpisodeTemplate episodeTemplateOutput = toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.of(sidebarEpisodeTemplate)
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.TOS_EPISODES)
		episodeTemplateOutput == null
	}

}
