package com.cezarykluczynski.stapi.etl.literature.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class LiteratureCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_LITERATURE = 'TITLE_LITERATURE'
	private static final String TITLE_EARTH_LITERATURE = 'TITLE_EARTH_LITERATURE'
	private static final String TITLE_SHAKESPEAREAN_WORKS = 'TITLE_SHAKESPEAREAN_WORKS'
	private static final String TITLE_REPORTS = 'TITLE_REPORTS'
	private static final String TITLE_SCIENTIFIC_LITERATURE = 'TITLE_SCIENTIFIC_LITERATURE'
	private static final String TITLE_SCIENTIFIC_LITERATURE_RETCONNED = 'TITLE_SCIENTIFIC_LITERATURE_RETCONNED'
	private static final String TITLE_TECHNICAL_MANUALS = 'TITLE_TECHNICAL_MANUALS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private LiteratureCreationConfiguration literatureCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		literatureCreationConfiguration = new LiteratureCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "LiteratureReader is created is created with all pages when step is not completed"() {
		when:
		LiteratureReader literatureReader = literatureCreationConfiguration.literatureReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(literatureReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_LITERATURE) >> false
		1 * categoryApiMock.getPages(CategoryTitle.LITERATURE, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_LITERATURE)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_LITERATURE, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_LITERATURE)
		1 * categoryApiMock.getPages(CategoryTitle.SHAKESPEAREAN_WORKS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SHAKESPEAREAN_WORKS)
		1 * categoryApiMock.getPages(CategoryTitle.REPORTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_REPORTS)
		1 * categoryApiMock.getPages(CategoryTitle.SCIENTIFIC_LITERATURE, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SCIENTIFIC_LITERATURE)
		1 * categoryApiMock.getPages(CategoryTitle.SCIENTIFIC_LITERATURE_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SCIENTIFIC_LITERATURE_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.TECHNICAL_MANUALS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TECHNICAL_MANUALS)
		0 * _
		categoryHeaderTitleList.contains TITLE_LITERATURE
		categoryHeaderTitleList.contains TITLE_EARTH_LITERATURE
		categoryHeaderTitleList.contains TITLE_SHAKESPEAREAN_WORKS
		categoryHeaderTitleList.contains TITLE_REPORTS
		categoryHeaderTitleList.contains TITLE_SCIENTIFIC_LITERATURE
		categoryHeaderTitleList.contains TITLE_SCIENTIFIC_LITERATURE_RETCONNED
		categoryHeaderTitleList.contains TITLE_TECHNICAL_MANUALS
	}

	void "LiteratureReader is created with no pages when step is completed"() {
		when:
		LiteratureReader literatureReader = literatureCreationConfiguration.literatureReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(literatureReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_LITERATURE) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
