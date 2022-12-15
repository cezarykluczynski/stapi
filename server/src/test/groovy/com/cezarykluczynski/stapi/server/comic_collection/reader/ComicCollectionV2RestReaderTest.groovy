package com.cezarykluczynski.stapi.server.comic_collection.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionV2Full
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionV2FullResponse
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.server.comic_collection.dto.ComicCollectionRestBeanParams
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullRestMapper
import com.cezarykluczynski.stapi.server.comic_collection.query.ComicCollectionRestQuery
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicCollectionV2RestReaderTest extends Specification {

	private static final String UID = 'UID'

	private ComicCollectionRestQuery comicCollectionRestQueryBuilderMock

	private ComicCollectionFullRestMapper comicCollectionFullRestMapperMock

	private ComicCollectionV2RestReader comicCollectionV2RestReader

	void setup() {
		comicCollectionRestQueryBuilderMock = Mock()
		comicCollectionFullRestMapperMock = Mock()
		comicCollectionV2RestReader = new ComicCollectionV2RestReader(comicCollectionRestQueryBuilderMock, comicCollectionFullRestMapperMock)
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicCollectionV2Full comicCollectionV2Full = Mock()
		ComicCollection comicCollection = Mock()
		List<ComicCollection> comicCollectionList = Lists.newArrayList(comicCollection)
		Page<ComicCollection> comicCollectionPage = Mock()

		when:
		ComicCollectionV2FullResponse comicCollectionResponseOutput = comicCollectionV2RestReader.readFull(UID)

		then:
		1 * comicCollectionRestQueryBuilderMock.query(_ as ComicCollectionRestBeanParams) >> {
			ComicCollectionRestBeanParams comicCollectionV2RestBeanParams ->
			assert comicCollectionV2RestBeanParams.uid == UID
			comicCollectionPage
		}
		1 * comicCollectionPage.content >> comicCollectionList
		1 * comicCollectionFullRestMapperMock.mapV2Full(comicCollection) >> comicCollectionV2Full
		0 * _
		comicCollectionResponseOutput.comicCollection == comicCollectionV2Full
	}

	void "requires UID in full request"() {
		when:
		comicCollectionV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
