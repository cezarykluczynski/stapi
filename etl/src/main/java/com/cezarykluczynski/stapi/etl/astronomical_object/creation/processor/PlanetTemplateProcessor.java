package com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor;

import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.mapper.AstronomicalObjectTypeMapper;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class PlanetTemplateProcessor implements ItemProcessor<PlanetTemplate, AstronomicalObject> {

	private final UidGenerator uidGenerator;

	private final AstronomicalObjectTypeMapper astronomicalObjectTypeMapper;

	public PlanetTemplateProcessor(UidGenerator uidGenerator, AstronomicalObjectTypeMapper astronomicalObjectTypeMapper) {
		this.uidGenerator = uidGenerator;
		this.astronomicalObjectTypeMapper = astronomicalObjectTypeMapper;
	}

	@Override
	public AstronomicalObject process(PlanetTemplate item) throws Exception {
		AstronomicalObject astronomicalObject = new AstronomicalObject();
		astronomicalObject.setName(item.getName());
		astronomicalObject.setUid(uidGenerator.generateFromPage(item.getPage(), AstronomicalObject.class));
		astronomicalObject.setPage(item.getPage());
		astronomicalObject.setAstronomicalObjectType(astronomicalObjectTypeMapper.fromEtlToModel(item.getAstronomicalObjectType()));
		return astronomicalObject;
	}

}
