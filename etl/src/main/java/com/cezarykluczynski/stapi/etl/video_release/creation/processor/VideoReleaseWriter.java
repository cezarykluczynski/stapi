package com.cezarykluczynski.stapi.etl.video_release.creation.processor;

import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseWriter implements ItemWriter<VideoRelease> {

	private final VideoReleaseRepository videoReleaseRepository;

	public VideoReleaseWriter(VideoReleaseRepository videoReleaseRepository) {
		this.videoReleaseRepository = videoReleaseRepository;
	}

	@Override
	public void write(Chunk<? extends VideoRelease> items) throws Exception {
		videoReleaseRepository.saveAll(items.getItems());
	}

}
