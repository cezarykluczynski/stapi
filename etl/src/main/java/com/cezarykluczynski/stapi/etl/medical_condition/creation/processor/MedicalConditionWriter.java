package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor;

import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.model.medical_condition.repository.MedicalConditionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MedicalConditionWriter implements ItemWriter<MedicalCondition> {

	private final MedicalConditionRepository medicalConditionRepository;

	public MedicalConditionWriter(MedicalConditionRepository medicalConditionRepository) {
		this.medicalConditionRepository = medicalConditionRepository;
	}

	@Override
	public void write(Chunk<? extends MedicalCondition> items) throws Exception {
		medicalConditionRepository.saveAll(items.getItems());
	}

}
