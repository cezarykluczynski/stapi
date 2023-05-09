package com.cezarykluczynski.stapi.etl.comic_strip.creation.processor

import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.comic_strip.repository.ComicStripRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class ComicStripWriterTest extends Specification {

	private ComicStripRepository comicStripRepositoryMock

	private ComicStripWriter comicStripWriterMock

	void setup() {
		comicStripRepositoryMock = Mock()
		comicStripWriterMock = new ComicStripWriter(comicStripRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		ComicStrip comicStrip = new ComicStrip()
		List<ComicStrip> comicStripList = Lists.newArrayList(comicStrip)

		when:
		comicStripWriterMock.write(new Chunk(comicStripList))

		then:
		1 * comicStripRepositoryMock.saveAll(comicStripList)
		0 * _
	}

}
