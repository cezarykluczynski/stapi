package com.cezarykluczynski.stapi.server.video_release.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBase
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams
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

	void "maps DB entity to base REST entity"() {
		given:
		VideoRelease videoRelease = createVideoRelease()

		when:
		VideoReleaseBase videoReleaseBase = videoReleaseBaseRestMapper.mapBase(Lists.newArrayList(videoRelease))[0]

		then:
		videoReleaseBase.uid == UID
		videoReleaseBase.title == TITLE
		videoReleaseBase.series != null
		videoReleaseBase.season != null
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
		videoReleaseBase.ITunesDigitalRelease == I_TUNES_DIGITAL_RELEASE
		videoReleaseBase.ultraVioletDigitalRelease == ULTRA_VIOLET_DIGITAL_RELEASE
		videoReleaseBase.vimeoDigitalRelease == VIMEO_DIGITAL_RELEASE
		videoReleaseBase.vuduDigitalRelease == VUDU_DIGITAL_RELEASE
		videoReleaseBase.xboxSmartGlassDigitalRelease == XBOX_SMART_GLASS_DIGITAL
		videoReleaseBase.youTubeDigitalRelease == YOU_TUBE_DIGITAL_RELEASE
		videoReleaseBase.netflixDigitalRelease == NETFLIX_DIGITAL_RELEASE
	}

}
