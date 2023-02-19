package com.cezarykluczynski.stapi.etl.common.service.step

import com.cezarykluczynski.stapi.etl.astronomical_object.link.processor.AstronomicalObjectLinkReader
import com.cezarykluczynski.stapi.etl.book.creation.processor.BookReader
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class StepToReadProcessorCountProviderTest extends Specification {

	ApplicationContext applicationContextMock

	StepToReadProcessorCountProvider stepToReadProcessorCountProvider

	void setup() {
		applicationContextMock = Mock()
		stepToReadProcessorCountProvider = new StepToReadProcessorCountProvider(applicationContextMock)
	}

	void "returns 0 for when there is no mapping for a given step"() {
		when:
		int readCountForStep = stepToReadProcessorCountProvider.getReadCountForStep('UNKNOWN_STEP')

		then:
		readCountForStep == 0
	}

	void "returns item count from ListItemReader"() {
		given:
		PageHeader book1 = Mock()
		PageHeader book2 = Mock()
		PageHeader book3 = Mock()
		BookReader bookReader = new BookReader([book1, book2, book3])

		when:
		int readCountForStep = stepToReadProcessorCountProvider.getReadCountForStep(StepName.CREATE_BOOKS)

		then:
		1 * applicationContextMock.getBean(BookReader) >> bookReader
		0 * _
		readCountForStep == 3
	}

	void "returns item count from RepositoryItemReader"() {
		given:
		AstronomicalObjectRepository astronomicalObjectRepository = Mock()
		AstronomicalObjectLinkReader astronomicalObjectLinkReader = new AstronomicalObjectLinkReader()
		astronomicalObjectLinkReader.setRepository(astronomicalObjectRepository)

		when:
		int readCountForStep = stepToReadProcessorCountProvider.getReadCountForStep(StepName.LINK_ASTRONOMICAL_OBJECTS)

		then:
		1 * applicationContextMock.getBean(AstronomicalObjectLinkReader) >> astronomicalObjectLinkReader
		1 * astronomicalObjectRepository.count() >> 5
		0 * _
		readCountForStep == 5
	}

}
