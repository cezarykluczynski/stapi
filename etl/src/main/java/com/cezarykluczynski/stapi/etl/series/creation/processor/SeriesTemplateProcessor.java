package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.series.creation.dto.SeriesEpisodeStatisticsDTO;
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesTemplateProcessor implements ItemProcessor<SeriesTemplate, Series> {

	private GuidGenerator guidGenerator;

	private SeriesEpisodeStatisticsFixedValueProvider seriesEpisodeStatisticsFixedValueProvider;

	@Inject
	public SeriesTemplateProcessor(GuidGenerator guidGenerator,
			SeriesEpisodeStatisticsFixedValueProvider seriesEpisodeStatisticsFixedValueProvider) {
		this.guidGenerator = guidGenerator;
		this.seriesEpisodeStatisticsFixedValueProvider = seriesEpisodeStatisticsFixedValueProvider;
	}

	@Override
	public Series process(SeriesTemplate item) throws Exception {
		Series series = new Series();

		series.setTitle(item.getTitle());
		series.setPage(item.getPage());
		series.setGuid(guidGenerator.generateFromPage(item.getPage(), Series.class));
		series.setAbbreviation(item.getAbbreviation());
		series.setProductionStartYear(item.getProductionYearRange().getStartYear());
		series.setProductionEndYear(item.getProductionYearRange().getEndYear());
		series.setOriginalRunStartDate(item.getOriginalRunDateRange().getStartDate());
		series.setOriginalRunEndDate(item.getOriginalRunDateRange().getEndDate());
		series.setProductionCompany(item.getProductionCompany());
		series.setOriginalBroadcaster(item.getOriginalBroadcaster());

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
