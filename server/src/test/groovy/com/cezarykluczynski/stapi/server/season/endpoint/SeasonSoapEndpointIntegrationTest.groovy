package com.cezarykluczynski.stapi.server.season.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SEASONS)
})
class SeasonSoapEndpointIntegrationTest extends AbstractSeasonEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets season by UID"() {
		when:
		SeasonFullResponse seasonFullResponse = stapiSoapClient.seasonPortType.getSeasonFull(new SeasonFullRequest(
				uid: 'SAMA0000001743'
		))

		then:
		seasonFullResponse.season.title == 'VOY Season 2'
	}

	void "TAS Season 2 is the only season with less than 10 episodes"() {
		when:
		SeasonBaseResponse seasonBaseResponse = stapiSoapClient.seasonPortType.getSeasonBase(new SeasonBaseRequest(
				numberOfEpisodes: new IntegerRange(
						to: 10
				)
		))

		then:
		seasonBaseResponse.seasons.size() == 1
		seasonBaseResponse.seasons[0].title == 'TAS Season 2'
	}

}
