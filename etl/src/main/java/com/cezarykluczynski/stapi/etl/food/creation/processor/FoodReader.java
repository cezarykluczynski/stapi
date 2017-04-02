package com.cezarykluczynski.stapi.etl.food.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class FoodReader extends ListItemReader<PageHeader> {

	public FoodReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of food list: {}", list.size());
	}

}
