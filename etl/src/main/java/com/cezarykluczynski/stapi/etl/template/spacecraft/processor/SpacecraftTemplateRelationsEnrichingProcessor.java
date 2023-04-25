package com.cezarykluczynski.stapi.etl.template.spacecraft.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate;
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.starship.processor.ClassTemplateSpacecraftClassesProcessor;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpacecraftTemplateRelationsEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<StarshipTemplate> {

	private final WikitextToEntitiesProcessor wikitextToEntitiesProcessor;

	private final ClassTemplateSpacecraftClassesProcessor classTemplateSpacecraftClassesProcessor;

	@Override
	@SuppressWarnings("NPathComplexity")
	public void enrich(EnrichablePair<Template, StarshipTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		StarshipTemplate starshipTemplate = enrichablePair.getOutput();
		String starshipName = starshipTemplate.getName();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case StarshipTemplateParameter.OWNER:
					List<Organization> ownerList = wikitextToEntitiesProcessor.findOrganizations(value);
					if (!ownerList.isEmpty()) {
						starshipTemplate.setOwner(ownerList.iterator().next());
						if (ownerList.size() > 1) {
							log.info("More than one organization found for spacecraft \"{}\" for owner value \"{}\", using the first value",
									starshipName, value);
						}
					}
					break;
				case StarshipTemplateParameter.OPERATOR:
					List<Organization> operatorList = wikitextToEntitiesProcessor.findOrganizations(value);
					if (!operatorList.isEmpty()) {
						starshipTemplate.setOperator(operatorList.iterator().next());
						if (operatorList.size() > 1) {
							log.info("More than one organization found for spacecraft \"{}\" for operator value \"{}\", using the first value",
									starshipName, value);
						}
					}
					break;
				case StarshipTemplateParameter.AFFILIATION:
					List<Organization> affiliations = wikitextToEntitiesProcessor.findOrganizations(value);
					if (!affiliations.isEmpty()) {
						starshipTemplate.setAffiliation(affiliations.iterator().next());
						if (affiliations.size() > 1) {
							log.info("More than one organization found for spacecraft \"{}\" for affiliation value \"{}\", using the first value",
									starshipName, value);
						}
					}
					break;
				case StarshipTemplateParameter.CLASS:
					List<SpacecraftClass> classList = wikitextToEntitiesProcessor.findSpacecraftClasses(value);
					if (!classList.isEmpty()) {
						setFirstSpacecraftToTemplate(classList, starshipTemplate);
						if (classList.size() > 1) {
							log.info("More than one spacecraft class found for spacecraft \"{}\" for operator value \"{}\", using the first value",
									starshipName, value);
						}
					}
					final Pair<List<SpacecraftClass>, List<SpacecraftType>> pair = classTemplateSpacecraftClassesProcessor.process(part);
					classList = pair.getKey();
					if (!classList.isEmpty() && starshipTemplate.getSpacecraftClass() == null) {
						setFirstSpacecraftToTemplate(classList, starshipTemplate);
						if (classList.size() > 1) {
							log.info("More than one spacecraft class found for spacecraft \"{}\" for operator part \"{}\", using the first value",
									starshipName, part);
						}
					}
					final List<SpacecraftType> spacecraftTypes = pair.getValue();
					starshipTemplate.getSpacecraftTypes().addAll(spacecraftTypes);
					break;
				default:
					break;
			}
		}
	}

	private void setFirstSpacecraftToTemplate(List<SpacecraftClass> classList, StarshipTemplate starshipTemplate) {
		starshipTemplate.setSpacecraftClass(classList.iterator().next());
	}

}
