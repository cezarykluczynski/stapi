package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor;

import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MagazineSeriesWriter implements ItemWriter<MagazineSeries> {

	private final MagazineSeriesRepository magazineSeriesRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public MagazineSeriesWriter(MagazineSeriesRepository magazineSeriesRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.magazineSeriesRepository = magazineSeriesRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends MagazineSeries> items) throws Exception {
		magazineSeriesRepository.save(process(items));
	}

	private List<MagazineSeries> process(List<? extends MagazineSeries> comicsList) {
		List<MagazineSeries> comicsListWithoutExtends = fromExtendsListToMagazineSeriesList(comicsList);
		return filterDuplicates(comicsListWithoutExtends);
	}

	private List<MagazineSeries> fromExtendsListToMagazineSeriesList(List<? extends MagazineSeries> magazineSeriesList) {
		return magazineSeriesList
				.stream()
				.map(pageAware -> (MagazineSeries) pageAware)
				.collect(Collectors.toList());
	}

	private List<MagazineSeries> filterDuplicates(List<MagazineSeries> magazineSeriesList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(magazineSeriesList.stream()
				.map(magazineSeries -> (PageAware) magazineSeries)
				.collect(Collectors.toList()), MagazineSeries.class).stream()
				.map(pageAware -> (MagazineSeries) pageAware)
				.collect(Collectors.toList());
	}

}
