package com.cezarykluczynski.stapi.etl.element.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementTemplateTitlesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<String>, Element>> {

	@Override
	public void enrich(EnrichablePair<List<String>, Element> enrichablePair) throws Exception {
		List<String> templateTitleList = enrichablePair.getInput();
		Element element = enrichablePair.getOutput();

		element.setGammaSeries(templateTitleList.contains(TemplateTitle.GAMMA_SERIES));
		element.setHypersonicSeries(templateTitleList.contains(TemplateTitle.HYPERSONIC_SERIES));
		element.setMegaSeries(templateTitleList.contains(TemplateTitle.MEGA_SERIES));
		element.setOmegaSeries(templateTitleList.contains(TemplateTitle.OMEGA_SERIES));
		element.setTransonicSeries(templateTitleList.contains(TemplateTitle.TRANSONIC_SERIES));
		element.setWorldSeries(templateTitleList.contains(TemplateTitle.WORLD_SERIES));
	}

}
