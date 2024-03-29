package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.MonthYearCandidate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
@Slf4j
public class MonthYearCandidateToYearMonthProcessor implements ItemProcessor<MonthYearCandidate, YearMonth> {

	private final MonthNameToMonthProcessor monthNameToMonthProcessor;

	public MonthYearCandidateToYearMonthProcessor(MonthNameToMonthProcessor monthNameToMonthProcessor) {
		this.monthNameToMonthProcessor = monthNameToMonthProcessor;
	}

	@Override
	public YearMonth process(MonthYearCandidate item) throws Exception {
		String monthValue = item.getMonth();
		Integer yearValue = item.getYear();

		try {
			return YearMonth.of(yearValue, monthNameToMonthProcessor.process(monthValue));
		} catch (Exception e) {
			return null;
		}
	}

}
