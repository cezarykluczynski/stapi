package com.cezarykluczynski.stapi.etl.video_release.creation.processor

import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.season.entity.Season
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
	private final Season season = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		videoReleaseTemplateProcessor = new VideoReleaseTemplateProcessor(uidGeneratorMock)
	}

	void "converts VideoTemplate to VideoRelease"() {
		given:
		VideoTemplate videoTemplate = new VideoTemplate(
				page: page,
				title: TITLE,
				series: series,
				season: season,
				format: VIDEO_RELEASE_FORMAT,
				numberOfEpisodes: NUMBER_OF_EPISODES,
				numberOfFeatureLengthEpisodes: NUMBER_OF_FEATURE_LENGTH_EPISODES,
				numberOfDataCarriers: NUMBER_OF_DATA_CARRIERS,
				runTime: RUN_TIME,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				regionFreeReleaseDate: REGION_FREE_RELEASE_DATE,
				region1AReleaseDate: REGION1_A_RELEASE_DATE,
				region1SlimlineReleaseDate: REGION1_SLIMLINE_RELEASE_DATE,
				region2BReleaseDate: REGION2_B_RELEASE_DATE,
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
				xboxSmartGlassDigitalRelease: XBOX_SMART_GLASS_DIGITAL,
				youTubeDigitalRelease: YOU_TUBE_DIGITAL_RELEASE,
				netflixDigitalRelease: NETFLIX_DIGITAL_RELEASE,
				references: createSetOfRandomNumberOfMocks(Reference),
				ratings: createSetOfRandomNumberOfMocks(ContentRating),
				languages: createSetOfRandomNumberOfMocks(ContentLanguage),
				languagesSubtitles: createSetOfRandomNumberOfMocks(ContentLanguage),
				languagesDubbed: createSetOfRandomNumberOfMocks(ContentLanguage))

		when:
		VideoRelease videoRelease = videoReleaseTemplateProcessor.process(videoTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, VideoRelease) >> UID
		0 * _
		videoRelease.uid == UID
		videoRelease.page == page
		videoRelease.title == TITLE
		videoRelease.series == series
		videoRelease.season == season
		videoRelease.format == VIDEO_RELEASE_FORMAT
		videoRelease.numberOfEpisodes == NUMBER_OF_EPISODES
		videoRelease.numberOfFeatureLengthEpisodes == NUMBER_OF_FEATURE_LENGTH_EPISODES
		videoRelease.numberOfDataCarriers == NUMBER_OF_DATA_CARRIERS
		videoRelease.runTime == RUN_TIME
		videoRelease.yearFrom == YEAR_FROM
		videoRelease.yearTo == YEAR_TO
		videoRelease.regionFreeReleaseDate == REGION_FREE_RELEASE_DATE
		videoRelease.region1AReleaseDate == REGION1_A_RELEASE_DATE
		videoRelease.region1SlimlineReleaseDate == REGION1_SLIMLINE_RELEASE_DATE
		videoRelease.region2BReleaseDate == REGION2_B_RELEASE_DATE
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
		videoRelease.xboxSmartGlassDigitalRelease == XBOX_SMART_GLASS_DIGITAL
		videoRelease.youTubeDigitalRelease == YOU_TUBE_DIGITAL_RELEASE
		videoRelease.netflixDigitalRelease == NETFLIX_DIGITAL_RELEASE
		videoRelease.references.size() == videoTemplate.references.size()
		videoRelease.ratings.size() == videoTemplate.ratings.size()
		videoRelease.languages.size() == videoTemplate.languages.size()
		videoRelease.languagesSubtitles.size() == videoTemplate.languagesSubtitles.size()
		videoRelease.languagesDubbed.size() == videoTemplate.languagesDubbed.size()
	}

}
