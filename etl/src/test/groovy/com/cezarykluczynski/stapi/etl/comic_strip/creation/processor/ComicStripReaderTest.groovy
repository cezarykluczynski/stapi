package com.cezarykluczynski.stapi.etl.comic_strip.creation.processor

import com.cezarykluczynski.stapi.etl.comic_strip.creation.service.ComicStripCandidatePageGatheringService
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicStripReaderTest extends Specification {

	private ComicStripCandidatePageGatheringService comicStripCandidatePageGatheringServiceMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider stepCompletenessDeciderMock

	private ComicStripReader comicStripReader

	void setup() {
		comicStripCandidatePageGatheringServiceMock = Mock()
		categoryApiMock = Mock()
		stepCompletenessDeciderMock = Mock()
		comicStripReader = new ComicStripReader(comicStripCandidatePageGatheringServiceMock, categoryApiMock, stepCompletenessDeciderMock)
	}

	void "when first read is made, and CREATE_COMIC_STRIPS step is completed, first item being read is null"() {
		when:
		PageHeader pageHeader = comicStripReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_STRIPS) >> true
		0 * _
		pageHeader == null
	}

	@SuppressWarnings('BracesForMethod')
	void """when first read is made, and CREATE_COMIC_STRIPS step is not completed, and ComicStripCandidatePageGatheringService is not empty,
			first read is performed from it"""() {
		given:
		PageHeader pageHeaderFromService = Mock()

		when:
		PageHeader pageHeader = comicStripReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_STRIPS) >> false
		1 * comicStripCandidatePageGatheringServiceMock.isEmpty() >> false
		1 * comicStripCandidatePageGatheringServiceMock.allPageHeadersThenClean >> Lists.newArrayList(pageHeaderFromService)
		0 * _
		pageHeader == pageHeaderFromService

		when:
		PageHeader nextPageHeader = comicStripReader.read()

		then:
		nextPageHeader == null
	}

	@SuppressWarnings('BracesForMethod')
	void """when first read is made, and CREATE_COMIC_STRIPS step is not completed, and ComicStripCandidatePageGatheringService is empty,
			first read is performed CategoryApi"""() {
		given:
		PageHeader pageHeaderFromApi = Mock()

		when:
		PageHeader pageHeader = comicStripReader.read()

		then:
		1 * stepCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMIC_STRIPS) >> false
		1 * comicStripCandidatePageGatheringServiceMock.isEmpty() >> true
		1 * categoryApiMock.getPages(CategoryTitle.COMICS, MediaWikiSource.MEMORY_ALPHA_EN) >> Lists.newArrayList(pageHeaderFromApi)
		1 * categoryApiMock.getPages(CategoryTitle.PHOTONOVELS, MediaWikiSource.MEMORY_ALPHA_EN) >> Lists.newArrayList(pageHeaderFromApi)
		0 * _
		pageHeader == pageHeaderFromApi

		when:
		PageHeader nextPageHeader = comicStripReader.read()

		then:
		nextPageHeader == null
	}

}
