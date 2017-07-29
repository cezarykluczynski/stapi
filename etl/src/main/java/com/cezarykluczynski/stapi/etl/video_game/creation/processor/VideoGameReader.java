package com.cezarykluczynski.stapi.etl.video_game.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class VideoGameReader extends ListItemReader<PageHeader> {

	public VideoGameReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of video games list: {}", list.size());
	}

}
