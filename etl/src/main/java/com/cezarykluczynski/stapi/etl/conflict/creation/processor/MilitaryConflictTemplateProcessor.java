package com.cezarykluczynski.stapi.etl.conflict.creation.processor;

import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MilitaryConflictTemplateProcessor implements ItemProcessor<MilitaryConflictTemplate, Conflict> {

	private final UidGenerator uidGenerator;

	public MilitaryConflictTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Conflict process(MilitaryConflictTemplate item) throws Exception {
		Conflict conflict = new Conflict();

		conflict.setName(item.getName());
		conflict.setPage(item.getPage());
		conflict.setUid(uidGenerator.generateFromPage(item.getPage(), Conflict.class));
		conflict.setYearFrom(item.getYearFrom());
		conflict.setYearTo(item.getYearTo());
		conflict.setEarthConflict(Boolean.TRUE.equals(item.getEarthConflict()));
		conflict.setFederationWar(Boolean.TRUE.equals(item.getFederationWar()));
		conflict.setKlingonWar(Boolean.TRUE.equals(item.getKlingonWar()));
		conflict.setDominionWarBattle(Boolean.TRUE.equals(item.getDominionWarBattle()));
		conflict.setAlternateReality(Boolean.TRUE.equals(item.getAlternateReality()));
		conflict.getLocations().addAll(item.getLocations());
		conflict.getFirstSideBelligerents().addAll(item.getFirstSideBelligerents());
		conflict.getSecondSideBelligerents().addAll(item.getSecondSideBelligerents());
		conflict.getFirstSideCommanders().addAll(item.getFirstSideCommanders());
		conflict.getSecondSideCommanders().addAll(item.getSecondSideCommanders());

		return conflict;
	}

}
