package com.cezarykluczynski.stapi.etl.magazine.creation.processor

import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class MagazineWriterTest extends Specification {

	private MagazineRepository magazineRepositoryMock

	private MagazineWriter magazineWriter

	void setup() {
		magazineRepositoryMock = Mock()
		magazineWriter = new MagazineWriter(magazineRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Magazine magazine = new Magazine()
		List<Magazine> magazineList = Lists.newArrayList(magazine)

		when:
		magazineWriter.write(new Chunk(magazineList))

		then:
		1 * magazineRepositoryMock.saveAll(magazineList)
		0 * _
	}

}
