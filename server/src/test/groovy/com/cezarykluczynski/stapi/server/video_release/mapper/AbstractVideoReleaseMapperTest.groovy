package com.cezarykluczynski.stapi.server.video_release.mapper

import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import com.cezarykluczynski.stapi.util.AbstractVideoReleaseTest

abstract class AbstractVideoReleaseMapperTest extends AbstractVideoReleaseTest {

	protected VideoRelease createVideoRelease() {
		new VideoRelease(
				uid: UID,
				title: TITLE,
				series: new Series(),
				season: new Season(),
				format: VideoReleaseFormat.BETAMAX,
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
	}

}
