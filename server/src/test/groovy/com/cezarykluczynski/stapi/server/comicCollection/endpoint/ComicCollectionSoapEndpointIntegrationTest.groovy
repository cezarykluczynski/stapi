package com.cezarykluczynski.stapi.server.comicCollection.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionResponse
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

	@SuppressWarnings('ClosureAsLastMethodParameter')
	void "'The Battle Within' is among collection with 132 to 160 pages that happened between 2365 and 2370"() {
		when:
		ComicCollectionResponse comicCollectionResponse = stapiSoapClient.comicCollectionPortType.getComicCollections(new ComicCollectionRequest(
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
				.map({ it -> it.title })
				.collect(Collectors.toList())

		then:
		titleList.contains 'The Battle Within'
	}

}
