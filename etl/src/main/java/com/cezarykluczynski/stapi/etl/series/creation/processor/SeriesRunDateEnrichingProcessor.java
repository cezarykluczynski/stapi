package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange;
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeriesRunDateEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<SeriesTemplate, SeriesTemplate>> {

	private final SeriesRunDateFixedValueProvider seriesRunDateFixedValueProvider;

	@Override
	public void enrich(EnrichablePair<SeriesTemplate, SeriesTemplate> enrichablePair) throws Exception {
		final SeriesTemplate seriesTemplate = enrichablePair.getInput();
		final FixedValueHolder<DateRange> runDateRange = seriesRunDateFixedValueProvider.getSearchedValue(seriesTemplate.getAbbreviation());
		if (runDateRange.isFound()) {
			final DateRange fixedValueOriginalRunDateRange = runDateRange.getValue();
			final DateRange originalRunDateRange = seriesTemplate.getOriginalRunDateRange();

			if (originalRunDateRange.getStartDate() == null) {
				originalRunDateRange.setStartDate(fixedValueOriginalRunDateRange.getStartDate());
			}
			if (originalRunDateRange.getEndDate() == null) {
				originalRunDateRange.setEndDate(fixedValueOriginalRunDateRange.getEndDate());
			}
		}
	}
}
