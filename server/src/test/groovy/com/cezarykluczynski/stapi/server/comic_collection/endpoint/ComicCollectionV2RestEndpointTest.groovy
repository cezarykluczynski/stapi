package com.cezarykluczynski.stapi.server.comic_collection.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionV2FullResponse
import com.cezarykluczynski.stapi.server.comic_collection.reader.ComicCollectionV2RestReader
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class ComicCollectionV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'

	private ComicCollectionV2RestReader comicCollectionV2RestReaderMock

	private ComicCollectionV2RestEndpoint comicCollectionV2RestEndpoint

	void setup() {
		comicCollectionV2RestReaderMock = Mock()
		comicCollectionV2RestEndpoint = new ComicCollectionV2RestEndpoint(comicCollectionV2RestReaderMock)
	}

	void "passes get call to ComicCollectionRestReader"() {
		given:
		ComicCollectionV2FullResponse comicCollectionV2FullResponse = Mock()

		when:
		ComicCollectionV2FullResponse comicCollectionV2FullResponseOutput = comicCollectionV2RestEndpoint.getComicCollection(UID)

		then:
		1 * comicCollectionV2RestReaderMock.readFull(UID) >> comicCollectionV2FullResponse
		comicCollectionV2FullResponseOutput == comicCollectionV2FullResponse
	}

}
