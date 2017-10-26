package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.spacecraft_type.repository.SpacecraftTypeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpacecraftTypeWriter implements ItemWriter<SpacecraftType> {

	private final SpacecraftTypeRepository spacecraftTypeRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public SpacecraftTypeWriter(SpacecraftTypeRepository spacecraftTypeRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.spacecraftTypeRepository = spacecraftTypeRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends SpacecraftType> items) throws Exception {
		spacecraftTypeRepository.save(process(items));
	}

	private List<SpacecraftType> process(List<? extends SpacecraftType> planetList) {
		List<SpacecraftType> spacecraftTypeListWithoutExtends = fromExtendsListToSpacecraftTypeList(planetList);
		return filterDuplicates(spacecraftTypeListWithoutExtends);
	}

	private List<SpacecraftType> fromExtendsListToSpacecraftTypeList(List<? extends SpacecraftType> planetList) {
		return planetList
				.stream()
				.map(pageAware -> (SpacecraftType) pageAware)
				.collect(Collectors.toList());
	}

	private List<SpacecraftType> filterDuplicates(List<SpacecraftType> spacecraftTypeList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(spacecraftTypeList.stream()
				.map(spacecraftType -> (PageAware) spacecraftType)
				.collect(Collectors.toList()), SpacecraftType.class).stream()
				.map(pageAware -> (SpacecraftType) pageAware)
				.collect(Collectors.toList());
	}

}
