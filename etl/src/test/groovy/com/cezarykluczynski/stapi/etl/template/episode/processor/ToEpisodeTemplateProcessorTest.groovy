package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.episode.creation.service.SeriesToEpisodeBindingService
import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodeLinkingWorkerComposite
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class ToEpisodeTemplateProcessorTest extends Specification {

	private static final Long PAGE_ID = 1L
	private static final String PAGE_TITLE = 'All Good Things... (episode)'
	private static final String EPISODE_TITLE = 'All Good Things...'
	private final Template SIDEBAR_EPISODE_TEMPLATE = new Template(
			title: TemplateName.SIDEBAR_EPISODE
	)
	private final Series SERIES = Mock(Series)

	private EpisodeTemplateProcessor episodeTemplateProcessorMock

	private EpisodeLinkingWorkerComposite episodeLinkingWorkerCompositeMock

	private PageBindingService pageBindingServiceMock

	private SeriesToEpisodeBindingService seriesToEpisodeBindingServiceMock

	private EpisodeTemplateEnrichingProcessorComposite episodeTemplateEnrichingProcessorCompositeMock

	private TemplateFinder templateFinderMock

	private EpisodeTitleFixedValueProvider episodeTitleFixedValueProviderMock

	private ToEpisodeTemplateProcessor toEpisodeTemplateProcessor

	def setup() {
		episodeTemplateProcessorMock = Mock(EpisodeTemplateProcessor)
		episodeLinkingWorkerCompositeMock = Mock(EpisodeLinkingWorkerComposite)
		pageBindingServiceMock = Mock(PageBindingService)
		seriesToEpisodeBindingServiceMock = Mock(SeriesToEpisodeBindingService)
		episodeTemplateEnrichingProcessorCompositeMock = Mock(EpisodeTemplateEnrichingProcessorComposite)
		templateFinderMock = Mock(TemplateFinder)
		episodeTitleFixedValueProviderMock = Mock(EpisodeTitleFixedValueProvider)
		toEpisodeTemplateProcessor = new ToEpisodeTemplateProcessor(episodeTemplateProcessorMock, episodeLinkingWorkerCompositeMock,
				pageBindingServiceMock, seriesToEpisodeBindingServiceMock, episodeTemplateEnrichingProcessorCompositeMock, templateFinderMock,
				episodeTitleFixedValueProviderMock)
	}


	def "does not interact with dependencies other than TemplateFinder when page does not have episode category"() {
		given:
		Page page = new Page()

		when:
		toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_EPISODE) >> Optional.empty()
		0 * _
	}

	def "does not interact with dependencies other than TemplateFinder when page does have episode category, but the production lists category too"() {
		given:
		Page page = new Page(
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES),
						new CategoryHeader(title: CategoryName.PRODUCTION_LISTS)
				),
				templates: Lists.newArrayList(SIDEBAR_EPISODE_TEMPLATE)
		)

		when:
		EpisodeTemplate episodeTemplate = toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_EPISODE) >> Optional.of(SIDEBAR_EPISODE_TEMPLATE)
		0 * _
		episodeTemplate == null
	}

	def "does not interact with dependencies other than TemplateFinder when page have episode category, but no template"() {
		given:
		Page page = new Page(
				pageId: PAGE_ID,
				title: PAGE_TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES)
				)
		)

		when:
		EpisodeTemplate episodeTemplate = toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_EPISODE) >> Optional.empty()
		0 * _
		episodeTemplate == null
	}

	def "page is processed using dependencies"() {
		given:
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(
				new CategoryHeader(title: CategoryName.TOS_EPISODES)
		)
		Page page = new Page(
				pageId: PAGE_ID,
				title: PAGE_TITLE,
				categories: categoryHeaderList,
				templates: Lists.newArrayList(SIDEBAR_EPISODE_TEMPLATE))
		PageEntity pageEntity = Mock(PageEntity)
		Episode episodeStub = Mock(Episode)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate(
				episodeStub: episodeStub)

		when:
		EpisodeTemplate episodeTemplateOutput = toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_EPISODE) >> Optional.of(SIDEBAR_EPISODE_TEMPLATE)
		1 * episodeTemplateProcessorMock.process(SIDEBAR_EPISODE_TEMPLATE) >> episodeTemplate
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		1 * episodeLinkingWorkerCompositeMock.link(page, episodeStub)
		1 * episodeTitleFixedValueProviderMock.getSearchedValue(EPISODE_TITLE) >> FixedValueHolder.notFound()
		1 * seriesToEpisodeBindingServiceMock.mapCategoriesToSeries(categoryHeaderList) >> SERIES
		1 * episodeTemplateEnrichingProcessorCompositeMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			enrichablePair.input == page
			enrichablePair.output == episodeTemplate
		}
		0 * _
		episodeTemplateOutput == episodeTemplate
		episodeTemplateOutput.title == EPISODE_TITLE
		episodeTemplateOutput.page == pageEntity
		episodeTemplateOutput.series == SERIES
	}

	def "returns null when EpisodeTemplateProcessor returns null"() {
		given:
		Page page = new Page(
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES)
				),
				templates: Lists.newArrayList(SIDEBAR_EPISODE_TEMPLATE)
		)

		when:
		EpisodeTemplate episodeTemplateOutput = toEpisodeTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_EPISODE) >> Optional.of(SIDEBAR_EPISODE_TEMPLATE)
		1 * episodeTemplateProcessorMock.process(_) >> null
		episodeTemplateOutput == null
	}

}
