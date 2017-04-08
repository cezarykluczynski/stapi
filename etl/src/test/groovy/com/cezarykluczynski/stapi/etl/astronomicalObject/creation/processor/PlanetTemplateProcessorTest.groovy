package com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor

import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType as EtlAstronomicalObjectType
import com.cezarykluczynski.stapi.etl.template.planet.mapper.AstronomicalObjectTypeMapper
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.enums.AstronomicalObjectType as ModelAstronomicalObjectType
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page
import spock.lang.Specification

class PlanetTemplateProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String GUID = 'GUID'
	private static final EtlAstronomicalObjectType ETL_ASTRONOMICAL_OBJECT_TYPE = EtlAstronomicalObjectType.GALAXY
	private static final ModelAstronomicalObjectType MODEL_ASTRONOMICAL_OBJECT_TYPE = ModelAstronomicalObjectType.GALAXY

	private final Page page = Mock()

	private GuidGenerator guidGeneratorMock

	private AstronomicalObjectTypeMapper astronomicalObjectTypeMapperMock

	private PlanetTemplateProcessor planetTemplateProcessor

	void setup() {
		guidGeneratorMock = Mock()
		astronomicalObjectTypeMapperMock = Mock()
		planetTemplateProcessor = new PlanetTemplateProcessor(guidGeneratorMock, astronomicalObjectTypeMapperMock)
	}

	void "returns null when PlanetTemplate is a product of redirect"() {
		given:
		PlanetTemplate planetTemplate = new PlanetTemplate(productOfRedirect: true)

		when:
		AstronomicalObject astronomicalObject = planetTemplateProcessor.process(planetTemplate)

		then:
		astronomicalObject == null
	}

	void "maps PlanetTemplate to AstronomicalObject"() {
		given:
		PlanetTemplate planetTemplate = new PlanetTemplate(
				name: NAME,
				page: page,
				astronomicalObjectType: ETL_ASTRONOMICAL_OBJECT_TYPE)

		when:
		AstronomicalObject astronomicalObject = planetTemplateProcessor.process(planetTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(page, AstronomicalObject) >> GUID
		1 * astronomicalObjectTypeMapperMock.fromEtlToModel(ETL_ASTRONOMICAL_OBJECT_TYPE) >> MODEL_ASTRONOMICAL_OBJECT_TYPE
		0 * _
		astronomicalObject.name == NAME
		astronomicalObject.page == page
		astronomicalObject.guid == GUID
		astronomicalObject.astronomicalObjectType == MODEL_ASTRONOMICAL_OBJECT_TYPE
	}

}
