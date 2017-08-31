package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.etl.common.service.PeriodCandidateDetector;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class StarshipDateStatusProcessor implements ItemProcessor<String, String> {

	private final PeriodCandidateDetector periodCandidateDetector;

	public StarshipDateStatusProcessor(PeriodCandidateDetector periodCandidateDetector) {
		this.periodCandidateDetector = periodCandidateDetector;
	}

	@Override
	public String process(String item) throws Exception {
		return periodCandidateDetector.isPeriodCandidate(item) ? item : null;
	}

}
