package com.cezarykluczynski.stapi.etl.location.creation.processor;

import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LocationWriter implements ItemWriter<Location> {

	private final LocationRepository locationRepository;

	public LocationWriter(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public void write(Chunk<? extends Location> items) throws Exception {
		locationRepository.saveAll(items.getItems());
	}

}
