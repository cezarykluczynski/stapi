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
		videoRelease.setFormat(item.getFormat());
		videoRelease.setRegionFreeReleaseDate(item.getRegionFreeReleaseDate());
		videoRelease.setRegion1AReleaseDate(item.getRegion1AReleaseDate());
		videoRelease.setRegion1SlimlineReleaseDate(item.getRegion1SlimlineReleaseDate());
		videoRelease.setRegion2AReleaseDate(item.getRegion2AReleaseDate());
		videoRelease.setRegion2SlimlineReleaseDate(item.getRegion2SlimlineReleaseDate());
		videoRelease.setRegion4AReleaseDate(item.getRegion4AReleaseDate());
		videoRelease.setRegion4SlimlineReleaseDate(item.getRegion4SlimlineReleaseDate());
		videoRelease.setAmazonDigitalRelease(Boolean.TRUE.equals(item.getAmazonDigitalRelease()));
		videoRelease.setDailymotionDigitalRelease(Boolean.TRUE.equals(item.getDailymotionDigitalRelease()));
		videoRelease.setGooglePlayDigitalRelease(Boolean.TRUE.equals(item.getGooglePlayDigitalRelease()));
		videoRelease.setITunesDigitalRelease(Boolean.TRUE.equals(item.getITunesDigitalRelease()));
		videoRelease.setUltraVioletDigitalRelease(Boolean.TRUE.equals(item.getUltraVioletDigitalRelease()));
		videoRelease.setVimeoDigitalRelease(Boolean.TRUE.equals(item.getVimeoDigitalRelease()));
		videoRelease.setVuduDigitalRelease(Boolean.TRUE.equals(item.getVuduDigitalRelease()));
		videoRelease.setXboxSmartGlassDigital(Boolean.TRUE.equals(item.getXboxSmartGlassDigital()));
		videoRelease.setYouTubeDigitalRelease(Boolean.TRUE.equals(item.getYouTubeDigitalRelease()));
		videoRelease.setNetflixDigitalRelease(Boolean.TRUE.equals(item.getNetflixDigitalRelease()));

		return videoRelease;
	}

}
