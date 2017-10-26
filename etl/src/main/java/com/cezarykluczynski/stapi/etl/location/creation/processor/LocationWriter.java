package com.cezarykluczynski.stapi.etl.location.creation.processor;


import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocationWriter implements ItemWriter<Location> {

	private final LocationRepository locationRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public LocationWriter(LocationRepository locationRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.locationRepository = locationRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Location> items) throws Exception {
		locationRepository.save(process(items));
	}

	private List<Location> process(List<? extends Location> locationList) {
		List<Location> locationListWithoutExtends = fromExtendsListToLocationList(locationList);
		return filterDuplicates(locationListWithoutExtends);
	}

	private List<Location> fromExtendsListToLocationList(List<? extends Location> locationList) {
		return locationList
				.stream()
				.map(pageAware -> (Location) pageAware)
				.collect(Collectors.toList());
	}

	private List<Location> filterDuplicates(List<Location> locationList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(locationList.stream()
				.map(location -> (PageAware) location)
				.collect(Collectors.toList()), Location.class).stream()
				.map(pageAware -> (Location) pageAware)
				.collect(Collectors.toList());
	}

}
