package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateToLifeRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.cezarykluczynski.stapi.util.tool.LogicUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class LifeRangeProcessor implements ItemProcessor<Page, DateRange> {

	private final PageToLifeRangeProcessor pageToLifeRangeProcessor;

	private final ActorTemplateToLifeRangeProcessor actorTemplateToLifeRangeProcessor;

	private final TemplateFinder templateFinder;

	public LifeRangeProcessor(PageToLifeRangeProcessor pageToLifeRangeProcessor,
			ActorTemplateToLifeRangeProcessor actorTemplateToLifeRangeProcessor, TemplateFinder templateFinder) {
		this.pageToLifeRangeProcessor = pageToLifeRangeProcessor;
		this.actorTemplateToLifeRangeProcessor = actorTemplateToLifeRangeProcessor;
		this.templateFinder = templateFinder;
	}

	@Override
	public DateRange process(Page item) throws Exception {
		DateRange pageLifeRange = pageToLifeRangeProcessor.process(item);
		DateRange actorTemplateLifeRange = getTemplateDateRange(item);
		Boolean hasPageLifeRange = pageLifeRange != null;
		Boolean hasTemplateLifeRange = actorTemplateLifeRange != null;

		DateRange dateRange = new DateRange();

		if (!hasPageLifeRange && !hasTemplateLifeRange) {
			return dateRange;
		} else if (hasPageLifeRange ^ hasTemplateLifeRange) {
			return ObjectUtils.firstNonNull(pageLifeRange, actorTemplateLifeRange);
		} else {
			LocalDate pageDateOfBirth = pageLifeRange.getStartDate();
			LocalDate pageDateOfDeath = pageLifeRange.getEndDate();
			LocalDate actorTemplateDateOfBirth = actorTemplateLifeRange.getStartDate();
			LocalDate actorTemplateDateOfDeath = actorTemplateLifeRange.getEndDate();

			dateRange.setStartDate(getIfEqualSkipNulls(pageDateOfBirth, actorTemplateDateOfBirth, item, true));
			dateRange.setEndDate(getIfEqualSkipNulls(pageDateOfDeath, actorTemplateDateOfDeath, item, false));
			return dateRange;
		}
	}

	private LocalDate getIfEqualSkipNulls(LocalDate left, LocalDate right, Page item, boolean dateOfBirth) {
		if (left == null && right == null) {
			return null;
		} else if (LogicUtil.xorNull(left, right)) {
			return ObjectUtils.firstNonNull(left, right);
		} else {
			if (left.equals(right)) {
				return left;
			} else {
				String dateType = dateOfBirth ? "birth" : "death";
				log.warn("Date of {} {} from for PageToLifeRangeProcessor does not equal date of {} {} from ActorTemplateToLifeRangeProcessor for "
						+ "{} - setting date to null", dateType, left, dateType, right, item.getTitle());
				return null;
			}
		}
	}

	private DateRange getTemplateDateRange(Page item) throws Exception {
		Optional<Template> bornTemplateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_ACTOR);

		if (bornTemplateOptional.isPresent()) {
			return actorTemplateToLifeRangeProcessor.process(bornTemplateOptional.get());
		}

		return null;
	}

}
