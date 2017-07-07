package com.cezarykluczynski.stapi.server.video_release.mapper

import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBase as VideoReleaseBase
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class VideoReleaseBaseSoapMapperTest extends AbstractVideoReleaseMapperTest {

	private VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapper

	void setup() {
		videoReleaseBaseSoapMapper = Mappers.getMapper(VideoReleaseBaseSoapMapper)
	}

	void "maps SOAP VideoReleaseRequest to VideoReleaseRequestDTO"() {
		given:
		VideoReleaseBaseRequest videoReleaseBaseRequest = new VideoReleaseBaseRequest(
				title: TITLE,
				year: new IntegerRange(
						from: YEAR_FROM,
						to: YEAR_TO,
				),
				runTime: new IntegerRange(
						from: RUN_TIME_FROM,
						to: RUN_TIME_TO
				))

		when:
		VideoReleaseRequestDTO videoReleaseRequestDTO = videoReleaseBaseSoapMapper.mapBase videoReleaseBaseRequest

		then:
		videoReleaseRequestDTO.title == TITLE
		videoReleaseRequestDTO.yearFrom == YEAR_FROM
		videoReleaseRequestDTO.yearTo == YEAR_TO
		videoReleaseRequestDTO.runTimeFrom == RUN_TIME_FROM
		videoReleaseRequestDTO.runTimeTo == RUN_TIME_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		VideoRelease videoRelease = createVideoRelease()

		when:
		VideoReleaseBase videoReleaseBase = videoReleaseBaseSoapMapper.mapBase(Lists.newArrayList(videoRelease))[0]

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
		videoReleaseBase.regionFreeReleaseDate == REGION_FREE_RELEASE_DATE_XML
		videoReleaseBase.region1AReleaseDate == REGION1_A_RELEASE_DATE_XML
		videoReleaseBase.region1SlimlineReleaseDate == REGION1_SLIMLINE_RELEASE_DATE_XML
		videoReleaseBase.region2BReleaseDate == REGION2_B_RELEASE_DATE_XML
		videoReleaseBase.region2SlimlineReleaseDate == REGION2_SLIMLINE_RELEASE_DATE_XML
		videoReleaseBase.region4AReleaseDate == REGION4_A_RELEASE_DATE_XML
		videoReleaseBase.region4SlimlineReleaseDate == REGION4_SLIMLINE_RELEASE_DATE_XML
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
