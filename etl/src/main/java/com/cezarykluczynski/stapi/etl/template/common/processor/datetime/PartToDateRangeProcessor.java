package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.IntegerValidator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class PartToDateRangeProcessor extends AbstractTemplateProcessor
		implements ItemProcessor<Template.Part, DateRange> {

	private DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessor;

	@Inject
	public PartToDateRangeProcessor(DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessor) {
		this.datelinkTemplateToLocalDateProcessor = datelinkTemplateToLocalDateProcessor;
	}

	@Override
	public DateRange process(Template.Part item) throws Exception {
		List<Template> templateList = item.getTemplates();

		return org.springframework.util.CollectionUtils.isEmpty(templateList) ? null : fromTemplate(templateList, item);
	}

	private DateRange fromTemplate(List<Template> templateList, Template.Part part) throws Exception {
		DateRange dateRange = new DateRange();

		List<Template> dateTemplateList = filterByTitle(templateList, TemplateName.D, TemplateName.DATELINK);

		Integer size = dateTemplateList.size();

		if (IntegerValidator.getInstance().isInRange(size, 1, 2)) {
			dateRange.setStartDate(datelinkTemplateToLocalDateProcessor.process(templateList.get(0)));
		}

		if (size == 2) {
			dateRange.setEndDate(datelinkTemplateToLocalDateProcessor.process(templateList.get(1)));
		}

		if (size > 2) {
			log.warn("Template part {} has more than two datelink templates.", part);
		}

		return dateRange;
	}

}
