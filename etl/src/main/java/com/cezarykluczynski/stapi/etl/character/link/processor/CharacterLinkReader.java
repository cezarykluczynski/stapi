package com.cezarykluczynski.stapi.etl.character.link.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class CharacterLinkReader extends ListItemReader<PageHeader> {

	public CharacterLinkReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of character to link list: {}", list.size());
	}

}
