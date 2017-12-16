package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.service.PeriodCandidateDetector;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.ActivityPeriodDTO;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StarshipClassActivityPeriodProcessor implements ItemProcessor<String, ActivityPeriodDTO> {

	private static final Pattern SEPARATOR = Pattern.compile("&ndash;|-|,|onward");

	private final WikitextApi wikitextApi;

	private final PeriodCandidateDetector periodCandidateDetector;

	public StarshipClassActivityPeriodProcessor(WikitextApi wikitextApi, PeriodCandidateDetector periodCandidateDetector) {
		this.wikitextApi = wikitextApi;
		this.periodCandidateDetector = periodCandidateDetector;
	}

	@Override
	public ActivityPeriodDTO process(String item) throws Exception {
		List<PageLink> pageLinkList = getPeriodCandidatePageLinkList(item);

		if (pageLinkList.isEmpty()) {
			return null;
		}

		List<Integer> separatorPositions = getSeparatorPositions(item);

		if (pageLinkList.size() == 1 || pageLinkList.size() == 2) {
			return fromPageLinkListAndSeparatorsPositions(pageLinkList, separatorPositions);
		}

		return null;
	}

	private List<PageLink> getPeriodCandidatePageLinkList(String item) {
		return wikitextApi.getPageLinksFromWikitext(item)
				.stream()
				.filter(this::isPeriodCandidate)
				.collect(Collectors.toList());
	}

	private List<Integer> getSeparatorPositions(String item) {
		List<Integer> separatorPositions = Lists.newArrayList();

		Matcher matcher = SEPARATOR.matcher(item);
		while (matcher.find()) {
			separatorPositions.add(matcher.start());
		}

		return separatorPositions;
	}

	private ActivityPeriodDTO fromPageLinkListAndSeparatorsPositions(List<PageLink> pageLinkList, List<Integer> separatorPositions) {
		PageLink firstPageLink = pageLinkList.get(0);
		Integer pageLinkEndPosition = firstPageLink.getEndPosition();
		String pageLinkTitle = firstPageLink.getTitle();
		boolean hasOnePageLink = pageLinkList.size() == 1;
		String secondTitleWithSeparator = hasOnePageLink ? null : pageLinkList.get(1).getTitle();
		boolean hasSeparator = separatorPositions.stream()
				.anyMatch(separatorPosition -> separatorPosition >= pageLinkEndPosition);
		return ActivityPeriodDTO.of(pageLinkTitle, hasSeparator ? secondTitleWithSeparator : pageLinkTitle);
	}

	private boolean isPeriodCandidate(PageLink pageLink) {
		return periodCandidateDetector.isPeriodCandidate(pageLink.getTitle());
	}

}
