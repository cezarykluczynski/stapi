package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class YearlinkToYearProcessor implements ItemProcessor<Template, Integer> {

	@Override
	public Integer process(Template item) throws Exception {
		String title = item.getTitle();
		if (!TemplateTitle.Y.equals(title) && !TemplateTitle.YEARLINK.equals(title)) {
			log.info("Template {} passed to TemplateToYearProcessor::process was of different type", item);
			return null;
		}

		return item.getParts().stream()
				.filter(part -> part.getKey().equals("1"))
				.map(part -> Ints.tryParse(part.getValue()))
				.filter(Objects::nonNull)
				.findFirst().orElse(null);
	}

}
