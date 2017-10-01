package com.cezarykluczynski.stapi.etl.spacecraft.creation.processor;

import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipTemplateProcessor implements ItemProcessor<StarshipTemplate, Spacecraft> {

	private final UidGenerator uidGenerator;

	public StarshipTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Spacecraft process(StarshipTemplate item) throws Exception {
		Spacecraft spacecraft = new Spacecraft();

		spacecraft.setName(item.getName());
		spacecraft.setPage(item.getPage());
		spacecraft.setUid(uidGenerator.generateFromPage(item.getPage(), Spacecraft.class));
		spacecraft.setRegistry(item.getRegistry());
		spacecraft.setSpacecraftClass(item.getSpacecraftClass());
		spacecraft.getSpacecraftTypes().addAll(item.getSpacecraftTypes());
		if (item.getSpacecraftClass() != null) {
			spacecraft.getSpacecraftTypes().addAll(item.getSpacecraftClass().getSpacecraftTypes());
		}
		spacecraft.setOwner(item.getOwner());
		spacecraft.setOperator(item.getOperator());
		spacecraft.setStatus(item.getStatus());
		spacecraft.setDateStatus(item.getDateStatus());

		return spacecraft;
	}

}
