package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

@Service
@Slf4j
public class DayMonthYearCandidateToLocalDateProcessor implements ItemProcessor<DayMonthYearCandidate, LocalDate> {

	private final MonthNameToMonthProcessor monthNameToMonthProcessor;

	public DayMonthYearCandidateToLocalDateProcessor(MonthNameToMonthProcessor monthNameToMonthProcessor) {
		this.monthNameToMonthProcessor = monthNameToMonthProcessor;
	}

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
			Month month = monthNameToMonthProcessor.process(monthValue);
			Integer day = getInteger(dayValue);
			if (year != null && month != null && day != null) {
				return LocalDate.of(year, month, day);
			} else {
				if (year == null) {
					log.info("{} candidate year could not be parsed to integer.", yearValue);
				}

				if (day == null) {
					log.info("{} candidate day could not be parsed to integer.", dayValue);
				}
			}
		}

		return null;
	}

	private Integer getInteger(String value) {
		return Ints.tryParse(value);
	}

}
