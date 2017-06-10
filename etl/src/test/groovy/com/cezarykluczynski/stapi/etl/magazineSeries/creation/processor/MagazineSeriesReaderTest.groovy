package com.cezarykluczynski.stapi.etl.magazineSeries.creation.processor

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.magazineSeries.creation.service.MagazineSeriesCandidatePageGatheringService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineSeriesReaderTest extends Specification {

	private MagazineSeriesCandidatePageGatheringService magazineSeriesCandidatePageGatheringServiceMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider stepCompletenessDeciderMock

	private MagazineSeriesReader magazineSeriesReader

	void setup() {
		magazineSeriesCandidatePageGatheringServiceMock = Mock()
		categoryApiMock = Mock()
		stepCompletenessDeciderMock = Mock()
		magazineSeriesReader = new MagazineSeriesReader(magazineSeriesCandidatePageGatheringServiceMock, categoryApiMock, stepCompletenessDeciderMock)
	}

	void "when first read is made, and CREATE_COMIC_STRIPS step is completed, first item being read is null"() {
		when:
		PageHeader pageHeader = magazineSeriesReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINE_SERIES) >> true
		0 * _
		pageHeader == null
	}

	@SuppressWarnings('BracesForMethod')
	void """when first read is made, and CREATE_MAGAZINE_SERIES step is not completed, and MagazineSeriesCandidatePageGatheringService is not empty,
			first read is performed from it"""() {
		given:
		PageHeader pageHeaderFromService = Mock()

		when:
		PageHeader pageHeader = magazineSeriesReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINE_SERIES) >> false
		1 * magazineSeriesCandidatePageGatheringServiceMock.isEmpty() >> false
		1 * magazineSeriesCandidatePageGatheringServiceMock.allPageHeadersThenClean >> Lists.newArrayList(pageHeaderFromService)
		0 * _
		pageHeader == pageHeaderFromService

		when:
		PageHeader nextPageHeader = magazineSeriesReader.read()

		then:
		nextPageHeader == null
	}

	@SuppressWarnings('BracesForMethod')
	void """when first read is made, and CREATE_MAGAZINE_SERIES step is not completed, and MagazineSeriesCandidatePageGatheringService is empty,
			first read is performed CategoryApi"""() {
		given:
		PageHeader pageHeaderFromApi = Mock()

		when:
		PageHeader pageHeader = magazineSeriesReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINE_SERIES) >> false
		1 * magazineSeriesCandidatePageGatheringServiceMock.isEmpty() >> true
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.MAGAZINES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				Lists.newArrayList(pageHeaderFromApi)
		0 * _
		pageHeader == pageHeaderFromApi

		when:
		PageHeader nextPageHeader = magazineSeriesReader.read()

		then:
		nextPageHeader == null
	}

}
