package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.tool.LogicUtil;
import com.cezarykluczynski.stapi.util.tool.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WikitextToYearRangeProcessor implements ItemProcessor<String, YearRange> {

	private final PartToYearRangeProcessor partToYearRangeProcessor;

	private final WikitextApi wikitextApi;

	private final PageLinkToYearProcessor pageLinkToYearProcessor;

	public WikitextToYearRangeProcessor(PartToYearRangeProcessor partToYearRangeProcessor, WikitextApi wikitextApi,
			PageLinkToYearProcessor pageLinkToYearProcessor) {
		this.partToYearRangeProcessor = partToYearRangeProcessor;
		this.wikitextApi = wikitextApi;
		this.pageLinkToYearProcessor = pageLinkToYearProcessor;
	}

	@Override
	public YearRange process(String item) throws Exception {
		Template.Part templatePart = new Template.Part();
		templatePart.setValue(item);
		YearRange yearRange = partToYearRangeProcessor.process(templatePart);

		if (yearRange != null && (yearRange.getYearFrom() != null || yearRange.getYearTo() != null)) {
			if (LogicUtil.xorNull(yearRange.getYearFrom(), yearRange.getYearTo())) {
				log.warn("When parsing {} with PartToYearRangeProcessor, start year was \"{}\", while end year was: \"{}\"",
						item, yearRange.getYearFrom(), yearRange.getYearTo());
			}

			return yearRange;
		}

		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(item);

		if (NumberUtil.inRangeInclusive(pageLinkList.size(), 1, 2)) {
			Integer startYear = pageLinkToYearProcessor.process(pageLinkList.get(0));
			Integer endYear;

			if (pageLinkList.size() == 2) {
				endYear = pageLinkToYearProcessor.process(pageLinkList.get(1));
			} else {
				endYear = startYear;
			}

			return YearRange.of(startYear, endYear);
		}

		return null;
	}

}
