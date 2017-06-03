package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.YearMonth;

@Service
@Slf4j
public class MonthYearCandidateToYearMonthProcessor implements ItemProcessor<MonthYearCandidate, YearMonth> {

	private final MonthNameToMonthProcessor monthNameToMonthProcessor;

	@Inject
	public MonthYearCandidateToYearMonthProcessor(MonthNameToMonthProcessor monthNameToMonthProcessor) {
		this.monthNameToMonthProcessor = monthNameToMonthProcessor;
	}

	@Override
	public YearMonth process(MonthYearCandidate item) throws Exception {
		String monthValue = item.getMonth();
		String yearValue = item.getYear();

		try {
			return YearMonth.of(getInteger(yearValue), monthNameToMonthProcessor.process(monthValue));
		} catch (Exception e) {
			return null;
		}
	}

	private Integer getInteger(String value) {
		return Ints.tryParse(value);
	}
}
