package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import com.cezarykluczynski.stapi.server.common.metrics.service.EndpointHitsReader
import com.google.common.collect.Maps
import spock.lang.Specification

class CommonHitsStatisticsReaderTest extends Specification {

	private static final long ALL_HITS_COUNT = 15
	private static final long BOOK_HITS_COUNT = 10
	private static final long COMICS_HITS_COUNT = 5

	private EndpointHitsReader endpointHitsReaderMock

	private CommonHitsStatisticsReader commonHitsStatisticsReader

	void setup() {
		endpointHitsReaderMock = Mock()
		commonHitsStatisticsReader = new CommonHitsStatisticsReader(endpointHitsReaderMock)
	}

	void "reads hits statistics from EndpointHitsReader"() {
		given:
		Map<Class<? extends PageAware>, Long> endpointHits = Maps.newLinkedHashMap()
		endpointHits.put(Book, BOOK_HITS_COUNT)
		endpointHits.put(Comics, COMICS_HITS_COUNT)

		when:
		RestEndpointStatisticsDTO restEndpointStatisticsDTO = commonHitsStatisticsReader.hitsStatistics()

		then:
		1 * endpointHitsReaderMock.readEndpointHits() >> endpointHits
		1 * endpointHitsReaderMock.readAllHitsCount() >> ALL_HITS_COUNT
		0 * _
		restEndpointStatisticsDTO.totalCount == ALL_HITS_COUNT
		restEndpointStatisticsDTO.statistics[0].name == 'Book'
		restEndpointStatisticsDTO.statistics[0].count == BOOK_HITS_COUNT
		restEndpointStatisticsDTO.statistics[1].name == 'Comics'
		restEndpointStatisticsDTO.statistics[1].count == COMICS_HITS_COUNT
	}

}
