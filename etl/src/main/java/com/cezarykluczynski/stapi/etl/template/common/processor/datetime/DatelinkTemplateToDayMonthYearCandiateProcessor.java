package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DatelinkTemplateToDayMonthYearCandiateProcessor implements ItemProcessor<Template, DayMonthYearCandidate> {

	@Override
	public DayMonthYearCandidate process(Template item) throws Exception {
		String title = item.getTitle();
		if (!TemplateTitle.D.equals(title) && !TemplateTitle.DATELINK.equals(title)) {
			log.warn("Template {} passed to DatelinkTemplateToDayMonthYearCandiateProcessor::process was of different type", item);
			return null;
		}

		String day = null;
		String month = null;
		String year = null;

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key.equals(TemplateParam.FIRST)) {
				day = value;
			}

			if (key.equals(TemplateParam.SECOND)) {
				month = value;
			}

			if (key.equals(TemplateParam.THIRD)) {
				year = value;
			}
		}

		return DayMonthYearCandidate.of(day, month, year);
	}
}
