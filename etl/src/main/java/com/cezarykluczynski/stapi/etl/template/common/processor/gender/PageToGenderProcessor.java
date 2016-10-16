package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class PageToGenderProcessor implements ItemProcessor<Page, Gender> {

	private PageToGenderPronounProcessor pageToGenderPronounProcessor;

	private PageToGenderRoleProcessor pageToGenderRoleProcessor;

	private PageToGenderSupplementaryProcessor pageToGenderSupplementaryProcessor;

	private PageToGenderNameProcessor pageToGenderNameProcessor;

	@Inject
	public PageToGenderProcessor(PageToGenderPronounProcessor pageToGenderPronounProcessor,
			PageToGenderRoleProcessor pageToGenderRoleProcessor,
			PageToGenderSupplementaryProcessor pageToGenderSupplementaryProcessor,
			PageToGenderNameProcessor pageToGenderNameProcessor) {
		this.pageToGenderPronounProcessor = pageToGenderPronounProcessor;
		this.pageToGenderRoleProcessor = pageToGenderRoleProcessor;
		this.pageToGenderSupplementaryProcessor = pageToGenderSupplementaryProcessor;
		this.pageToGenderNameProcessor = pageToGenderNameProcessor;
	}

	@Override
	public Gender process(Page item) throws Exception {
		PageToGenderSupplementaryProcessor.Finding finding = pageToGenderSupplementaryProcessor.process(item);

		if (finding.isFound()) {
			return finding.getGender();
		}

		if(item.getWikitext() != null) {
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

		return null;
	}

}
