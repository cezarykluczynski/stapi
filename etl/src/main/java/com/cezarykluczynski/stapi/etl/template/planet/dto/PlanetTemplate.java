package com.cezarykluczynski.stapi.etl.template.planet.dto;

import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;

@Data
public class PlanetTemplate {

	private String name;

	private Page page;

	private AstronomicalObjectType astronomicalObjectType;

}
