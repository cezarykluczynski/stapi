package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

@Service
public class DayMonthYearToLocalDateProcessor implements ItemProcessor<DayMonthYear, LocalDate> {

	@Override
	public LocalDate process(DayMonthYear item) throws Exception {
		if (item == null || item.getYear() == null || item.getMonth() == null || item.getDay() == null) {
			return null;
		}

		return LocalDate.of(item.getYear(), Month.of(item.getMonth()), item.getDay());
	}

}
