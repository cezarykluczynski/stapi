package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.etl.template.common.processor.FullNameToFirstNameProcessor;
import com.cezarykluczynski.stapi.sources.genderize.client.GenderizeClient;
import com.cezarykluczynski.stapi.sources.genderize.dto.NameGenderDTO;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PageToGenderNameProcessor implements ItemProcessor<Page, Gender> {

	private static final float MINIMAL_PROBABILITY = (float) 0.95;
	private static final String MALE = "male";

	private final FullNameToFirstNameProcessor fullNameToFirstNameProcessor;

	private final GenderizeClient genderizeClient;

	public PageToGenderNameProcessor(FullNameToFirstNameProcessor fullNameToFirstNameProcessor,
			GenderizeClient genderizeClient) {
		this.fullNameToFirstNameProcessor = fullNameToFirstNameProcessor;
		this.genderizeClient = genderizeClient;
	}

	@Override
	public Gender process(Page item) throws Exception {
		String name = fullNameToFirstNameProcessor.process(item.getTitle());
		NameGenderDTO nameGenderDTO = genderizeClient.getNameGender(name);

		if (nameGenderDTO == null) {
			log.info("Could not determine gender of \"{}\" using external API because response was invalid", item.getTitle());
			return null;
		}

		String foundGender = nameGenderDTO.getGender();

		if (foundGender == null) {
			log.info("Could not determine gender of \"{}\" using external API", item.getTitle());
			return null;
		}

		Gender gender = MALE.equalsIgnoreCase(foundGender) ? Gender.M : Gender.F;
		final Float probability = nameGenderDTO.getProbability();

		if (probability < MINIMAL_PROBABILITY) {
			log.info("Probability {} of gender {} found in external API for name \"{}\" lower than required {}.",
					probability, gender, name, MINIMAL_PROBABILITY);
			return null;
		} else if (probability < 1.0f) {
			log.info("Gender {} found in external API for \"{}\" with probability {}",
					gender, item.getTitle(), probability);
		}

		return gender;
	}

}
