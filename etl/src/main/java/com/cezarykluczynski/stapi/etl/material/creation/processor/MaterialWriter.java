package com.cezarykluczynski.stapi.etl.material.creation.processor;

import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MaterialWriter implements ItemWriter<Material> {

	private final MaterialRepository materialRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public MaterialWriter(MaterialRepository materialRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.materialRepository = materialRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Material> items) throws Exception {
		materialRepository.save(process(items));
	}

	private List<Material> process(List<? extends Material> materialList) {
		List<Material> materialListWithoutExtends = fromExtendsListToMaterialList(materialList);
		return filterDuplicates(materialListWithoutExtends);
	}

	private List<Material> fromExtendsListToMaterialList(List<? extends Material> materialList) {
		return materialList
				.stream()
				.map(pageAware -> (Material) pageAware)
				.collect(Collectors.toList());
	}

	private List<Material> filterDuplicates(List<Material> materialList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(materialList.stream()
				.map(material -> (PageAware) material)
				.collect(Collectors.toList()), Material.class).stream()
				.map(pageAware -> (Material) pageAware)
				.collect(Collectors.toList());
	}

}
