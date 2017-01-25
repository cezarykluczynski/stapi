package com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor;

import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.mapper.AstronomicalObjectTypeMapper;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class PlanetTemplateProcessor implements ItemProcessor<PlanetTemplate, AstronomicalObject> {

	private GuidGenerator guidGenerator;

	private AstronomicalObjectTypeMapper astronomicalObjectTypeMapper;

	@Inject
	public PlanetTemplateProcessor(GuidGenerator guidGenerator, AstronomicalObjectTypeMapper astronomicalObjectTypeMapper) {
		this.guidGenerator = guidGenerator;
		this.astronomicalObjectTypeMapper = astronomicalObjectTypeMapper;
	}

	@Override
	public AstronomicalObject process(PlanetTemplate item) throws Exception {
		if (item.isProductOfRedirect()) {
			return null;
		}

		AstronomicalObject astronomicalObject = new AstronomicalObject();
		astronomicalObject.setName(item.getName());
		astronomicalObject.setGuid(guidGenerator.generateFromPage(item.getPage(), AstronomicalObject.class));
		astronomicalObject.setPage(item.getPage());
		astronomicalObject.setAstronomicalObjectType(astronomicalObjectTypeMapper.fromEtlToModel(item.getAstronomicalObjectType()));
		return astronomicalObject;
	}

}
