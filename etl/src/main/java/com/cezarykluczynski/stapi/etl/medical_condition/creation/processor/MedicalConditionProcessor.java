package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MedicalConditionProcessor extends CompositeItemProcessor<PageHeader, MedicalCondition> {

	public MedicalConditionProcessor(PageHeaderProcessor pageHeaderProcessor, MedicalConditionPageProcessor medicalConditionPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, medicalConditionPageProcessor));
	}

}

