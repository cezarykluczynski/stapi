package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.etl.template.common.processor.FullNameToFirstNameProcessor;
import com.cezarykluczynski.stapi.sources.genderize.client.GenderizeClient;
import com.cezarykluczynski.stapi.sources.genderize.dto.NameGender;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class PageToGenderNameProcessor implements ItemProcessor<Page, Gender> {

	private static final float MINIMAL_PROBABILITY = (float) 0.95;

	private FullNameToFirstNameProcessor fullNameToFirstNameProcessor;

	private GenderizeClient genderizeClient;

	@Inject
	public PageToGenderNameProcessor(FullNameToFirstNameProcessor fullNameToFirstNameProcessor,
			GenderizeClient genderizeClient) {
		this.fullNameToFirstNameProcessor = fullNameToFirstNameProcessor;
		this.genderizeClient = genderizeClient;
	}

	@Override
	public Gender process(Page item) throws Exception {
		String name = fullNameToFirstNameProcessor.process(item.getTitle());
		NameGender nameGender = genderizeClient.getNameGender(name);

		if (nameGender == null) {
			log.warn("Could not determine gender of {} using external API because response was invalid",
					item.getTitle());
			return null;
		}

		String foundGender = nameGender.getGender();

		if (foundGender == null) {
			log.warn("Could not determine gender of {} using external API", item.getTitle());
			return null;
		}

		Gender gender = foundGender.compareToIgnoreCase("male") == 0 ? Gender.M : Gender.F;

		log.info("Gender {} found in external API for {} with probability {}",
				gender, item.getTitle(), nameGender.getProbability());

		if (nameGender.getProbability() < MINIMAL_PROBABILITY) {
			log.warn("Probability of gender {} found in external API for name {} lower than required.", gender, name);
			return null;
		}

		return gender;
	}

}
