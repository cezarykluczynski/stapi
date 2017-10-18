package com.cezarykluczynski.stapi.etl.medical_condition.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.medical_condition.creation.service.MedicalConditionPageFilter;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalConditionPageProcessor implements ItemProcessor<Page, MedicalCondition> {

	private final MedicalConditionPageFilter medicalConditionPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public MedicalConditionPageProcessor(MedicalConditionPageFilter medicalConditionPageFilter, PageBindingService pageBindingService,
			UidGenerator uidGenerator, CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.medicalConditionPageFilter = medicalConditionPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public MedicalCondition process(Page item) throws Exception {
		if (medicalConditionPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		MedicalCondition medicalCondition = new MedicalCondition();
		medicalCondition.setName(TitleUtil.getNameFromTitle(item.getTitle()));

		medicalCondition.setPage(pageBindingService.fromPageToPageEntity(item));
		medicalCondition.setUid(uidGenerator.generateFromPage(medicalCondition.getPage(), MedicalCondition.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());
		medicalCondition.setPsychologicalCondition(categoryTitleList.contains(CategoryTitle.PSYCHOLOGICAL_CONDITIONS));

		return medicalCondition;
	}

}
