package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class VideoGameTemplatePageProcessor implements ItemProcessor<Page, VideoGameTemplate> {

	@Override
	public VideoGameTemplate process(Page item) throws Exception {
		// TODO
		return null;
	}

}
