package com.cezarykluczynski.stapi.etl.magazine.creation.service

import com.cezarykluczynski.stapi.etl.magazine_series.creation.service.MagazineSeriesDetector
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazinePageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'

	private MagazineSeriesDetector magazineSeriesDetectorMock

	private TemplateFinder templateFinderMock

	private MagazinePageFilter magazinePageFilter

	void setup() {
		magazineSeriesDetectorMock = Mock()
		templateFinderMock = Mock()
		magazinePageFilter = new MagazinePageFilter(magazineSeriesDetectorMock, templateFinderMock)
	}

	void "returns true when page title is among invalid page titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(MagazinePageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = magazinePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is a product of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = magazinePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when MagazineSeriesDetector detect magazine series"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = magazinePageFilter.shouldBeFilteredOut(page)

		then:
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when sidebar magazine series template is found"() {
		given:
		Page page = new Page(title: TITLE)
		Template sidebarMagazineSeriesTemplate = Mock()

		when:
		boolean shouldBeFilteredOut = magazinePageFilter.shouldBeFilteredOut(page)

		then:
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.of(sidebarMagazineSeriesTemplate)
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when everything is in order"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = magazinePageFilter.shouldBeFilteredOut(page)

		then:
		1 * magazineSeriesDetectorMock.isMagazineSeries(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MAGAZINE_SERIES) >> Optional.empty()
		0 * _
		!shouldBeFilteredOut
	}

}
