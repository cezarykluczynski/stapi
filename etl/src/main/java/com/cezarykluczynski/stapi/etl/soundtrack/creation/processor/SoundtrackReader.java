package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class SoundtrackReader extends ListItemReader<PageHeader> {

	public SoundtrackReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of soundtracks list: {}", list.size());
	}

}
