package com.cezarykluczynski.stapi.server.spacecraft.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBase
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPACECRAFTS)
})
class SpacecraftSoapEndpointIntegrationTest extends AbstractSpacecraftEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets spacecraft  by UID"() {
		when:
		SpacecraftFullResponse spacecraftFullResponse = stapiSoapClient.spacecraftPortType
				.getSpacecraftFull(new SpacecraftFullRequest(
				uid: 'SRMA0000003219'
		))

		then:
		spacecraftFullResponse.spacecraft.name == 'USS Shenandoah'
		spacecraftFullResponse.spacecraft.registry == 'NCC-73024'
	}

	void "gets spacecraft by name"() {
		when:
		SpacecraftBaseResponse spacecraftBaseResponse = stapiSoapClient.spacecraftPortType
				.getSpacecraftBase(new SpacecraftBaseRequest(
						name: 'IKS Klothos'
				))
		List<SpacecraftBase> spacecraftBaseList = spacecraftBaseResponse.spacecrafts

		then:
		spacecraftBaseList.size() == 1
		spacecraftBaseList[0].uid == 'SRMA0000008658'
	}

}
