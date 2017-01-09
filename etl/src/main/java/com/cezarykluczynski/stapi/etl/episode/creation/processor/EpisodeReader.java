package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class EpisodeReader extends ListItemReader<PageHeader> {

	public EpisodeReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of episodes list: {}", list.size());
	}

}
