package com.cezarykluczynski.stapi.server.episode.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.*
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

	def setup() {
		createSoapClient()
	}

	def "gets episode by title"() {
		when:
		EpisodeResponse episodeResponse = stapiSoapClient.episodePortType.getEpisodes(new EpisodeRequest(
				title: 'All Good Things...'
		))
		List<Episode> episodeList = episodeResponse.episodes

		then:
		episodeList.size() == 1
		episodeList[0].title == 'All Good Things...'
		episodeList[0].series.title == 'Star Trek: The Next Generation'
	}

	def "gets all episodes aired in 1996"() {
		given:
		Integer pageNumber = 0
		Integer pageSize = 100

		when:
		EpisodeResponse episodeResponse = stapiSoapClient.episodePortType.getEpisodes(new EpisodeRequest(
				page: new RequestPage(
						pageNumber: pageNumber,
						pageSize: pageSize
				),
				usAirDate: new DateRange(
						from: XMLGregorianCalendarImpl.createDate(1996, 1, 1, DatatypeConstants.FIELD_UNDEFINED),
						to: XMLGregorianCalendarImpl.createDate(1996, 12, 31, DatatypeConstants.FIELD_UNDEFINED)
				),
				order: new RequestOrder(
						clauses: Lists.newArrayList(
								new RequestOrderClause(
										name: 'usAirDate',
										order: RequestOrderEnum.ASC
						)
						)
				)
		))
		List<Episode> episodeList = episodeResponse.episodes

		then:
		episodeResponse.page.pageNumber == pageNumber
		episodeResponse.page.pageSize == pageSize
		episodeList.size() == 51
		episodeList.stream().filter({ episode -> episode.series.title.equals('Star Trek: Deep Space Nine')}).collect(Collectors.toList()).size() == 26
		episodeList.stream().filter({ episode -> episode.series.title.equals('Star Trek: Voyager')}).collect(Collectors.toList()).size() == 25
	}

}
