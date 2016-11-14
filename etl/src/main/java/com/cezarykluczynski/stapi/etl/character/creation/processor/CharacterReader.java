package com.cezarykluczynski.stapi.etl.character.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class CharacterReader extends ListItemReader<PageHeader> {

	public CharacterReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of character list: {}", list.size());
	}

}
