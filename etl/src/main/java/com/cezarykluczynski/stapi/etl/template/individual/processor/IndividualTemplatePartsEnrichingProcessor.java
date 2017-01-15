package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class IndividualTemplatePartsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<List<Template.Part>, IndividualTemplate>> {

	private static final String GENDER = "gender";
	private static final String ACTOR = "actor";
	private static final String HEIGHT = "height";
	private static final String WEIGHT = "weight";
	private static final String SERIAL_NUMBER = "serial number";
	private static final String BORN = "born";
	private static final String DIED = "died";
	private static final String MARITAL_STATUS = "marital_status";
	private static final String BLOOD_TYPE = "blood type";

	private PartToGenderProcessor partToGenderProcessor;

	private IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor;

	private IndividualActorLinkingProcessor individualActorLinkingProcessor;

	private IndividualHeightProcessor individualHeightProcessor;

	private IndividualWeightProcessor individualWeightProcessor;

	private IndividualBloodTypeProcessor individualBloodTypeProcessor;

	private MaritalStatusProcessor maritalStatusProcessor;

	@Inject
	public IndividualTemplatePartsEnrichingProcessor(PartToGenderProcessor partToGenderProcessor,
			IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor, IndividualActorLinkingProcessor individualActorLinkingProcessor,
			IndividualHeightProcessor individualHeightProcessor, IndividualWeightProcessor individualWeightProcessor,
			IndividualBloodTypeProcessor individualBloodTypeProcessor, MaritalStatusProcessor maritalStatusProcessor) {
		this.partToGenderProcessor = partToGenderProcessor;
		this.individualLifeBoundaryProcessor = individualLifeBoundaryProcessor;
		this.individualActorLinkingProcessor = individualActorLinkingProcessor;
		this.individualHeightProcessor = individualHeightProcessor;
		this.individualWeightProcessor = individualWeightProcessor;
		this.individualBloodTypeProcessor = individualBloodTypeProcessor;
		this.maritalStatusProcessor = maritalStatusProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<Template.Part>, IndividualTemplate> enrichablePair) throws Exception {
		IndividualTemplate individualTemplate = enrichablePair.getOutput();

		for (Template.Part part : enrichablePair.getInput()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case GENDER:
					individualTemplate.setGender(partToGenderProcessor.process(part));
					break;
				case ACTOR:
					individualActorLinkingProcessor.enrich(EnrichablePair.of(part, individualTemplate));
					break;
				case HEIGHT:
					individualTemplate.setHeight(individualHeightProcessor.process(value));
					break;
				case WEIGHT:
					individualTemplate.setWeight(individualWeightProcessor.process(value));
					break;
				case SERIAL_NUMBER:
					if (StringUtils.isNotBlank(value)) {
						individualTemplate.setSerialNumber(value);
					}
					break;
				case BORN:
					IndividualLifeBoundaryDTO birthBoundaryDTO = individualLifeBoundaryProcessor
							.process(value);
					individualTemplate.setYearOfBirth(birthBoundaryDTO.getYear());
					individualTemplate.setMonthOfBirth(birthBoundaryDTO.getMonth());
					individualTemplate.setDayOfBirth(birthBoundaryDTO.getDay());
					individualTemplate.setPlaceOfBirth(birthBoundaryDTO.getPlace());
					break;
				case DIED:
					IndividualLifeBoundaryDTO deathBoundaryDTO = individualLifeBoundaryProcessor
							.process(value);
					individualTemplate.setYearOfDeath(deathBoundaryDTO.getYear());
					individualTemplate.setMonthOfDeath(deathBoundaryDTO.getMonth());
					individualTemplate.setDayOfDeath(deathBoundaryDTO.getDay());
					individualTemplate.setPlaceOfDeath(deathBoundaryDTO.getPlace());
					break;
				case MARITAL_STATUS:
					individualTemplate.setMaritalStatus(maritalStatusProcessor.process(value));
					break;
				case BLOOD_TYPE:
					individualTemplate.setBloodType(individualBloodTypeProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}
