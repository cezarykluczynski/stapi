package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate;
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MonthlinkTemplateToMonthYearCandiateProcessor implements ItemProcessor<Template, MonthYearCandidate> {

	@Override
	public MonthYearCandidate process(Template item) throws Exception {
		String title = item.getTitle();
		if (!TemplateTitle.M.equals(title) && !TemplateTitle.MONTHLINK.equals(title)) {
			log.warn("Template {} passed to DatelinkTemplateToMonthYearCandiateProcessor::process was of different type", item);
			return null;
		}

		String month = null;
		String year = null;

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key.equals(TemplateParam.FIRST)) {
				month = value;
			}

			if (key.equals(TemplateParam.SECOND)) {
				year = value;
			}
		}

		return MonthYearCandidate.of(month, year);
	}
}
