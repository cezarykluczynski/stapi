package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FullNameToFirstNameProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		List<String> nameParts = Lists.newArrayList(StringUtils.split(item, " "));

		if (nameParts.size() == 1) {
			return nameParts.get(0);
		}

		if (nameParts.get(0).endsWith(".")) {
			return nameParts.get(1);
		}

		return nameParts.get(0);
	}

}
