package com.cezarykluczynski.stapi.server.video_release.mapper

import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseBase
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2Base
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseV2RestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class VideoReleaseBaseRestMapperTest extends AbstractVideoReleaseMapperTest {

	private VideoReleaseBaseRestMapper videoReleaseBaseRestMapper

	void setup() {
		videoReleaseBaseRestMapper = Mappers.getMapper(VideoReleaseBaseRestMapper)
	}

	void "maps VideoReleaseRestBeanParams to VideoReleaseRequestDTO"() {
		given:
		VideoReleaseRestBeanParams videoReleaseRestBeanParams = new VideoReleaseRestBeanParams(
				uid: UID,
				title: TITLE,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				runTimeFrom: RUN_TIME_FROM,
				runTimeTo: RUN_TIME_TO)

		when:
		VideoReleaseRequestDTO videoReleaseRequestDTO = videoReleaseBaseRestMapper.mapBase videoReleaseRestBeanParams

		then:
		videoReleaseRequestDTO.uid == UID
		videoReleaseRequestDTO.title == TITLE
		videoReleaseRequestDTO.yearFrom == YEAR_FROM
		videoReleaseRequestDTO.yearTo == YEAR_TO
		videoReleaseRequestDTO.runTimeFrom == RUN_TIME_FROM
		videoReleaseRequestDTO.runTimeTo == RUN_TIME_TO
	}

	void "maps VideoReleaseV2RestBeanParams to VideoReleaseRequestDTO"() {
		given:
		VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams = new VideoReleaseV2RestBeanParams(
				uid: UID,
				title: TITLE,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				runTimeFrom: RUN_TIME_FROM,
				runTimeTo: RUN_TIME_TO,
				documentary: DOCUMENTARY,
				specialFeatures: SPECIAL_FEATURES)

		when:
		VideoReleaseRequestDTO videoReleaseRequestDTO = videoReleaseBaseRestMapper.mapV2Base videoReleaseV2RestBeanParams

		then:
		videoReleaseRequestDTO.uid == UID
		videoReleaseRequestDTO.title == TITLE
		videoReleaseRequestDTO.yearFrom == YEAR_FROM
		videoReleaseRequestDTO.yearTo == YEAR_TO
		videoReleaseRequestDTO.runTimeFrom == RUN_TIME_FROM
		videoReleaseRequestDTO.runTimeTo == RUN_TIME_TO
		videoReleaseRequestDTO.documentary == DOCUMENTARY
		videoReleaseRequestDTO.specialFeatures == SPECIAL_FEATURES
	}

	void "maps DB entity to base REST entity"() {
		given:
		VideoRelease videoRelease = createVideoRelease()

		when:
		VideoReleaseBase videoReleaseBase = videoReleaseBaseRestMapper.mapBase(Lists.newArrayList(videoRelease))[0]

		then:
		videoReleaseBase.uid == UID
		videoReleaseBase.title == TITLE
		videoReleaseBase.series != null
		videoReleaseBase.series.title == 'SERIES_1'
		videoReleaseBase.season != null
		videoReleaseBase.season.title == 'SEASON_1'
		videoReleaseBase.format.name() == videoRelease.format.name()
		videoReleaseBase.numberOfEpisodes == NUMBER_OF_EPISODES
		videoReleaseBase.numberOfFeatureLengthEpisodes == NUMBER_OF_FEATURE_LENGTH_EPISODES
		videoReleaseBase.numberOfDataCarriers == NUMBER_OF_DATA_CARRIERS
		videoReleaseBase.runTime == RUN_TIME
		videoReleaseBase.yearFrom == YEAR_FROM
		videoReleaseBase.yearTo == YEAR_TO
		videoReleaseBase.regionFreeReleaseDate == REGION_FREE_RELEASE_DATE
		videoReleaseBase.region1AReleaseDate == REGION1_A_RELEASE_DATE
		videoReleaseBase.region1SlimlineReleaseDate == REGION1_SLIMLINE_RELEASE_DATE
		videoReleaseBase.region2BReleaseDate == REGION2_B_RELEASE_DATE
		videoReleaseBase.region2SlimlineReleaseDate == REGION2_SLIMLINE_RELEASE_DATE
		videoReleaseBase.region4AReleaseDate == REGION4_A_RELEASE_DATE
		videoReleaseBase.region4SlimlineReleaseDate == REGION4_SLIMLINE_RELEASE_DATE
		videoReleaseBase.amazonDigitalRelease == AMAZON_DIGITAL_RELEASE
		videoReleaseBase.dailymotionDigitalRelease == DAILYMOTION_DIGITAL_RELEASE
		videoReleaseBase.googlePlayDigitalRelease == GOOGLE_PLAY_DIGITAL_RELEASE
		videoReleaseBase.itunesDigitalRelease == I_TUNES_DIGITAL_RELEASE
		videoReleaseBase.ultraVioletDigitalRelease == ULTRA_VIOLET_DIGITAL_RELEASE
		videoReleaseBase.vimeoDigitalRelease == VIMEO_DIGITAL_RELEASE
		videoReleaseBase.vuduDigitalRelease == VUDU_DIGITAL_RELEASE
		videoReleaseBase.xboxSmartGlassDigitalRelease == XBOX_SMART_GLASS_DIGITAL
		videoReleaseBase.youTubeDigitalRelease == YOU_TUBE_DIGITAL_RELEASE
		videoReleaseBase.netflixDigitalRelease == NETFLIX_DIGITAL_RELEASE
	}

	void "maps DB entity to base REST V2 entity"() {
		given:
		VideoRelease videoRelease = createVideoRelease()

		when:
		VideoReleaseV2Base videoReleaseV2Base = videoReleaseBaseRestMapper.mapV2Base(Lists.newArrayList(videoRelease))[0]

		then:
		videoReleaseV2Base.uid == UID
		videoReleaseV2Base.title == TITLE
		videoReleaseV2Base.format.name() == videoRelease.format.name()
		videoReleaseV2Base.numberOfEpisodes == NUMBER_OF_EPISODES
		videoReleaseV2Base.numberOfFeatureLengthEpisodes == NUMBER_OF_FEATURE_LENGTH_EPISODES
		videoReleaseV2Base.numberOfDataCarriers == NUMBER_OF_DATA_CARRIERS
		videoReleaseV2Base.runTime == RUN_TIME
		videoReleaseV2Base.yearFrom == YEAR_FROM
		videoReleaseV2Base.yearTo == YEAR_TO
		videoReleaseV2Base.regionFreeReleaseDate == REGION_FREE_RELEASE_DATE
		videoReleaseV2Base.region1AReleaseDate == REGION1_A_RELEASE_DATE
		videoReleaseV2Base.region1SlimlineReleaseDate == REGION1_SLIMLINE_RELEASE_DATE
		videoReleaseV2Base.region2BReleaseDate == REGION2_B_RELEASE_DATE
		videoReleaseV2Base.region2SlimlineReleaseDate == REGION2_SLIMLINE_RELEASE_DATE
		videoReleaseV2Base.region4AReleaseDate == REGION4_A_RELEASE_DATE
		videoReleaseV2Base.region4SlimlineReleaseDate == REGION4_SLIMLINE_RELEASE_DATE
		videoReleaseV2Base.amazonDigitalRelease == AMAZON_DIGITAL_RELEASE
		videoReleaseV2Base.dailymotionDigitalRelease == DAILYMOTION_DIGITAL_RELEASE
		videoReleaseV2Base.googlePlayDigitalRelease == GOOGLE_PLAY_DIGITAL_RELEASE
		videoReleaseV2Base.itunesDigitalRelease == I_TUNES_DIGITAL_RELEASE
		videoReleaseV2Base.ultraVioletDigitalRelease == ULTRA_VIOLET_DIGITAL_RELEASE
		videoReleaseV2Base.vimeoDigitalRelease == VIMEO_DIGITAL_RELEASE
		videoReleaseV2Base.vuduDigitalRelease == VUDU_DIGITAL_RELEASE
		videoReleaseV2Base.xboxSmartGlassDigitalRelease == XBOX_SMART_GLASS_DIGITAL
		videoReleaseV2Base.youTubeDigitalRelease == YOU_TUBE_DIGITAL_RELEASE
		videoReleaseV2Base.netflixDigitalRelease == NETFLIX_DIGITAL_RELEASE
		videoReleaseV2Base.documentary == DOCUMENTARY
		videoReleaseV2Base.specialFeatures == SPECIAL_FEATURES
	}

}
