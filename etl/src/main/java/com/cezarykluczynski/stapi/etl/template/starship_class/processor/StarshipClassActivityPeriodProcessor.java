package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.template.starship_class.dto.ActivityPeriodDTO;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StarshipClassActivityPeriodProcessor implements ItemProcessor<String, ActivityPeriodDTO> {

	private static final Pattern SEPARATOR = Pattern.compile("&ndash;|-|,|onward");

	private final WikitextApi wikitextApi;

	@Inject
	public StarshipClassActivityPeriodProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
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
		boolean hasOnePageLink =  pageLinkList.size() == 1;
		String secondTitleWithSeparator = hasOnePageLink ? null : pageLinkList.get(1).getTitle();
		String secondTitleWithoutSeparator = hasOnePageLink ? pageLinkTitle : pageLinkTitle;
		boolean hasSeparator = separatorPositions.stream()
				.anyMatch(separatorPosition -> separatorPosition >= pageLinkEndPosition);
		return ActivityPeriodDTO.of(pageLinkTitle, hasSeparator ? secondTitleWithSeparator : secondTitleWithoutSeparator);
	}

	private boolean isPeriodCandidate(PageLink pageLink) {
		return StringUtils.length(pageLink.getTitle()) > 2 && NumberUtils.isDigits(StringUtils.substring(pageLink.getTitle(), 0, 2));
	}

}
