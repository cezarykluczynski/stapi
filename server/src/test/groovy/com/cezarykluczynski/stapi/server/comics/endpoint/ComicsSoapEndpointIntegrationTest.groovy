package com.cezarykluczynski.stapi.server.comics.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse
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

	void "gets 'Brothers in Darkness' by UID"() {
		when:
		ComicsFullResponse comicsFullResponse = stapiSoapClient.comicsPortType.getComicsFull(new ComicsFullRequest(
				uid: 'CCMA0000085530'
		))

		then:
		comicsFullResponse.comics.title == 'Brothers in Darkness'
		comicsFullResponse.comics.writers[0].name == 'Michael Jan Friedman'
		comicsFullResponse.comics.editors[0].name == 'Margaret Clark'
		comicsFullResponse.comics.publishers[0].name == 'DC Comics'

		when:
		List<String> characterNameList = comicsFullResponse.comics.characters
				.stream()
				.map { it -> it.name }
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
		ComicsBaseResponse comicsBaseResponse = stapiSoapClient.comicsPortType.getComicsBase(new ComicsBaseRequest(
				title: 'Star Trek - Legion of Super-Heroes',
				sort: new RequestSort(
						clauses: Lists.newArrayList(
								new RequestSortClause(
										name: 'title',
										direction: RequestSortDirectionEnum.ASC)))))

		then:
		comicsBaseResponse.comics.size() == 6
		comicsBaseResponse.comics[0].title == 'Star Trek - Legion of Super-Heroes, Issue 1'
		comicsBaseResponse.comics[1].title == 'Star Trek - Legion of Super-Heroes, Issue 2'
		comicsBaseResponse.comics[2].title == 'Star Trek - Legion of Super-Heroes, Issue 3'
		comicsBaseResponse.comics[3].title == 'Star Trek - Legion of Super-Heroes, Issue 4'
		comicsBaseResponse.comics[4].title == 'Star Trek - Legion of Super-Heroes, Issue 5'
		comicsBaseResponse.comics[5].title == 'Star Trek - Legion of Super-Heroes, Issue 6'
	}

}
