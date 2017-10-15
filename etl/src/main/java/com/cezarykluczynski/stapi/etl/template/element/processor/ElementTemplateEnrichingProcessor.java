package com.cezarykluczynski.stapi.etl.template.element.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.element.dto.ElementTemplateParameter;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementTemplateEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, Element>> {

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, Element> enrichablePair) throws Exception {
		List<Template.Part> templatePartList = enrichablePair.getInput();
		Element element = enrichablePair.getOutput();

		for (Template.Part part : templatePartList) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case ElementTemplateParameter.SYMBOL:
					element.setSymbol(value);
					break;
				case ElementTemplateParameter.A_NUM:
					element.setAtomicNumber(Ints.tryParse(value));
					break;
				case ElementTemplateParameter.A_WGT:
					element.setAtomicWeight(Ints.tryParse(value));
					break;
				default:
					break;
			}
		}
	}

}
