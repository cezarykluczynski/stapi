package com.cezarykluczynski.stapi.etl.food.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class FoodProcessor extends CompositeItemProcessor<PageHeader, Food> {

	public FoodProcessor(PageHeaderProcessor pageHeaderProcessor, FoodPageProcessor foodPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, foodPageProcessor));
	}

}

