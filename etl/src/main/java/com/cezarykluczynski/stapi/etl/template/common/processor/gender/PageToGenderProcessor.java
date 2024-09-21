package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageToGenderProcessor implements ItemProcessor<Page, Gender> {

	private final PageToGenderPronounProcessor pageToGenderPronounProcessor;

	private final PageToGenderRoleProcessor pageToGenderRoleProcessor;

	private final RealWorldPersonGenderFixedValueProvider realWorldPersonGenderFixedValueProvider;

	private final PageToGenderNameProcessor pageToGenderNameProcessor;

	@Override
	public Gender process(Page item) throws Exception {
		String pageTitle = item.getTitle();
		FixedValueHolder<Gender> fixedValueHolder = realWorldPersonGenderFixedValueProvider.getSearchedValue(pageTitle);

		if (fixedValueHolder.isFound()) {
			return fixedValueHolder.getValue();
		}

		if (item.getWikitext() != null) {
			Gender genderFromPronouns = pageToGenderPronounProcessor.process(item);

			if (genderFromPronouns != null) {
				return genderFromPronouns;
			}

			Gender genderFromRole = pageToGenderRoleProcessor.process(item);

			if (genderFromRole != null) {
				return genderFromRole;
			}
		}

		Gender genderFromName = pageToGenderNameProcessor.process(item);

		if (genderFromName != null) {
			return genderFromName;
		}

		log.info("Could not determine gender of \"{}\".", pageTitle);
		return null;
	}

}
