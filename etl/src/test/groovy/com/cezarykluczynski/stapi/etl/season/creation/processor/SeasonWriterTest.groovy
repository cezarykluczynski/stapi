package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class SeasonWriterTest extends Specification {

	private SeasonRepository seasonRepositoryMock

	private SeasonWriter seasonWriter

	void setup() {
		seasonRepositoryMock = Mock()
		seasonWriter = new SeasonWriter(seasonRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Season season = new Season()
		List<Season> seasonList = Lists.newArrayList(season)

		when:
		seasonWriter.write(new Chunk(seasonList))

		then:
		1 * seasonRepositoryMock.saveAll(seasonList)
		0 * _
	}

}
