package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

@Service
@Slf4j
public class DayMonthYearProcessor implements ItemProcessor<DayMonthYearCandidate, LocalDate> {

	@Override
	public LocalDate process(DayMonthYearCandidate item) throws Exception {
		String dayValue = item.getDay();
		String monthValue = item.getMonth();
		String yearValue = item.getYear();

		/* "?" here means that the data is not complete, so there is no point in going further. */
		if (Lists.newArrayList(dayValue, monthValue, yearValue).contains("?")) {
			return null;
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
					log.error("{} candidate day could not be parsed to integer.", dayValue);
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
		} catch (IllegalArgumentException e) {
			log.error("{} could not be mapped to Month.", monthNameUpperCase);
			return null;
		}
	}

}
