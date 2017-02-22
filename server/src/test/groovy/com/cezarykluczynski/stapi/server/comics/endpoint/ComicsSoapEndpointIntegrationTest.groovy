package com.cezarykluczynski.stapi.server.comics.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

import java.util.stream.Collectors

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMICS)
})
class ComicsSoapEndpointIntegrationTest extends AbstractComicsEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	@SuppressWarnings('ClosureAsLastMethodParameter')
	void "gets 'Brothers in Darkness' by GUID"() {
		when:
		ComicsResponse comicsResponse = stapiSoapClient.comicsPortType.getComics(new ComicsRequest(
				guid: 'CCMA0000085530'
		))

		then:
		comicsResponse.comics.size() == 1
		comicsResponse.comics[0].title == 'Brothers in Darkness'
		comicsResponse.comics[0].writerHeaders[0].name == 'Michael Jan Friedman'
		comicsResponse.comics[0].editorHeaders[0].name == 'Margaret Clark'
		comicsResponse.comics[0].publisherHeaders[0].name == 'DC Comics'

		when:
		List<String> characterNameList = comicsResponse.comics[0].characterHeaders
				.stream()
				.map({ it -> it.name })
				.collect(Collectors.toList())

		then:
		characterNameList.contains 'Jean-Luc Picard'
		characterNameList.contains 'William T. Riker'
		characterNameList.contains 'Data'
		characterNameList.contains 'Worf'
		characterNameList.contains 'Geordi La Forge'
		characterNameList.contains 'Deanna Troi'
		characterNameList.contains 'Beverly Crusher'
		characterNameList.contains 'Wesley Crusher'
	}

	void "gets six issues of 'Star Trek - Legion of Super-Heroes'"() {
		when:
		ComicsResponse comicsResponse = stapiSoapClient.comicsPortType.getComics(new ComicsRequest(
				title: 'Star Trek - Legion of Super-Heroes',
				sort: new RequestSort(
						clauses: Lists.newArrayList(
								new RequestSortClause(
										name: 'title',
										direction: RequestSortDirectionEnum.ASC)))))

		then:
		comicsResponse.comics.size() == 6
		comicsResponse.comics[0].title == 'Star Trek - Legion of Super-Heroes, Issue 1'
		comicsResponse.comics[1].title == 'Star Trek - Legion of Super-Heroes, Issue 2'
		comicsResponse.comics[2].title == 'Star Trek - Legion of Super-Heroes, Issue 3'
		comicsResponse.comics[3].title == 'Star Trek - Legion of Super-Heroes, Issue 4'
		comicsResponse.comics[4].title == 'Star Trek - Legion of Super-Heroes, Issue 5'
		comicsResponse.comics[5].title == 'Star Trek - Legion of Super-Heroes, Issue 6'
	}

}
