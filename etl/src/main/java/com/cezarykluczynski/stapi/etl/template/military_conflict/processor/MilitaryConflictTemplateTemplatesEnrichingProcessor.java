package com.cezarykluczynski.stapi.etl.template.military_conflict.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilitaryConflictTemplateTemplatesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<List<String>, MilitaryConflictTemplate>> {

	@Override
	public void enrich(EnrichablePair<List<String>, MilitaryConflictTemplate> enrichablePair) throws Exception {
		List<String> templateTitleList = enrichablePair.getInput();
		MilitaryConflictTemplate militaryConflictTemplate = enrichablePair.getOutput();

		militaryConflictTemplate.setEarthConflict(Boolean.TRUE.equals(militaryConflictTemplate.getEarthConflict())
				|| templateTitleList.contains(TemplateTitle.EARTHWARS));
		militaryConflictTemplate.setFederationWar(Boolean.TRUE.equals(militaryConflictTemplate.getFederationWar())
				|| templateTitleList.contains(TemplateTitle.FEDERATION_WARS));
		militaryConflictTemplate.setDominionWarBattle(Boolean.TRUE.equals(militaryConflictTemplate.getDominionWarBattle())
				|| templateTitleList.contains(TemplateTitle.DOMINION_WAR_BATTLES));
		militaryConflictTemplate.setKlingonWar(Boolean.TRUE.equals(militaryConflictTemplate.getKlingonWar())
				|| templateTitleList.contains(TemplateTitle.KLINGON_WARS));
	}

}
