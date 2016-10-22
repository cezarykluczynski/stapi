package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.wiki.api.CategoryApi;
import com.cezarykluczynski.stapi.wiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class PerformerCreationConfiguration {

	@Inject
	private CategoryApi categoryApi;

	@Bean
	public PerformerReader performerReader() {
		List<PageHeader> performers = Lists.newArrayList();

		performers.addAll(categoryApi.getPages(CategoryName.PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.ANIMAL_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.DIS_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.DS9_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.ENT_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.FILM_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.STAND_INS));
		performers.addAll(categoryApi.getPages(CategoryName.STUNT_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.TAS_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.TNG_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.TOS_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.VIDEO_GAME_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.VOICE_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.VOY_PERFORMERS));

		return new PerformerReader(Lists.newArrayList(Sets.newHashSet(performers)));
	}

}
