package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor;

import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.model.medical_condition.repository.MedicalConditionRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MedicalConditionWriter implements ItemWriter<MedicalCondition> {

	private final MedicalConditionRepository medicalConditionRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public MedicalConditionWriter(MedicalConditionRepository medicalConditionRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.medicalConditionRepository = medicalConditionRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends MedicalCondition> items) throws Exception {
		medicalConditionRepository.save(process(items));
	}

	private List<MedicalCondition> process(List<? extends MedicalCondition> medicalConditionList) {
		List<MedicalCondition> medicalConditionListWithoutExtends = fromExtendsListToMedicalConditionList(medicalConditionList);
		return filterDuplicates(medicalConditionListWithoutExtends);
	}

	private List<MedicalCondition> fromExtendsListToMedicalConditionList(List<? extends MedicalCondition> medicalConditionList) {
		return medicalConditionList
				.stream()
				.map(pageAware -> (MedicalCondition) pageAware)
				.collect(Collectors.toList());
	}

	private List<MedicalCondition> filterDuplicates(List<MedicalCondition> medicalConditionList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(medicalConditionList.stream()
				.map(medicalCondition -> (PageAware) medicalCondition)
				.collect(Collectors.toList()), MedicalCondition.class).stream()
				.map(pageAware -> (MedicalCondition) pageAware)
				.collect(Collectors.toList());
	}

}
