package com.cezarykluczynski.stapi.etl.episode.creation.service;

import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SeriesToEpisodeBindingService {

	private static final BiMap<String, String> categoryTitlesToAbbreviations = HashBiMap.create();

	static {
		categoryTitlesToAbbreviations.put(CategoryName.TOS_EPISODES, "TOS");
		categoryTitlesToAbbreviations.put(CategoryName.TAS_EPISODES, "TAS");
		categoryTitlesToAbbreviations.put(CategoryName.TNG_EPISODES, "TNG");
		categoryTitlesToAbbreviations.put(CategoryName.DS9_EPISODES, "DS9");
		categoryTitlesToAbbreviations.put(CategoryName.VOY_EPISODES, "VOY");
		categoryTitlesToAbbreviations.put(CategoryName.ENT_EPISODES, "ENT");
	}

	private SeriesRepository seriesRepository;

	private List<Series> seriesList;

	private Map<String, Series> categoryTitlesToSeries = Maps.newHashMap();

	@Inject
	public SeriesToEpisodeBindingService(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;

	}

	public Series mapCategoriesToSeries(List<CategoryHeader> categoryHeaderList) {
		if (seriesList == null) {
			synchronized (this) {
				if (seriesList == null) {
					seriesList = seriesRepository.findAll();
					fillCategoryTitlesToSeries();
				}
			}
		}

		Series series = null;

		for (CategoryHeader categoryHeader : categoryHeaderList) {
			String categoryTitle = categoryHeader.getTitle();
			if (categoryTitlesToSeries.containsKey(categoryTitle)) {
				if (series == null) {
					series = categoryTitlesToSeries.get(categoryTitle);
				} else {
					log.error("More than one category denoting episode in series previous was {}, but {} also found ",
							series.getTitle(), categoryTitlesToSeries.get(categoryTitle).getTitle());
					return null;
				}
			}
		}

		return series;
	}

	private void fillCategoryTitlesToSeries() {
		final BiMap<String, String> categoryAbbreviationsToTitles = categoryTitlesToAbbreviations.inverse();
		seriesList.forEach(seriesEntity -> {
			String categoryName = categoryAbbreviationsToTitles.get(seriesEntity.getAbbreviation());
			categoryTitlesToSeries.put(categoryName, seriesEntity);
		});
	}

}
