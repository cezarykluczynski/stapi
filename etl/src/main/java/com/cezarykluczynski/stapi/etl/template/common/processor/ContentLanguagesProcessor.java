package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ContentLanguagesProcessor implements ItemProcessor<String, Set<ContentLanguage>> {

	@Override
	public Set<ContentLanguage> process(String item) throws Exception {
		Set<ContentLanguage> contentLanguageSet = Sets.newHashSet();
		// TODO
		return contentLanguageSet;
	}

}
