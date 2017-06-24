package com.cezarykluczynski.stapi.etl.video_release.creation.processor

import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import com.cezarykluczynski.stapi.util.AbstractVideoReleaseTest

class VideoReleaseTemplateProcessorTest extends AbstractVideoReleaseTest {

	private static final VideoReleaseFormat VIDEO_RELEASE_FORMAT = VideoReleaseFormat.UMD

	private UidGenerator uidGeneratorMock

	private VideoReleaseTemplateProcessor videoReleaseTemplateProcessor

	private final Page page = Mock()
	private final Series series = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		videoReleaseTemplateProcessor = new VideoReleaseTemplateProcessor(uidGeneratorMock)
	}

	void "converts VideoTemplate to VideoRelease"() {
		given:
		VideoTemplate videoReleaseTemplate = new VideoTemplate(
				page: page,
				title: TITLE,
				series: series,
				format: VIDEO_RELEASE_FORMAT,
				regionFreeReleaseDate: REGION_FREE_RELEASE_DATE,
				region1AReleaseDate: REGION1_A_RELEASE_DATE,
				region1SlimlineReleaseDate: REGION1_SLIMLINE_RELEASE_DATE,
				region2AReleaseDate: REGION2_A_RELEASE_DATE,
				region2SlimlineReleaseDate: REGION2_SLIMLINE_RELEASE_DATE,
				region4AReleaseDate: REGION4_A_RELEASE_DATE,
				region4SlimlineReleaseDate: REGION4_SLIMLINE_RELEASE_DATE,
				amazonDigitalRelease: AMAZON_DIGITAL_RELEASE,
				dailymotionDigitalRelease: DAILYMOTION_DIGITAL_RELEASE,
				googlePlayDigitalRelease: GOOGLE_PLAY_DIGITAL_RELEASE,
				iTunesDigitalRelease: I_TUNES_DIGITAL_RELEASE,
				ultraVioletDigitalRelease: ULTRA_VIOLET_DIGITAL_RELEASE,
				vimeoDigitalRelease: VIMEO_DIGITAL_RELEASE,
				vuduDigitalRelease: VUDU_DIGITAL_RELEASE,
				xboxSmartGlassDigital: XBOX_SMART_GLASS_DIGITAL,
				youTubeDigitalRelease: YOU_TUBE_DIGITAL_RELEASE,
				netflixDigitalRelease: NETFLIX_DIGITAL_RELEASE)

		when:
		VideoRelease videoRelease = videoReleaseTemplateProcessor.process(videoReleaseTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, VideoRelease) >> UID
		0 * _
		videoRelease.uid == UID
		videoRelease.page == page
		videoRelease.title == TITLE
		videoRelease.series == series
		videoRelease.format == VIDEO_RELEASE_FORMAT
		videoRelease.regionFreeReleaseDate == REGION_FREE_RELEASE_DATE
		videoRelease.region1AReleaseDate == REGION1_A_RELEASE_DATE
		videoRelease.region1SlimlineReleaseDate == REGION1_SLIMLINE_RELEASE_DATE
		videoRelease.region2AReleaseDate == REGION2_A_RELEASE_DATE
		videoRelease.region2SlimlineReleaseDate == REGION2_SLIMLINE_RELEASE_DATE
		videoRelease.region4AReleaseDate == REGION4_A_RELEASE_DATE
		videoRelease.region4SlimlineReleaseDate == REGION4_SLIMLINE_RELEASE_DATE
		videoRelease.amazonDigitalRelease == AMAZON_DIGITAL_RELEASE
		videoRelease.dailymotionDigitalRelease == DAILYMOTION_DIGITAL_RELEASE
		videoRelease.googlePlayDigitalRelease == GOOGLE_PLAY_DIGITAL_RELEASE
		videoRelease.ITunesDigitalRelease == I_TUNES_DIGITAL_RELEASE
		videoRelease.ultraVioletDigitalRelease == ULTRA_VIOLET_DIGITAL_RELEASE
		videoRelease.vimeoDigitalRelease == VIMEO_DIGITAL_RELEASE
		videoRelease.vuduDigitalRelease == VUDU_DIGITAL_RELEASE
		videoRelease.xboxSmartGlassDigital == XBOX_SMART_GLASS_DIGITAL
		videoRelease.youTubeDigitalRelease == YOU_TUBE_DIGITAL_RELEASE
		videoRelease.netflixDigitalRelease == NETFLIX_DIGITAL_RELEASE
	}

}
