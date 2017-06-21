package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import spock.lang.Specification
import spock.lang.Unroll

class VideoReleaseFormatProcessorTest extends Specification {

	private VideoReleaseFormatProcessor videoReleaseFormatProcessor

	void setup() {
		videoReleaseFormatProcessor = new VideoReleaseFormatProcessor()
	}

	@Unroll('maps #stringFormat to #videoReleaseFormat')
	void "maps string to VideoReleaseFormat"() {
		expect:
		videoReleaseFormat == videoReleaseFormatProcessor.process(stringFormat)

		where:
		stringFormat | videoReleaseFormat
		null         | null
		''           | null
		's8'         | VideoReleaseFormat.SUPER_8
		'bm'         | VideoReleaseFormat.BETAMAX
		'vhs'        | VideoReleaseFormat.VHS
		'usvhs'      | VideoReleaseFormat.VHS
		'ukvhs'      | VideoReleaseFormat.VHS
		'ced'        | VideoReleaseFormat.CED
		'ld'         | VideoReleaseFormat.LD
		'vhd'        | VideoReleaseFormat.VHD
		'vcd'        | VideoReleaseFormat.VCD
		'v8'         | VideoReleaseFormat.VIDEO_8
		'dvd'        | VideoReleaseFormat.DVD
		'umd'        | VideoReleaseFormat.UMD
		'hddvd'      | VideoReleaseFormat.HD_DVD
		'bd'         | VideoReleaseFormat.BLU_RAY
		'4kuhd'      | VideoReleaseFormat.BLU_RAY_4K_UHD
		'df'         | VideoReleaseFormat.DIGITAL_FORMAT
	}

}
