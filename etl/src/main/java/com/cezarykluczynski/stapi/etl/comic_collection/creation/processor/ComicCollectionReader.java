package com.cezarykluczynski.stapi.etl.comic_collection.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class ComicCollectionReader extends ListItemReader<PageHeader> {

	public ComicCollectionReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of comic collection list: {}", list.size());
	}

}
