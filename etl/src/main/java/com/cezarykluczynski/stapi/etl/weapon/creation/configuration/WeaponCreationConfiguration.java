package com.cezarykluczynski.stapi.etl.weapon.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.etl.weapon.creation.processor.WeaponReader;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class WeaponCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public WeaponReader weaponReader() {
		List<PageHeader> weapons = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_WEAPONS)) {
			weapons.addAll(categoryApi.getPages(CategoryTitle.WEAPONS, MediaWikiSource.MEMORY_ALPHA_EN));
			weapons.addAll(categoryApi.getPages(CategoryTitle.HAND_HELD_WEAPONS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new WeaponReader(Lists.newArrayList(Sets.newHashSet(weapons)));
	}

}
