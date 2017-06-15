package com.cezarykluczynski.stapi.etl.template.magazine_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.magazine.creation.service.MagazineCandidatePageGatheringService
import com.cezarykluczynski.stapi.etl.magazine_series.creation.service.MagazineSeriesDetector
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.cache.PageCacheStorage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineSeriesTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_MAGAZINE = 'TITLE (magazine)'

	private TemplateFinder templateFinderMock

	private MagazineSeriesDetector magazineSeriesDetectorMock

	private MagazineCandidatePageGatheringService magazineCandidatePageGatheringServiceMock

	private PageCacheStorage pageCacheStorageMock

	private PageBindingService pageBindingServiceMock

	private MagazineSeriesTemplatePartsEnrichingProcessor magazineSeriesTemplatePartsEnrichingProcessorMock

	private MagazineSeriesTemplatePageProcessor magazineSeriesTemplatePageProcessor

	void setup() {
		templateFinderMock = Mock()
		magazineSeriesDetectorMock = Mock()
		magazineCandidatePageGatheringServiceMock = Mock()
		pageCacheStorageMock = Mock()
		pageBindingServiceMock = Mock()
		magazineSeriesTemplatePartsEnrichingProcessorMock = Mock()
		magazineSeriesTemplatePageProcessor = new MagazineSeriesTemplatePageProcessor(templateFinderMock, magazineSeriesDetectorMock,
				magazineCandidatePageGatheringServiceMock, pageCacheStorageMock, pageBindingServiceMock,
				magazineSeriesTemplatePartsEnrichingProcessorMock)
	}

	void "returns null when page title is among invalid page titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(MagazineSeriesTemplatePageProcessor.INVALID_TITLES))

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		0 * _
		magazineSeriesTemplate == null
	}

	void "returns null when page is a product of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		0 * _
		magazineSeriesTemplate == null
	}

	@SuppressWarnings('BracesForMethod')
	void """returns null when sidebar magazine series template is not found, and MagazineSeriesDetector does not detect magazine series,
			and adds page to MagazineCandidatePageGatheringService, and to PageCacheStorage"""() {
		given:
		Page page = new Page(title: TITLE)

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> false
		1 * magazineCandidatePageGatheringServiceMock.addCandidate(page)
		1 * pageCacheStorageMock.put(page)
		0 * _
		magazineSeriesTemplate == null
	}

	void "does not return null when sidebar magazine series template is not found, but MagazineSeriesDetector does detect magazine series"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> true
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		0 * _
		magazineSeriesTemplate != null
	}

	void "clears title when it contains '(magazine)'"() {
		given:
		Page page = new Page(title: TITLE_MAGAZINE)
		ModelPage modelPage = new ModelPage()
		Template sidebarMagazineTemplate = Mock()
		List<Template.Part> templatePartList = Mock()

		when:
		MagazineSeriesTemplate magazineSeriesTemplate = magazineSeriesTemplatePageProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.of(sidebarMagazineTemplate)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * sidebarMagazineTemplate.parts >> templatePartList
		1 * magazineSeriesTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			assert enrichablePair.input == templatePartList
			assert enrichablePair.output != null
		}
		0 * _
		magazineSeriesTemplate.title == TITLE
	}

}
