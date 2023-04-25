package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.enums.StardateYearSource;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.StardateYearCandidateDTO;
import com.cezarykluczynski.stapi.etl.template.common.processor.StardateYearProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.episode.processor.EpisodeTemplateStardateYearFixedValueProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EpisodeTemplateStardateYearEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<ImageTemplate> {

	private final EpisodeTemplateStardateYearFixedValueProvider episodeTemplateStardateYearFixedValueProvider;

	private final StardateYearProcessor stardateYearProcessor;

	public EpisodeTemplateStardateYearEnrichingProcessor(EpisodeTemplateStardateYearFixedValueProvider episodeTemplateStardateYearFixedValueProvider,
			StardateYearProcessor stardateYearProcessor) {
		this.episodeTemplateStardateYearFixedValueProvider = episodeTemplateStardateYearFixedValueProvider;
		this.stardateYearProcessor = stardateYearProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, ImageTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		ImageTemplate imageTemplate = enrichablePair.getOutput();
		StardateYearDTO stardateYearDTO = null;

		String title = null;
		boolean stardateFixedValueFound = false;

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key == null) {
				continue;
			}

			switch (key) {
				case EpisodeTemplateParameter.TITLE:
					title = value;
					FixedValueHolder<StardateYearDTO> fixedValueHolder = episodeTemplateStardateYearFixedValueProvider.getSearchedValue(value);
					stardateFixedValueFound = fixedValueHolder.isFound();
					if (stardateFixedValueFound) {
						stardateYearDTO = fixedValueHolder.getValue();
					}
					break;
				case EpisodeTemplateParameter.DATE:
					if (!stardateFixedValueFound) {
						stardateYearDTO = stardateYearProcessor.process(StardateYearCandidateDTO.of(value, title, StardateYearSource.EPISODE));
					}
					break;
				default:
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
