package com.cezarykluczynski.stapi.etl.video_release.creation.processor;

import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseTemplateProcessor implements ItemProcessor<VideoTemplate, VideoRelease> {

	private final UidGenerator uidGenerator;

	public VideoReleaseTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public VideoRelease process(VideoTemplate item) throws Exception {
		VideoRelease videoRelease = new VideoRelease();

		videoRelease.setUid(uidGenerator.generateFromPage(item.getPage(), VideoRelease.class));
		videoRelease.setPage(item.getPage());
		videoRelease.setTitle(item.getTitle());
		videoRelease.setSeries(item.getSeries());
		videoRelease.setSeason(item.getSeason());
		videoRelease.setFormat(item.getFormat());
		videoRelease.setNumberOfEpisodes(item.getNumberOfEpisodes());
		videoRelease.setNumberOfFeatureLengthEpisodes(item.getNumberOfFeatureLengthEpisodes());
		videoRelease.setNumberOfDataCarriers(item.getNumberOfDataCarriers());
		videoRelease.setRunTime(item.getRunTime());
		videoRelease.setYearFrom(item.getYearFrom());
		videoRelease.setYearTo(item.getYearTo());
		videoRelease.setRegionFreeReleaseDate(item.getRegionFreeReleaseDate());
		videoRelease.setRegion1AReleaseDate(item.getRegion1AReleaseDate());
		videoRelease.setRegion1SlimlineReleaseDate(item.getRegion1SlimlineReleaseDate());
		videoRelease.setRegion2BReleaseDate(item.getRegion2BReleaseDate());
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
		videoRelease.setXboxSmartGlassDigitalRelease(Boolean.TRUE.equals(item.getXboxSmartGlassDigitalRelease()));
		videoRelease.setYouTubeDigitalRelease(Boolean.TRUE.equals(item.getYouTubeDigitalRelease()));
		videoRelease.setNetflixDigitalRelease(Boolean.TRUE.equals(item.getNetflixDigitalRelease()));
		videoRelease.getRatings().addAll(item.getRatings());
		videoRelease.getReferences().addAll(item.getReferences());
		videoRelease.getLanguages().addAll(item.getLanguages());
		videoRelease.getLanguagesSubtitles().addAll(item.getLanguagesSubtitles());
		videoRelease.getLanguagesDubbed().addAll(item.getLanguagesDubbed());

		return videoRelease;
	}

}
