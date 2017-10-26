package com.cezarykluczynski.stapi.etl.video_release.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoReleaseWriter implements ItemWriter<VideoRelease> {

	private final VideoReleaseRepository videoReleaseRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public VideoReleaseWriter(VideoReleaseRepository videoReleaseRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.videoReleaseRepository = videoReleaseRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends VideoRelease> items) throws Exception {
		videoReleaseRepository.save(process(items));
	}

	private List<VideoRelease> process(List<? extends VideoRelease> videoReleaseList) {
		List<VideoRelease> comicsListWithoutExtends = fromExtendsListToVideoReleaseList(videoReleaseList);
		return filterDuplicates(comicsListWithoutExtends);
	}

	private List<VideoRelease> fromExtendsListToVideoReleaseList(List<? extends VideoRelease> videoReleases) {
		return videoReleases
				.stream()
				.map(pageAware -> (VideoRelease) pageAware)
				.collect(Collectors.toList());
	}

	private List<VideoRelease> filterDuplicates(List<VideoRelease> videoReleaseList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(videoReleaseList.stream()
				.map(videoRelease -> (PageAware) videoRelease)
				.collect(Collectors.toList()), VideoRelease.class).stream()
				.map(pageAware -> (VideoRelease) pageAware)
				.collect(Collectors.toList());
	}

}
