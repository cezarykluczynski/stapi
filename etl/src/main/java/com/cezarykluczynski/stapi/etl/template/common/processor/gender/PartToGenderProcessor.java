package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class PartToGenderProcessor implements ItemProcessor<Template.Part, Gender> {

	@Override
	public Gender process(Template.Part item) throws Exception {
		String value = StringUtils.lowerCase(StringUtils.trim(item.getValue()));

		switch (value) {
			case "male":
				return Gender.M;
			case "female":
				return Gender.F;
			default:
				return null;
		}
	}

}
