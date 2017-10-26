package com.cezarykluczynski.stapi.etl.template.spacecraft.processor;

import com.cezarykluczynski.stapi.etl.template.util.PatternDictionary;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpacecraftRegistryProcessor implements ItemProcessor<String, String> {

	private final WikitextApi wikitextApi;

	public SpacecraftRegistryProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public String process(String item) throws Exception {
		if (StringUtils.isBlank(item)) {
			return null;
		}

		String wikitextWithoutLinks = wikitextApi.getWikitextWithoutLinks(item);
		List<String> wikitextWithoutLinksParts = Lists.newArrayList(wikitextWithoutLinks.split(PatternDictionary.BR));
		return wikitextWithoutLinksParts.get(0);
	}

}
