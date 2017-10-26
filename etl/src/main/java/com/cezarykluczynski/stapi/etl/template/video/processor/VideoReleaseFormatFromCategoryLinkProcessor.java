package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoReleaseFormatFromCategoryLinkProcessor implements ItemProcessor<List<CategoryHeader>, VideoReleaseFormat> {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public VideoReleaseFormatFromCategoryLinkProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	@SuppressWarnings("ReturnCount")
	public VideoReleaseFormat process(List<CategoryHeader> item) throws Exception {
		if (CollectionUtils.isEmpty(item)) {
			return null;
		}

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item);

		if (categoryTitleList.contains(CategoryTitle._4K_ULTRA_HD_BLU_RAYS)) {
			return VideoReleaseFormat.BLU_RAY_4K_UHD;
		} else if (categoryTitleList.contains(CategoryTitle.BETAMAX_RELEASES)) {
			return VideoReleaseFormat.BETAMAX;
		} else if (categoryTitleList.contains(CategoryTitle.BLU_RAY_DISCS)) {
			return VideoReleaseFormat.BLU_RAY;
		} else if (categoryTitleList.contains(CategoryTitle.CEDS)) {
			return VideoReleaseFormat.CED;
		} else if (categoryTitleList.contains(CategoryTitle.DIGITAL_RELEASES)) {
			return VideoReleaseFormat.DIGITAL_FORMAT;
		} else if (categoryTitleList.contains(CategoryTitle.DVDS)) {
			return VideoReleaseFormat.DVD;
		} else if (categoryTitleList.contains(CategoryTitle.LASER_DISCS)) {
			return VideoReleaseFormat.LD;
		} else if (categoryTitleList.contains(CategoryTitle.SUPER_8S)) {
			return VideoReleaseFormat.SUPER_8;
		} else if (categoryTitleList.contains(CategoryTitle.VCDS)) {
			return VideoReleaseFormat.VCD;
		} else if (categoryTitleList.contains(CategoryTitle.VHDS)) {
			return VideoReleaseFormat.VHD;
		} else if (categoryTitleList.contains(CategoryTitle.VHS_RELEASES)
				|| categoryTitleList.contains(CategoryTitle.UK_VHS_RELEASES)
				|| categoryTitleList.contains(CategoryTitle.US_VHS_RELEASES)) {
			return VideoReleaseFormat.VHS;
		} else if (categoryTitleList.contains(CategoryTitle.VIDEO_8S)) {
			return VideoReleaseFormat.VIDEO_8;
		}

		return null;
	}

}
