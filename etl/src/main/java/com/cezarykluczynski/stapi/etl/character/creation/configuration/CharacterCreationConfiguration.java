package com.cezarykluczynski.stapi.etl.character.creation.configuration;

import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader;
import com.cezarykluczynski.stapi.etl.common.service.JobCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.boot.autoconfigure.batch.BatchDatabaseInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class CharacterCreationConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private JobCompletenessDecider jobCompletenessDecider;

	// ensure Spring Batch migrates it's schema before reader is instantiated
	@Inject
	private BatchDatabaseInitializer batchDatabaseInitializer;

	@Bean
	public CharacterReader characterReader() {
		List<PageHeader> characters = Lists.newArrayList();

		if (!jobCompletenessDecider.isStepComplete(JobCompletenessDecider.STEP_004_CREATE_CHARACTERS)) {
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryName.INDIVIDUALS, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryName.MILITARY_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryName.Q_CONTINUUM, MediaWikiSource.MEMORY_ALPHA_EN));
			characters.addAll(categoryApi.getPagesIncludingSubcategories(CategoryName.STARFLEET_PERSONNEL, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new CharacterReader(Lists.newArrayList(Sets.newHashSet(characters)));
	}

}
