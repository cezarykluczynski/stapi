package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoReleaseFormatFromCategoryLinkProcessor implements ItemProcessor<List<CategoryHeader>, VideoReleaseFormat> {

	@Override
	public VideoReleaseFormat process(List<CategoryHeader> item) throws Exception {
		// TODO
		return null;
	}

}
