package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.sources.wordpress.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class TradingCardSetReader extends ListItemReader<Page> {

	public TradingCardSetReader(List<Page> list) {
		super(list);
		log.info("Initial size of trading card sets list: {}", list.size());
	}

}
