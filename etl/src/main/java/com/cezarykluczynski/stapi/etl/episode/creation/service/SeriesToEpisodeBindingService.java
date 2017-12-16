package com.cezarykluczynski.stapi.etl.episode.creation.service;

import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.SeriesAbbreviation;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SeriesToEpisodeBindingService {

	private static final BiMap<String, String> CATEGORY_TITLES_TO_ABBREVIATIONS = HashBiMap.create();

	static {
		CATEGORY_TITLES_TO_ABBREVIATIONS.put(CategoryTitle.TOS_EPISODES, SeriesAbbreviation.TOS);
		CATEGORY_TITLES_TO_ABBREVIATIONS.put(CategoryTitle.TAS_EPISODES, SeriesAbbreviation.TAS);
		CATEGORY_TITLES_TO_ABBREVIATIONS.put(CategoryTitle.TNG_EPISODES, SeriesAbbreviation.TNG);
		CATEGORY_TITLES_TO_ABBREVIATIONS.put(CategoryTitle.DS9_EPISODES, SeriesAbbreviation.DS9);
		CATEGORY_TITLES_TO_ABBREVIATIONS.put(CategoryTitle.VOY_EPISODES, SeriesAbbreviation.VOY);
		CATEGORY_TITLES_TO_ABBREVIATIONS.put(CategoryTitle.ENT_EPISODES, SeriesAbbreviation.ENT);
		CATEGORY_TITLES_TO_ABBREVIATIONS.put(CategoryTitle.DIS_EPISODES, SeriesAbbreviation.DIS);
	}

	private SeriesRepository seriesRepository;

	private List<Series> seriesList;

	private Map<String, Series> categoryTitlesToSeries = Maps.newHashMap();

	public SeriesToEpisodeBindingService(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}

	public Series mapCategoriesToSeries(List<CategoryHeader> categoryHeaderList) {
		synchronized (this) {
			if (seriesList == null) {
				seriesList = seriesRepository.findAll();
				fillCategoryTitlesToSeries();
			}
		}

		Series series = null;

		for (CategoryHeader categoryHeader : categoryHeaderList) {
			String categoryTitle = categoryHeader.getTitle();
			if (categoryTitlesToSeries.containsKey(categoryTitle)) {
				if (series == null) {
					series = categoryTitlesToSeries.get(categoryTitle);
				} else {
					log.warn("More than one category denoting episode in series previous was \"{}\", but \"{}\" also found",
							series.getTitle(), categoryTitlesToSeries.get(categoryTitle).getTitle());
					return null;
				}
			}
		}

		if (series == null) {
			log.info("Could not determine series from categories {}", categoryHeaderList);
		}

		return series;
	}

	private void fillCategoryTitlesToSeries() {
		final BiMap<String, String> categoryAbbreviationsToTitles = CATEGORY_TITLES_TO_ABBREVIATIONS.inverse();
		seriesList.forEach(seriesEntity -> {
			String categoryName = categoryAbbreviationsToTitles.get(seriesEntity.getAbbreviation());
			categoryTitlesToSeries.put(categoryName, seriesEntity);
		});
	}

}
