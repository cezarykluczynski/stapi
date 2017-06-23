package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class VideoReleaseFormatFromCategoryLinkProcessorTest extends Specification {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private VideoReleaseFormatFromCategoryLinkProcessor videoReleaseFormatFromCategoryLinkProcessor

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		videoReleaseFormatFromCategoryLinkProcessor = new VideoReleaseFormatFromCategoryLinkProcessor(
				categoryTitlesExtractingProcessorMock)
	}

	@Unroll('returns #format when #categoryTitle is among page categories')
	void "returns format for given category title"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		format == videoReleaseFormatFromCategoryLinkProcessor.process(categoryHeaderList)

		where:
		categoryHeaderList                              | format
		null                                            | null
		Lists.newArrayList()                            | null
		createList('INVALID')                           | null
		createList(CategoryTitle._4K_ULTRA_HD_BLU_RAYS) | VideoReleaseFormat.BLU_RAY_4K_UHD
		createList(CategoryTitle.BETAMAX_RELEASES)      | VideoReleaseFormat.BETAMAX
		createList(CategoryTitle.BLU_RAY_DISCS)         | VideoReleaseFormat.BLU_RAY
		createList(CategoryTitle.CEDS)                  | VideoReleaseFormat.CED
		createList(CategoryTitle.DIGITAL_RELEASES)      | VideoReleaseFormat.DIGITAL_FORMAT
		createList(CategoryTitle.DVDS)                  | VideoReleaseFormat.DVD
		createList(CategoryTitle.LASER_DISCS)           | VideoReleaseFormat.LD
		createList(CategoryTitle.SUPER_8S)              | VideoReleaseFormat.SUPER_8
		createList(CategoryTitle.VCDS)                  | VideoReleaseFormat.VCD
		createList(CategoryTitle.VHDS)                  | VideoReleaseFormat.VHD
		createList(CategoryTitle.VHS_RELEASES)          | VideoReleaseFormat.VHS
		createList(CategoryTitle.UK_VHS_RELEASES)       | VideoReleaseFormat.VHS
		createList(CategoryTitle.US_VHS_RELEASES)       | VideoReleaseFormat.VHS
		createList(CategoryTitle.VIDEO_8S)              | VideoReleaseFormat.VIDEO_8
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
