package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.YearlinkToYearProcessor;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VideoTemplateYearsProcessor implements ItemProcessor<Template.Part, YearRange> {

	private final WikitextApi wikitextApi;

	private final PageLinkToYearProcessor pageLinkToYearProcessor;

	private final YearlinkToYearProcessor yearlinkToYearProcessor;

	public VideoTemplateYearsProcessor(WikitextApi wikitextApi, PageLinkToYearProcessor pageLinkToYearProcessor,
			YearlinkToYearProcessor yearlinkToYearProcessor) {
		this.wikitextApi = wikitextApi;
		this.pageLinkToYearProcessor = pageLinkToYearProcessor;
		this.yearlinkToYearProcessor = yearlinkToYearProcessor;
	}

	@Override
	@SuppressWarnings("NPathComplexity")
	public YearRange process(Template.Part item) throws Exception {
		if (StringUtils.isBlank(item.getValue()) && StringUtils.isBlank(item.getContent()) && CollectionUtils.isEmpty(item.getTemplates())) {
			return null;
		}

		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(item.getValue());

		if (!pageLinkList.isEmpty()) {
			Optional<YearRange> yearRangeOptional = fromPageLinkList(pageLinkList);
			if (yearRangeOptional.isPresent()) {
				return yearRangeOptional.get();
			}
		}

		List<Template> templateList = item.getTemplates();
		if (!CollectionUtils.isEmpty(templateList)) {
			Optional<YearRange> yearRangeOptional = fromTemplateList(templateList);
			if (yearRangeOptional.isPresent()) {
				return yearRangeOptional.get();
			}
		}

		if (!pageLinkList.isEmpty() || !templateList.isEmpty()) {
			log.info("Could not parse template part {} into YearRange", item);
		}
		return null;
	}

	private Optional<YearRange> fromPageLinkList(List<PageLink> pageLinkList) throws Exception {
		List<Integer> yearList = Lists.newArrayList();
		for (PageLink pageLink : pageLinkList) {
			addIfNotNull(yearList, pageLinkToYearProcessor.process(pageLink));
		}

		return fromYearList(yearList);
	}

	private Optional<YearRange> fromTemplateList(List<Template> templateList) throws Exception {
		List<Integer> yearList = Lists.newArrayList();
		for (Template template : templateList) {
			addIfNotNull(yearList, yearlinkToYearProcessor.process(template));
		}

		return fromYearList(yearList);
	}

	private static Optional<YearRange> fromYearList(List<Integer> yearList) {
		if (!yearList.isEmpty()) {
			Collections.sort(yearList);
			return Optional.of(YearRange.of(yearList.get(0), yearList.get(yearList.size() - 1)));
		}

		return Optional.empty();
	}

	private static void addIfNotNull(List<Integer> yearList, Integer yearCandidate) {
		if (yearCandidate != null) {
			yearList.add(yearCandidate);
		}
	}

}
