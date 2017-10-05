package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBase
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl
import spock.lang.Requires

import javax.xml.datatype.DatatypeConstants
import java.util.stream.Collectors

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_EPISODES)
})
class EpisodeSoapEndpointIntegrationTest extends AbstractEpisodeEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets episode by UID"() {
		when:
		EpisodeFullResponse episodeFullResponse = stapiSoapClient.episodePortType.getEpisodeFull(new EpisodeFullRequest(
				uid: 'EPMA0000001413'
		))

		then:
		episodeFullResponse.episode.title == 'True Q'
	}

	void "gets episode by title"() {
		when:
		EpisodeBaseResponse episodeBaseResponse = stapiSoapClient.episodePortType.getEpisodeBase(new EpisodeBaseRequest(
				title: 'All Good Things...'
		))
		List<EpisodeBase> episodeBaseList = episodeBaseResponse.episodes

		then:
		episodeBaseList.size() == 1
		episodeBaseList[0].title == 'All Good Things...'
		episodeBaseList[0].series.title == 'Star Trek: The Next Generation'
	}

	void "gets all episodes aired in 1996"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 100

		when:
		EpisodeBaseResponse episodeResponse = stapiSoapClient.episodePortType.getEpisodeBase(new EpisodeBaseRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize
				),
				usAirDate: new DateRange(
						from: XMLGregorianCalendarImpl.createDate(1996, 1, 1, DatatypeConstants.FIELD_UNDEFINED),
						to: XMLGregorianCalendarImpl.createDate(1996, 12, 31, DatatypeConstants.FIELD_UNDEFINED)
				),
				sort: new RequestSort(
						clauses: Lists.newArrayList(
								new RequestSortClause(
										name: 'usAirDate',
										direction: RequestSortDirectionEnum.ASC)))))
		List<EpisodeBase> episodeBaseList = episodeResponse.episodes

		then:
		episodeResponse.page.pageNumber == pageNumber
		episodeResponse.page.pageSize == pageSize
		episodeBaseList.size() == 52
		episodeBaseList.stream().filter { episode -> episode.series.title == 'Star Trek: Deep Space Nine' }.collect(Collectors.toList()).size() == 26
		episodeBaseList.stream().filter { episode -> episode.series.title == 'Star Trek: Voyager' }.collect(Collectors.toList()).size() == 26
	}

}
