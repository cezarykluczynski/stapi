package com.cezarykluczynski.stapi.etl.magazine_series.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class MagazineSeriesDetectorTest extends Specification {

	private MagazineSeriesDetector magazineSeriesDetector

	void setup() {
		magazineSeriesDetector = new MagazineSeriesDetector()
	}

	void "detects existing magazine series"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(MagazineSeriesDetector.MAGAZINE_SERIES_TITLE))

		when:
		boolean isMagazineSeries = magazineSeriesDetector.isMagazineSeries(page)

		then:
		isMagazineSeries
	}

	void "detects page that is not a magazine series"() {
		given:
		Page page = new Page(title: 'Some title')

		when:
		boolean isMagazineSeries = magazineSeriesDetector.isMagazineSeries(page)

		then:
		!isMagazineSeries
	}

}
