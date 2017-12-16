package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.episode.creation.service.SeriesToEpisodeBindingService
import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodeLinkingWorkerComposite
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
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

	private EpisodeTemplateProcessor episodeTemplateProcessorMock

	private EpisodeLinkingWorkerComposite episodeLinkingWorkerCompositeMock

	private PageBindingService pageBindingServiceMock

	private SeriesToEpisodeBindingService seriesToEpisodeBindingServiceMock

	private EpisodeTemplateEnrichingProcessorComposite episodeTemplateEnrichingProcessorCompositeMock

	private TemplateFinder templateFinderMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private ToEpisodeTemplateProcessor toEpisodeTemplateProcessor

	void setup() {
		episodeTemplateProcessorMock = Mock()
		episodeLinkingWorkerCompositeMock = Mock()
		pageBindingServiceMock = Mock()
		seriesToEpisodeBindingServiceMock = Mock()
		episodeTemplateEnrichingProcessorCompositeMock = Mock()
		templateFinderMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		toEpisodeTemplateProcessor = new ToEpisodeTemplateProcessor(episodeTemplateProcessorMock, episodeLinkingWorkerCompositeMock,
				pageBindingServiceMock, seriesToEpisodeBindingServiceMock, episodeTemplateEnrichingProcessorCompositeMock, templateFinderMock,
				categoryTitlesExtractingProcessorMock)
	}

	void "does not interact with dependencies other than TemplateFinder when page does not have episode category"() {
		given:
		Page page = new Page()

		when:
		toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.empty()
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
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.of(sidebarEpisodeTemplate)
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >>
				Lists.newArrayList(CategoryTitle.TOS_EPISODES, CategoryTitle.PRODUCTION_LISTS)
		0 * _
		episodeTemplate == null
	}

	void "does not interact further with dependencies when page have episode category, but no template"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(new CategoryHeader(title: CategoryTitle.TOS_EPISODES))
		Page page = new Page(
				pageId: PAGE_ID,
				title: PAGE_TITLE,
				categories: categoryHeaderList
		)

		when:
		EpisodeTemplate episodeTemplate = toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.empty()
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.TOS_EPISODES)
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
		Episode episodeStub = Mock()
		EpisodeTemplate episodeTemplate = new EpisodeTemplate(
				episodeStub: episodeStub)

		when:
		EpisodeTemplate episodeTemplateOutput = toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_EPISODE) >> Optional.of(sidebarEpisodeTemplate)
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> Lists.newArrayList(CategoryTitle.TOS_EPISODES)
		1 * episodeTemplateProcessorMock.process(sidebarEpisodeTemplate) >> episodeTemplate
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		1 * episodeLinkingWorkerCompositeMock.link(page, episodeStub)
		1 * seriesToEpisodeBindingServiceMock.mapCategoriesToSeries(categoryHeaderList) >> series
		1 * episodeTemplateEnrichingProcessorCompositeMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			enrichablePair.input == page
			enrichablePair.output == episodeTemplate
		}
		0 * _
		episodeTemplateOutput == episodeTemplate
		episodeTemplateOutput.title == EPISODE_TITLE
		episodeTemplateOutput.page == pageEntity
		episodeTemplateOutput.series == series
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
		1 * episodeTemplateProcessorMock.process(_) >> null
		episodeTemplateOutput == null
	}

}
