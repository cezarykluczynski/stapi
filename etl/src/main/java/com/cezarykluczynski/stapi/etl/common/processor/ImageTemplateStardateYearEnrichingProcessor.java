package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.service.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.StardateYearDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.StardateYearSource;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.StardateYearCandidateDTO;
import com.cezarykluczynski.stapi.etl.template.common.processor.StardateYearProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageTemplateStardateYearEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Template, ImageTemplate>> {

	private static final String S_TITLE = "stitle";
	private static final String WS_DATE = "wsdate";

	private FixedValueProvider<String, StardateYearDTO> stardateYearDTOFixedValueProvider;

	private StardateYearProcessor stardateYearProcessor;

	public ImageTemplateStardateYearEnrichingProcessor(
			FixedValueProvider<String, StardateYearDTO> stardateYearDTOFixedValueProvider,
			StardateYearProcessor stardateYearProcessor) {
		this.stardateYearDTOFixedValueProvider = stardateYearDTOFixedValueProvider;
		this.stardateYearProcessor = stardateYearProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, ImageTemplate> enrichablePair) {
		Template template = enrichablePair.getInput();
		ImageTemplate imageTemplate = enrichablePair.getOutput();
		StardateYearDTO stardateYearDTO = null;

		String title = null;
		boolean stardateFixedValueFound = false;

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case S_TITLE:
					title = value;
					FixedValueHolder<StardateYearDTO> fixedValueHolder
							= stardateYearDTOFixedValueProvider.getSearchedValue(value);
					stardateFixedValueFound = fixedValueHolder.isFound();
					if (stardateFixedValueFound) {
						stardateYearDTO = fixedValueHolder.getValue();
					}
					break;
				case WS_DATE:
					if (!stardateFixedValueFound) {
						try {
							stardateYearDTO = stardateYearProcessor
									.process(StardateYearCandidateDTO.of(value, StardateYearSource.EPISODE, title));
						} catch (Exception e) {
						}
					}
					break;
			}
		}

		if (stardateYearDTO != null) {
			imageTemplate.setStardateFrom(stardateYearDTO.getStardateFrom());
			imageTemplate.setStardateTo(stardateYearDTO.getStardateTo());
			imageTemplate.setYearFrom(stardateYearDTO.getYearFrom());
			imageTemplate.setYearTo(stardateYearDTO.getYearTo());
		}
	}

}
