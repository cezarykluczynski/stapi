package com.cezarykluczynski.stapi.server.video_release.mapper

import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseFull
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2Full
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
		videoReleaseFull.series.title == 'SERIES_1'
		videoReleaseFull.season != null
		videoReleaseFull.season.title == 'SEASON_1'
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
		videoReleaseFull.itunesDigitalRelease == I_TUNES_DIGITAL_RELEASE
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		VideoRelease videoRelease = createVideoRelease()

		when:
		VideoReleaseV2Full videoReleaseV2Full = videoReleaseFullRestMapper.mapV2Full(videoRelease)

		then:
		videoReleaseV2Full.uid == UID
		videoReleaseV2Full.title == TITLE
		videoReleaseV2Full.series.size() == videoRelease.series.size()
		videoReleaseV2Full.seasons.size() == videoRelease.seasons.size()
		videoReleaseV2Full.movies.size() == videoRelease.movies.size()
		videoReleaseV2Full.format.name() == videoRelease.format.name()
		videoReleaseV2Full.numberOfEpisodes == NUMBER_OF_EPISODES
		videoReleaseV2Full.numberOfFeatureLengthEpisodes == NUMBER_OF_FEATURE_LENGTH_EPISODES
		videoReleaseV2Full.numberOfDataCarriers == NUMBER_OF_DATA_CARRIERS
		videoReleaseV2Full.runTime == RUN_TIME
		videoReleaseV2Full.yearFrom == YEAR_FROM
		videoReleaseV2Full.yearTo == YEAR_TO
		videoReleaseV2Full.regionFreeReleaseDate == REGION_FREE_RELEASE_DATE
		videoReleaseV2Full.region1AReleaseDate == REGION1_A_RELEASE_DATE
		videoReleaseV2Full.region1SlimlineReleaseDate == REGION1_SLIMLINE_RELEASE_DATE
		videoReleaseV2Full.region2BReleaseDate == REGION2_B_RELEASE_DATE
		videoReleaseV2Full.region2SlimlineReleaseDate == REGION2_SLIMLINE_RELEASE_DATE
		videoReleaseV2Full.region4AReleaseDate == REGION4_A_RELEASE_DATE
		videoReleaseV2Full.region4SlimlineReleaseDate == REGION4_SLIMLINE_RELEASE_DATE
		videoReleaseV2Full.amazonDigitalRelease == AMAZON_DIGITAL_RELEASE
		videoReleaseV2Full.dailymotionDigitalRelease == DAILYMOTION_DIGITAL_RELEASE
		videoReleaseV2Full.googlePlayDigitalRelease == GOOGLE_PLAY_DIGITAL_RELEASE
		videoReleaseV2Full.itunesDigitalRelease == I_TUNES_DIGITAL_RELEASE
		videoReleaseV2Full.ultraVioletDigitalRelease == ULTRA_VIOLET_DIGITAL_RELEASE
		videoReleaseV2Full.vimeoDigitalRelease == VIMEO_DIGITAL_RELEASE
		videoReleaseV2Full.vuduDigitalRelease == VUDU_DIGITAL_RELEASE
		videoReleaseV2Full.xboxSmartGlassDigitalRelease == XBOX_SMART_GLASS_DIGITAL
		videoReleaseV2Full.youTubeDigitalRelease == YOU_TUBE_DIGITAL_RELEASE
		videoReleaseV2Full.netflixDigitalRelease == NETFLIX_DIGITAL_RELEASE
		videoReleaseV2Full.references.size() == videoRelease.references.size()
		videoReleaseV2Full.ratings.size() == videoRelease.ratings.size()
		videoReleaseV2Full.languages.size() == videoRelease.languages.size()
		videoReleaseV2Full.languagesSubtitles.size() == videoRelease.languagesSubtitles.size()
		videoReleaseV2Full.languagesDubbed.size() == videoRelease.languagesDubbed.size()
	}
}
