package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class MagazineWriter implements ItemWriter<Magazine> {

	private final MagazineRepository magazineRepository;

	public MagazineWriter(MagazineRepository magazineRepository) {
		this.magazineRepository = magazineRepository;
	}

	@Override
	public void write(Chunk<? extends Magazine> items) throws Exception {
		magazineRepository.saveAll(items.getItems());
	}

}
