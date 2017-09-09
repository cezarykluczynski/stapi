package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.etl.common.service.PeriodCandidateDetector;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class DateStatusProcessor implements ItemProcessor<String, String> {

	private final PeriodCandidateDetector periodCandidateDetector;

	public DateStatusProcessor(PeriodCandidateDetector periodCandidateDetector) {
		this.periodCandidateDetector = periodCandidateDetector;
	}

	@Override
	public String process(String item) throws Exception {
		return periodCandidateDetector.isPeriodCandidate(item) ? item : null;
	}

}
