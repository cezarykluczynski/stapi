package com.cezarykluczynski.stapi.etl.video_release.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.video.processor.VideoTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseProcessor extends CompositeItemProcessor<PageHeader, VideoRelease> {

	public VideoReleaseProcessor(PageHeaderProcessor pageHeaderProcessor, VideoTemplatePageProcessor videoReleaseTemplatePageProcessor,
			VideoReleaseTemplateProcessor videoReleaseTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, videoReleaseTemplatePageProcessor, videoReleaseTemplateProcessor));
	}

}
