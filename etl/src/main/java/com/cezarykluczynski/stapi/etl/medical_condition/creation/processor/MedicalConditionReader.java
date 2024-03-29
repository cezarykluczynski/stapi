package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class MedicalConditionReader extends ListItemReader<PageHeader> {

	public MedicalConditionReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of medical conditions list: {}", list.size());
	}

}
