package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.etl.template.common.factory.ContentLanguageFactory;
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ContentLanguagesProcessor implements ItemProcessor<String, Set<ContentLanguage>> {

	private final ContentLanguageFactory contentLanguageFactory;

	public ContentLanguagesProcessor(ContentLanguageFactory contentLanguageFactory) {
		this.contentLanguageFactory = contentLanguageFactory;
	}

	@Override
	public Set<ContentLanguage> process(String item) throws Exception {
		Set<ContentLanguage> contentLanguageSet = Sets.newHashSet();

		if (StringUtils.isEmpty(item)) {
			return contentLanguageSet;
		}

		List<String> languageNames = Lists.newArrayList(item.split("(,|<br\\s?/?>)"));

		languageNames.forEach(languageName -> {
			Optional<ContentLanguage> contentLanguageOptional = contentLanguageFactory.createForName(StringUtils.trim(languageName));
			contentLanguageOptional.ifPresent(contentLanguageSet::add);
		});

		return contentLanguageSet;
	}

}
