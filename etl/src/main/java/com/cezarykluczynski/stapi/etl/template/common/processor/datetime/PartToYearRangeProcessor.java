package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.IntegerValidator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PartToYearRangeProcessor implements ItemProcessor<Template.Part, YearRange> {

	private YearlinkToYearProcessor yearlinkToYearProcessor;

	private TemplateFilter templateFilter;

	@Inject
	public PartToYearRangeProcessor(YearlinkToYearProcessor yearlinkToYearProcessor, TemplateFilter templateFilter) {
		this.yearlinkToYearProcessor = yearlinkToYearProcessor;
		this.templateFilter = templateFilter;
	}

	@Override
	public YearRange process(Template.Part item) throws Exception {
		String value = item.getValue();
		List<Template> templateList = item.getTemplates();
		if (!org.springframework.util.CollectionUtils.isEmpty(templateList)) {
			return fromTemplates(templateList);
		} else if (value == null) {
			return new YearRange();
		} else {
			return fromValue(value);
		}
	}

	private YearRange fromValue(String value) {
		YearRange yearRange = new YearRange();

		List<String> dates = Lists.newArrayList(value.split("&ndash;|\\sto\\s"))
				.stream()
				.map(StringUtils::trim)
				.collect(Collectors.toList());

		if (dates.size() >= 1) {
			yearRange.setStartYear(Ints.tryParse(dates.get(0)));
		}
		if (dates.size() == 2) {
			yearRange.setEndYear(Ints.tryParse(dates.get(1)));
		}

		return yearRange;
	}

	private YearRange fromTemplates(List<Template> templateList) throws Exception {
		YearRange yearRange = new YearRange();

		List<Template> yearTemplateList = templateFilter
				.filterByTitle(templateList, TemplateName.Y, TemplateName.YEARLINK);

		Integer size = yearTemplateList.size();

		if (IntegerValidator.getInstance().isInRange(size, 1, 2)) {
			yearRange.setStartYear(yearlinkToYearProcessor.process(yearTemplateList.get(0)));
		}
		if (size == 2) {
			yearRange.setEndYear(yearlinkToYearProcessor.process(yearTemplateList.get(1)));
		}

		if (size > 2) {
			log.warn("Template list {} has more than two yearlink templates.", templateList);
		}

		return yearRange;
	}

}
