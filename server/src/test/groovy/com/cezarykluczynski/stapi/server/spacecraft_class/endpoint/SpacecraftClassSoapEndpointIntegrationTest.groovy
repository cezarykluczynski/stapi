package com.cezarykluczynski.stapi.server.spacecraft_class.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBase
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SPACECRAFT_CLASSES)
})
class SpacecraftClassSoapEndpointIntegrationTest extends AbstractSpacecraftClassEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets spacecraft class by UID"() {
		when:
		SpacecraftClassFullResponse spacecraftClassFullResponse = stapiSoapClient.spacecraftClassPortType
				.getSpacecraftClassFull(new SpacecraftClassFullRequest(
				uid: 'SCMA0000071768'
		))

		then:
		spacecraftClassFullResponse.spacecraftClass.name == 'Vulcan science vessel'
		spacecraftClassFullResponse.spacecraftClass.species.name == 'Vulcan'
	}

	void "gets spacecraft class by name"() {
		when:
		SpacecraftClassBaseResponse spacecraftClassBaseResponse = stapiSoapClient.spacecraftClassPortType
				.getSpacecraftClassBase(new SpacecraftClassBaseRequest(
						name: 'Malurian starship'
				))
		List<SpacecraftClassBase> spacecraftClassBaseList = spacecraftClassBaseResponse.spacecraftClasss

		then:
		spacecraftClassBaseList.size() == 1
		spacecraftClassBaseList[0].uid == 'SCMA0000040720'
	}

	void "'Franklin type' is among warp-capable spacecraft types from alternate-reality"() {
		when:
		SpacecraftClassBaseResponse spacecraftClassBaseResponse = stapiSoapClient.spacecraftClassPortType
				.getSpacecraftClassBase(new SpacecraftClassBaseRequest(
						warpCapable: true,
						alternateReality: true
				))
		List<SpacecraftClassBase> spacecraftClassBaseList = spacecraftClassBaseResponse.spacecraftClasss

		then:
		spacecraftClassBaseList.stream()
				.anyMatch { it.name == 'Franklin type' }
	}

}
