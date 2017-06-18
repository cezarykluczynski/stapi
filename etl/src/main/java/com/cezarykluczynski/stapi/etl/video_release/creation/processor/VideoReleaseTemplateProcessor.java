package com.cezarykluczynski.stapi.etl.video_release.creation.processor;

import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class VideoReleaseTemplateProcessor implements ItemProcessor<VideoTemplate, VideoRelease> {

	private final UidGenerator uidGenerator;

	@Inject
	public VideoReleaseTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public VideoRelease process(VideoTemplate item) throws Exception {
		VideoRelease videoRelease = new VideoRelease();

		videoRelease.setUid(uidGenerator.generateFromPage(item.getPage(), VideoRelease.class));
		videoRelease.setPage(item.getPage());
		videoRelease.setTitle(item.getTitle());

		return videoRelease;
	}

}
