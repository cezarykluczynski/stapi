package com.cezarykluczynski.stapi.server.comic_collection.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

import java.util.stream.Collectors

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_COLLECTIONS)
})
class ComicCollectionSoapEndpointIntegrationTest extends AbstractComicCollectionEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets comic collection by UID"() {
		when:
		ComicCollectionFullResponse comicCollectionFullResponse = stapiSoapClient.comicCollectionPortType
				.getComicCollectionFull(new ComicCollectionFullRequest(uid: 'CLMA0000105753'))

		then:
		comicCollectionFullResponse.comicCollection.title == 'Best of Gary Seven'
	}

	void "'The Battle Within' is among collection with 132 to 160 pages that happened between 2365 and 2370"() {
		when:
		ComicCollectionBaseResponse comicCollectionResponse = stapiSoapClient.comicCollectionPortType
				.getComicCollectionBase(new ComicCollectionBaseRequest(
						numberOfPages: new IntegerRange(
								from: 132,
								to: 160
						),
						year: new IntegerRange(
								from: 2365,
								to: 2370
						)
				))

		then:
		comicCollectionResponse.comicCollections.size() < 5

		when:
		List<String> titleList = comicCollectionResponse.comicCollections
				.stream()
				.map { it -> it.title }
				.collect(Collectors.toList())

		then:
		titleList.contains 'The Battle Within'
	}

}
