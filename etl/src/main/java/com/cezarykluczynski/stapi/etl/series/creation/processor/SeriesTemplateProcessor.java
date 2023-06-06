package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.series.creation.dto.SeriesEpisodeStatisticsDTO;
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeriesTemplateProcessor implements ItemProcessor<SeriesTemplate, Series> {

	private final UidGenerator uidGenerator;

	private final SeriesEpisodeStatisticsFixedValueProvider seriesEpisodeStatisticsFixedValueProvider;

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
		series.setSeasonsCount(item.getSeasonsCount());
		series.setEpisodesCount(item.getEpisodesCount());
		if (series.getSeasonsCount() != null || series.getEpisodesCount() != null) {
			series.setFeatureLengthEpisodesCount(0);
		}

		FixedValueHolder<SeriesEpisodeStatisticsDTO> seriesEpisodeStatisticsDTOFixedValueHolder
				= seriesEpisodeStatisticsFixedValueProvider.getSearchedValue(series.getAbbreviation());

		if (seriesEpisodeStatisticsDTOFixedValueHolder.isFound()) {
			SeriesEpisodeStatisticsDTO seriesEpisodeStatisticsDTO = seriesEpisodeStatisticsDTOFixedValueHolder
					.getValue();
			if (series.getSeasonsCount() == null) {
				series.setSeasonsCount(seriesEpisodeStatisticsDTO.getSeasonsCount());
				log.info("Setting seasons count for {} to {} from SeriesEpisodeStatisticsFixedValueProvider",
						series.getTitle(), series.getSeasonsCount());
			}
			if (series.getEpisodesCount() == null) {
				series.setEpisodesCount(seriesEpisodeStatisticsDTO.getEpisodesCount());
				log.info("Setting episodes count for {} to {} from SeriesEpisodeStatisticsFixedValueProvider",
						series.getTitle(), series.getEpisodesCount());
			}
			series.setFeatureLengthEpisodesCount(seriesEpisodeStatisticsDTO.getFeatureLengthEpisodesCount());
		}

		series.setCompanionSeries(item.getCompanionSeries());

		return series;
	}

}
