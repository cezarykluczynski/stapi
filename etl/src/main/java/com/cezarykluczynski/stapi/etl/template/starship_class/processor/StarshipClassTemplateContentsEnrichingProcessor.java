package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.ActivityPeriodDTO;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StarshipClassTemplateContentsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipClassTemplate> {

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final StarshipClassWarpCapableProcessor starshipClassWarpCapableProcessor;

	private final StarshipClassActivityPeriodProcessor starshipClassActivityPeriodProcessor;

	public StarshipClassTemplateContentsEnrichingProcessor(NumberOfPartsProcessor numberOfPartsProcessor,
			StarshipClassWarpCapableProcessor starshipClassWarpCapableProcessor,
			StarshipClassActivityPeriodProcessor starshipClassActivityPeriodProcessor) {
		this.numberOfPartsProcessor = numberOfPartsProcessor;
		this.starshipClassWarpCapableProcessor = starshipClassWarpCapableProcessor;
		this.starshipClassActivityPeriodProcessor = starshipClassActivityPeriodProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, StarshipClassTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (StringUtils.isBlank(value)) {
				continue;
			}

			// TODO: add crew (found 136 times), armament (203), defenses (161)
			// also consider: length (21), diameter (5), mass (10), beam (7), height (6), volume (1), cargo (11)
			switch (key) {
				case StarshipClassTemplateParameter.DECKS:
				case StarshipClassTemplateParameter.T1DECKS:
				case StarshipClassTemplateParameter.T2DECKS:
				case StarshipClassTemplateParameter.T3DECKS:
				case StarshipClassTemplateParameter.T4DECKS:
				case StarshipClassTemplateParameter.T5DECKS:
					Integer numberOfDecks = numberOfPartsProcessor.process(value);
					if (starshipClassTemplate.getNumberOfDecks() == null) {
						starshipClassTemplate.setNumberOfDecks(numberOfDecks);
					} else if (!starshipClassTemplate.getNumberOfDecks().equals(numberOfDecks)) {
						log.info("Number of decks already set for {} to {}, value {} won't be used.",
								starshipClassTemplate.getName(), starshipClassTemplate.getNumberOfDecks(), numberOfDecks);
					}
					break;
				case StarshipClassTemplateParameter.SPEED:
				case StarshipClassTemplateParameter.T1SPEED:
				case StarshipClassTemplateParameter.T2SPEED:
				case StarshipClassTemplateParameter.T3SPEED:
				case StarshipClassTemplateParameter.T4SPEED:
				case StarshipClassTemplateParameter.T5SPEED:
					Boolean warpCapable = starshipClassWarpCapableProcessor.process(value);
					if (starshipClassTemplate.getWarpCapable() == null) {
						starshipClassTemplate.setWarpCapable(warpCapable);
					} else if (!starshipClassTemplate.getWarpCapable().equals(warpCapable)){
						log.info("Warp capable flag already set for {} to {}, value {} won't be used.",
								starshipClassTemplate.getName(), warpCapable, value);
					}
					break;
				case StarshipClassTemplateParameter.ACTIVE:
				case StarshipClassTemplateParameter.T1ACTIVE:
				case StarshipClassTemplateParameter.T2ACTIVE:
				case StarshipClassTemplateParameter.T3ACTIVE:
				case StarshipClassTemplateParameter.T4ACTIVE:
				case StarshipClassTemplateParameter.T5ACTIVE:
					ActivityPeriodDTO activityPeriodDTO = starshipClassActivityPeriodProcessor.process(value);
					final ActivityPeriodDTO currentActivityPeriodDTO = ActivityPeriodDTO
							.of(starshipClassTemplate.getActiveFrom(), starshipClassTemplate.getActiveTo());
					if (currentActivityPeriodDTO.getFrom() == null && currentActivityPeriodDTO.getTo() == null) {
						if (activityPeriodDTO != null) {
							starshipClassTemplate.setActiveFrom(activityPeriodDTO.getFrom());
							starshipClassTemplate.setActiveTo(activityPeriodDTO.getTo());
						}
					} else if (!currentActivityPeriodDTO.equals(activityPeriodDTO)) {
						log.info("Activity period already set for {} to {}, value {} won't be used.",
								starshipClassTemplate.getName(), currentActivityPeriodDTO, activityPeriodDTO);
					}
					break;
				default:
					break;
			}
		}
	}

}
