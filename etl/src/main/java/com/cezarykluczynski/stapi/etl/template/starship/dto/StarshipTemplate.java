package com.cezarykluczynski.stapi.etl.template.starship.dto;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import lombok.Data;

@Data
public class StarshipTemplate {

	private String name;

	private Page page;

	private SpacecraftClass spacecraftClass;
}
