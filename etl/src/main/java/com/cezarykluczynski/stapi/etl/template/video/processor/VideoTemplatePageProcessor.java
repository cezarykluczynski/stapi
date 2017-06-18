package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.video_release.creation.service.VideoReleasePageFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class VideoTemplatePageProcessor implements ItemProcessor<Page, VideoTemplate> {

	private final VideoReleasePageFilter videoReleasePageFilter;

	private final PageBindingService pageBindingService;

	@Inject
	public VideoTemplatePageProcessor(VideoReleasePageFilter videoReleasePageFilter, PageBindingService pageBindingService) {
		this.videoReleasePageFilter = videoReleasePageFilter;
		this.pageBindingService = pageBindingService;
	}

	@Override
	public VideoTemplate process(Page item) throws Exception {
		if (videoReleasePageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		VideoTemplate videoTemplate = new VideoTemplate();
		videoTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		videoTemplate.setTitle(TitleUtil.getNameFromTitle(item.getTitle()));

		return videoTemplate;
	}

}
