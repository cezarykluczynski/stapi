package com.cezarykluczynski.stapi.etl.template.magazine.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagazineTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, MagazineTemplate>> {

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, MagazineTemplate> enrichablePair) throws Exception {
		// TODO
	}

}
