package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.series.creation.dto.SeriesEpisodeStatisticsDTO;
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SeriesTemplateProcessor implements ItemProcessor<SeriesTemplate, Series> {

	private final UidGenerator uidGenerator;

	private final SeriesAbbreviationFixedValueProvider seriesAbbreviationFixedValueProvider;

	private final SeriesEpisodeStatisticsFixedValueProvider seriesEpisodeStatisticsFixedValueProvider;

	public SeriesTemplateProcessor(UidGenerator uidGenerator, SeriesAbbreviationFixedValueProvider seriesAbbreviationFixedValueProvider,
			SeriesEpisodeStatisticsFixedValueProvider seriesEpisodeStatisticsFixedValueProvider) {
		this.uidGenerator = uidGenerator;
		this.seriesAbbreviationFixedValueProvider = seriesAbbreviationFixedValueProvider;
		this.seriesEpisodeStatisticsFixedValueProvider = seriesEpisodeStatisticsFixedValueProvider;
	}

	@Override
	public Series process(SeriesTemplate item) throws Exception {
		Series series = new Series();

		series.setTitle(item.getTitle());
		series.setPage(item.getPage());
		series.setUid(uidGenerator.generateFromPage(item.getPage(), Series.class));
		series.setAbbreviation(item.getAbbreviation());
		series.setProductionStartYear(item.getProductionYearRange().getYearFrom());
		series.setProductionEndYear(item.getProductionYearRange().getYearTo());
		series.setOriginalRunStartDate(item.getOriginalRunDateRange().getStartDate());
		series.setOriginalRunEndDate(item.getOriginalRunDateRange().getEndDate());
		series.setProductionCompany(item.getProductionCompany());
		series.setOriginalBroadcaster(item.getOriginalBroadcaster());

		if (series.getAbbreviation() == null) {
			FixedValueHolder<String> abbreviationFixedValueHolder = seriesAbbreviationFixedValueProvider.getSearchedValue(item.getTitle());

			if (abbreviationFixedValueHolder.isFound()) {
				series.setAbbreviation(abbreviationFixedValueHolder.getValue());
			}
		}

		FixedValueHolder<SeriesEpisodeStatisticsDTO> seriesEpisodeStatisticsDTOFixedValueHolder
				= seriesEpisodeStatisticsFixedValueProvider.getSearchedValue(series.getAbbreviation());

		if (seriesEpisodeStatisticsDTOFixedValueHolder.isFound()) {
			SeriesEpisodeStatisticsDTO seriesEpisodeStatisticsDTO = seriesEpisodeStatisticsDTOFixedValueHolder
					.getValue();
			series.setSeasonsCount(seriesEpisodeStatisticsDTO.getSeasonsCount());
			series.setEpisodesCount(seriesEpisodeStatisticsDTO.getEpisodesCount());
			series.setFeatureLengthEpisodesCount(seriesEpisodeStatisticsDTO.getFeatureLengthEpisodesCount());
		}

		return series;
	}

}
