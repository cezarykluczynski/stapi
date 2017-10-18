package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class MedicalConditionReader extends ListItemReader<PageHeader> {

	public MedicalConditionReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of medicalCondition list: {}", list.size());
	}

}
