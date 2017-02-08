package com.cezarykluczynski.stapi.etl.comics.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class ComicsReader extends ListItemReader<PageHeader> {

	public ComicsReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of comics list: {}", list.size());
	}

}
