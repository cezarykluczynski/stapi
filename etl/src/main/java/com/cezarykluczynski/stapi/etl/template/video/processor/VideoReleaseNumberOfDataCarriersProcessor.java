package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.primitives.Ints;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoReleaseNumberOfDataCarriersProcessor implements ItemProcessor<String, Integer> {

	private static final String PLUS_SIGN = "+";

	@Override
	public Integer process(String item) throws Exception {
		if (item == null) {
			return null;
		}

		String valueToParse = item.endsWith(PLUS_SIGN) ? StringUtils.chop(item) : item;
		valueToParse = StringUtil.substringBeforeAll(valueToParse, List.of("<br", " ("));
		valueToParse = StringUtils.remove(valueToParse, ",").trim();

		return Ints.tryParse(valueToParse);
	}

}
