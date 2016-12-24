package com.cezarykluczynski.stapi.etl.movie.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class MovieReader extends ListItemReader<PageHeader> {

	public MovieReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of movies list: {}", list.size());
	}

}
