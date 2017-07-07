package com.cezarykluczynski.stapi.server.video_release.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFull
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import org.mapstruct.factory.Mappers

class VideoReleaseFullRestMapperTest extends AbstractVideoReleaseMapperTest {

	private VideoReleaseFullRestMapper videoReleaseFullRestMapper

	void setup() {
		videoReleaseFullRestMapper = Mappers.getMapper(VideoReleaseFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		VideoRelease videoRelease = createVideoRelease()

		when:
		VideoReleaseFull videoReleaseFull = videoReleaseFullRestMapper.mapFull(videoRelease)

		then:
		videoReleaseFull.uid == UID
		videoReleaseFull.title == TITLE
		videoReleaseFull.series != null
		videoReleaseFull.season != null
		videoReleaseFull.format.name() == videoRelease.format.name()
		videoReleaseFull.numberOfEpisodes == NUMBER_OF_EPISODES
		videoReleaseFull.numberOfFeatureLengthEpisodes == NUMBER_OF_FEATURE_LENGTH_EPISODES
		videoReleaseFull.numberOfDataCarriers == NUMBER_OF_DATA_CARRIERS
		videoReleaseFull.runTime == RUN_TIME
		videoReleaseFull.yearFrom == YEAR_FROM
		videoReleaseFull.yearTo == YEAR_TO
		videoReleaseFull.regionFreeReleaseDate == REGION_FREE_RELEASE_DATE
		videoReleaseFull.region1AReleaseDate == REGION1_A_RELEASE_DATE
		videoReleaseFull.region1SlimlineReleaseDate == REGION1_SLIMLINE_RELEASE_DATE
		videoReleaseFull.region2BReleaseDate == REGION2_B_RELEASE_DATE
		videoReleaseFull.region2SlimlineReleaseDate == REGION2_SLIMLINE_RELEASE_DATE
		videoReleaseFull.region4AReleaseDate == REGION4_A_RELEASE_DATE
		videoReleaseFull.region4SlimlineReleaseDate == REGION4_SLIMLINE_RELEASE_DATE
		videoReleaseFull.amazonDigitalRelease == AMAZON_DIGITAL_RELEASE
		videoReleaseFull.dailymotionDigitalRelease == DAILYMOTION_DIGITAL_RELEASE
		videoReleaseFull.googlePlayDigitalRelease == GOOGLE_PLAY_DIGITAL_RELEASE
		videoReleaseFull.ITunesDigitalRelease == I_TUNES_DIGITAL_RELEASE
		videoReleaseFull.ultraVioletDigitalRelease == ULTRA_VIOLET_DIGITAL_RELEASE
		videoReleaseFull.vimeoDigitalRelease == VIMEO_DIGITAL_RELEASE
		videoReleaseFull.vuduDigitalRelease == VUDU_DIGITAL_RELEASE
		videoReleaseFull.xboxSmartGlassDigitalRelease == XBOX_SMART_GLASS_DIGITAL
		videoReleaseFull.youTubeDigitalRelease == YOU_TUBE_DIGITAL_RELEASE
		videoReleaseFull.netflixDigitalRelease == NETFLIX_DIGITAL_RELEASE
		videoReleaseFull.references.size() == videoRelease.references.size()
		videoReleaseFull.ratings.size() == videoRelease.ratings.size()
		videoReleaseFull.languages.size() == videoRelease.languages.size()
		videoReleaseFull.languagesSubtitles.size() == videoRelease.languagesSubtitles.size()
		videoReleaseFull.languagesDubbed.size() == videoRelease.languagesDubbed.size()
	}
}
