package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.template.util.PatternDictionary;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RawDatelinkExtractingProcessor implements ItemProcessor<String, List<LocalDate>> {

	private static final Pattern RAW_DATELINK = Pattern.compile("\\{\\{(d|datelink)\\|(\\d{1,2})\\|"
			+ PatternDictionary.MONTH_GROUP + "\\|(\\d{4})}}");

	private final DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	public RawDatelinkExtractingProcessor(DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor) {
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
	}

	@Override
	public List<LocalDate> process(String item) throws Exception {
		List<LocalDate> localDateList = Lists.newArrayList();

		Matcher matcher = RAW_DATELINK.matcher(item);

		while (matcher.find()) {
			localDateList.add(dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(
					matcher.group(2), matcher.group(3), matcher.group(4))));
		}

		return localDateList;
	}

}
