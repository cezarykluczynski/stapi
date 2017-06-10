package com.cezarykluczynski.stapi.etl.magazine.creation.processor

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.magazine.creation.service.MagazineCandidatePageGatheringService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineReaderTest extends Specification {

	private MagazineCandidatePageGatheringService magazineCandidatePageGatheringServiceMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider stepCompletenessDeciderMock

	private MagazineReader magazineReader

	void setup() {
		magazineCandidatePageGatheringServiceMock = Mock()
		categoryApiMock = Mock()
		stepCompletenessDeciderMock = Mock()
		magazineReader = new MagazineReader(magazineCandidatePageGatheringServiceMock, categoryApiMock, stepCompletenessDeciderMock)
	}

	void "when first read is made, and CREATE_COMIC_STRIPS step is completed, first item being read is null"() {
		when:
		PageHeader pageHeader = magazineReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINES) >> true
		0 * _
		pageHeader == null
	}

	@SuppressWarnings('BracesForMethod')
	void """when first read is made, and CREATE_MAGAZINES step is not completed, and MagazineSeriesCandidatePageGatheringService is not empty,
			first read is performed from it"""() {
		given:
		PageHeader pageHeaderFromService = Mock()

		when:
		PageHeader pageHeader = magazineReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINES) >> false
		1 * magazineCandidatePageGatheringServiceMock.isEmpty() >> false
		1 * magazineCandidatePageGatheringServiceMock.allPageHeadersThenClean >> Lists.newArrayList(pageHeaderFromService)
		0 * _
		pageHeader == pageHeaderFromService

		when:
		PageHeader nextPageHeader = magazineReader.read()

		then:
		nextPageHeader == null
	}

	@SuppressWarnings('BracesForMethod')
	void """when first read is made, and CREATE_MAGAZINES step is not completed, and MagazineSeriesCandidatePageGatheringService is empty,
			first read is performed CategoryApi"""() {
		given:
		PageHeader pageHeaderFromApi = Mock()

		when:
		PageHeader pageHeader = magazineReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINES) >> false
		1 * magazineCandidatePageGatheringServiceMock.isEmpty() >> true
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.MAGAZINES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				Lists.newArrayList(pageHeaderFromApi)
		0 * _
		pageHeader == pageHeaderFromApi

		when:
		PageHeader nextPageHeader = magazineReader.read()

		then:
		nextPageHeader == null
	}

}
