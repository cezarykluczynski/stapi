package com.cezarykluczynski.stapi.etl.material.creation.processor;

import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MaterialWriter implements ItemWriter<Material> {

	private final MaterialRepository materialRepository;

	public MaterialWriter(MaterialRepository materialRepository) {
		this.materialRepository = materialRepository;
	}

	@Override
	public void write(Chunk<? extends Material> items) throws Exception {
		materialRepository.saveAll(items.getItems());
	}

}
