package com.cezarykluczynski.stapi.etl.character.link.configuration;

import com.cezarykluczynski.stapi.etl.character.common.service.CharactersPageHeadersProvider;
import com.cezarykluczynski.stapi.etl.character.link.processor.CharacterLinkReader;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class CharacterLinkConfiguration {

	@Inject
	private CharactersPageHeadersProvider charactersPageHeadersProvider;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public CharacterLinkReader characterLinkReader() {
		List<PageHeader> characters = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.LINK_CHARACTERS)) {
			characters.addAll(charactersPageHeadersProvider.provide());
		}

		return new CharacterLinkReader(characters);
	}

}
