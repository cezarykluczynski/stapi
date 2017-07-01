package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class VideoTemplateYearsProcessor implements ItemProcessor<String, YearRange> {

	@Override
	public YearRange process(String item) throws Exception {
		// TODO
		return null;
	}

}
