package com.cezarykluczynski.stapi.etl.template.comic_series.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO;
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesTemplateFixedValuesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<ComicSeriesTemplate, ComicSeriesTemplate>> {

	private final ComicSeriesPublishedDateFixedValueProvider comicSeriesPublishedDateFixedValueProvider;

	private final PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;

	private final ComicSeriesTemplateNumberOfIssuesFixedValueProvider comicSeriesTemplateNumberOfIssuesFixedValueProvider;

	private final ComicSeriesStardateYearFixedValueProvider comicSeriesStardateYearFixedValueProvider;

	public ComicSeriesTemplateFixedValuesEnrichingProcessor(ComicSeriesPublishedDateFixedValueProvider comicSeriesPublishedDateFixedValueProvider,
			PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor,
			ComicSeriesTemplateNumberOfIssuesFixedValueProvider comicSeriesTemplateNumberOfIssuesFixedValueProvider,
			ComicSeriesStardateYearFixedValueProvider comicSeriesStardateYearFixedValueProvider) {
		this.comicSeriesPublishedDateFixedValueProvider = comicSeriesPublishedDateFixedValueProvider;
		this.publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor = publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor;
		this.comicSeriesTemplateNumberOfIssuesFixedValueProvider = comicSeriesTemplateNumberOfIssuesFixedValueProvider;
		this.comicSeriesStardateYearFixedValueProvider = comicSeriesStardateYearFixedValueProvider;
	}

	@Override
	public void enrich(EnrichablePair<ComicSeriesTemplate, ComicSeriesTemplate> enrichablePair) throws Exception {
		ComicSeriesTemplate comicSeriesTemplate = enrichablePair.getOutput();
		String title = comicSeriesTemplate.getTitle();

		FixedValueHolder<Range<DayMonthYear>> dayMonthYearRangeFixedValueHolder = comicSeriesPublishedDateFixedValueProvider.getSearchedValue(title);

		if (dayMonthYearRangeFixedValueHolder.isFound()) {
			Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeFixedValueHolder.getValue();
			publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
					.enrich(EnrichablePair.of(Pair.of(null, dayMonthYearRange), comicSeriesTemplate));
		}

		FixedValueHolder<Integer> numberOfIssuesFixedValueHolder = comicSeriesTemplateNumberOfIssuesFixedValueProvider.getSearchedValue(title);

		if (numberOfIssuesFixedValueHolder.isFound()) {
			comicSeriesTemplate.setNumberOfIssues(numberOfIssuesFixedValueHolder.getValue());
		}

		FixedValueHolder<StardateYearDTO> stardateYearDTOFixedValueHolder = comicSeriesStardateYearFixedValueProvider.getSearchedValue(title);

		if (stardateYearDTOFixedValueHolder.isFound()) {
			StardateYearDTO stardateYearDTO = stardateYearDTOFixedValueHolder.getValue();
			comicSeriesTemplate.setYearFrom(stardateYearDTO.getYearFrom());
			comicSeriesTemplate.setYearTo(stardateYearDTO.getYearTo());
			comicSeriesTemplate.setStardateFrom(stardateYearDTO.getStardateFrom());
			comicSeriesTemplate.setStardateTo(stardateYearDTO.getStardateTo());
		}
	}

}
