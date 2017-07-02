package com.cezarykluczynski.stapi.etl.video_release.creation.processor

import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import spock.lang.Specification

class VideoReleaseFormatFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'To Be Takei'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private VideoReleaseFormatFixedValueProvider videoReleaseFormatFixedValueProvider

	void setup() {
		videoReleaseFormatFixedValueProvider = new VideoReleaseFormatFixedValueProvider()
	}

	void "provides correct value"() {
		expect:
		videoReleaseFormatFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		videoReleaseFormatFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == VideoReleaseFormat.DVD
	}

	void "provides missing value"() {
		expect:
		!videoReleaseFormatFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		videoReleaseFormatFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
