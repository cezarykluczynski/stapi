package com.cezarykluczynski.stapi.etl.video_game.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.video_game.processor.VideoGameTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class VideoGameProcessor extends CompositeItemProcessor<PageHeader, VideoGame> {

	public VideoGameProcessor(PageHeaderProcessor pageHeaderProcessor, VideoGameTemplatePageProcessor videoGameTemplatePageProcessor,
			VideoGameTemplateProcessor videoGameTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, videoGameTemplatePageProcessor, videoGameTemplateProcessor));
	}

}
