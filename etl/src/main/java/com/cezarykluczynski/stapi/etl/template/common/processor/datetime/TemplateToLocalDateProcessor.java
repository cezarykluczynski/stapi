package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.util.constants.TemplateNames;
import com.cezarykluczynski.stapi.wiki.dto.Template;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

@Service
@Slf4j
public class TemplateToLocalDateProcessor implements ItemProcessor<Template, LocalDate> {

	@Override
	public LocalDate process(Template item) throws Exception {
		String title = item.getTitle();
		if (!TemplateNames.D.equals(title) && !TemplateNames.DATELINK.equals(title)) {
			log.warn("Template {} passed to TemplateToLocalDateProcessor::process was of different type.", item);
			return null;
		}

		String dayValue = null;
		String monthValue = null;
		String yearValue = null;

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key.equals("1")) {
				dayValue = value;
			}

			if (key.equals("2")) {
				monthValue = value;
			}

			if (key.equals("3")) {
				yearValue = value;
			}
		}

		if (dayValue != null && monthValue != null && yearValue != null) {
			Integer year = getInteger(yearValue);
			Month month = getMonth(monthValue);
			Integer day = getInteger(dayValue);
			if (year != null && month != null && day != null) {
				return LocalDate.of(year, month, day);
			} else {
				if (year == null) {
					log.error("{} candidate year could not be parsed to integer.", yearValue);
				}

				if (day == null) {
					log.error("{} candidate day could not be parsed to integer.", monthValue);
				}
			}
		}

		return null;
	}

	private Integer getInteger(String value) {
		return Ints.tryParse(value);
	}

	private Month getMonth(String monthName) {
		String monthNameUpperCase = StringUtils.upperCase(monthName);
		try {
			return Month.valueOf(monthNameUpperCase);
		} catch(IllegalArgumentException e) {
			log.error("{} could not be mapped to Month.", monthNameUpperCase);
			return null;
		}
	}

}
