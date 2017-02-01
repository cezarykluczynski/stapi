package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import spock.lang.Specification

class ComicSeriesTemplateNumberOfIssuesProcessorTest extends Specification {

	private ComicSeriesTemplateNumberOfIssuesProcessor comicSeriesTemplateNumberOfIssuesProcessor

	void setup() {
		comicSeriesTemplateNumberOfIssuesProcessor = new ComicSeriesTemplateNumberOfIssuesProcessor()
	}

	void "parsed valid integer"() {
		expect:
		comicSeriesTemplateNumberOfIssuesProcessor.process('1') == 1
		comicSeriesTemplateNumberOfIssuesProcessor.process('12') == 12
		comicSeriesTemplateNumberOfIssuesProcessor.process('123') == 123
	}

	void "returns null for invalid integers"() {
		expect:
		comicSeriesTemplateNumberOfIssuesProcessor.process('') == null
		comicSeriesTemplateNumberOfIssuesProcessor.process('1+') == null
		comicSeriesTemplateNumberOfIssuesProcessor.process('No') == null
	}

}
