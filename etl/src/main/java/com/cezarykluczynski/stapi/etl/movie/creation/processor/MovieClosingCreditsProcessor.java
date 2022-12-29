package com.cezarykluczynski.stapi.etl.movie.creation.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.etl.movie.creation.service.MovieExistingEntitiesHelper;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public class MovieClosingCreditsProcessor implements ItemProcessor<Page, Pair<List<PageSection>, Page>> {

	private static final List<String> SKIPPABLE_PAGE_SECTION_LINE_LIST = Lists.newArrayList(
			"Miniature Crash Sequence Photography Unit", "A [[Rick Berman]] Production",
			"[[Industrial Light & Magic]], a Division of Lucasfilm, Ltd.",
			"[[Category:Star Trek credits|Search for Spock, The]]",
			"[[Category:Star Trek credits|Voyage Home, The]]",
			"[[Category:Star Trek credits|Generations]]",
			"<section end=credits />"
	);

	private static final List<String> STOPPING_SECTIONS = Lists.newArrayList("Stunt Coordinator");

	private static final Pattern INTERWIKI = Pattern.compile("\\[\\[[a-z\\-]{2,5}:.*");

	private final PageApi pageApi;

	private final WikitextApi wikitextApi;

	private final PageSectionExtractor pageSectionExtractor;

	private final MovieExistingEntitiesHelper movieExistingEntitiesHelper;

	private final Set<String> loggedCastMultilines = Sets.newHashSet();

	@Override
	@NonNull
	public Pair<List<PageSection>, Page> process(Page page) throws Exception {
		List<PageSection> creditsPageSectionList = Lists.newArrayList();
		String pageTitle = page.getTitle();
		Page creditsPage = pageApi.getPage("Credits for " + pageTitle, page.getMediaWikiSource());

		if (creditsPage == null) {
			log.warn("Could not find credits page fom movie {}", pageTitle);
			return Pair.of(creditsPageSectionList, null);
		}
		// from now on that's the context in which logging should happen
		pageTitle = creditsPage.getTitle();

		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(creditsPage, "Closing credits",
				"Closing Credits", "Cast", "Crew");

		if (CollectionUtils.isEmpty(pageSectionList)) {
			log.info("Could not find closing credits section for page {}", pageTitle);
			return Pair.of(creditsPageSectionList, creditsPage);
		}

		List<String> pageSectionLineList = Lists.newArrayList();

		pageSectionList.forEach(pageSection -> {
			if (Lists.newArrayList("Cast", "Crew").contains(pageSection.getText())) {
				pageSectionLineList.add(";" + pageSection.getText());
			}
			pageSectionLineList.addAll(Lists.newArrayList(pageSection.getWikitext().split("\n")));
		});

		return Pair.of(getPageSectionList(pageSectionLineList, pageTitle, creditsPageSectionList), creditsPage);
	}

	@SuppressWarnings("NPathComplexity")
	private List<PageSection> getPageSectionList(List<String> pageSectionLineList, String pageTitle, List<PageSection> creditsPageSectionList) {
		PageSection creditsPageSection = null;
		List<String> creditsPageSectionWikitextLines = null;
		boolean createNewSection = false;
		String previousLine = null;
		String subsectionTitleCandidate = null;

		boolean couldBeCastMultiline = false;
		for (int i = 0; i < pageSectionLineList.size(); i++) {
			boolean isCast = isCastPageSection(creditsPageSection);
			couldBeCastMultiline = isCast && couldBeCastMultiline(pageSectionLineList, i) || couldBeCastMultiline;
			if (couldBeCastMultiline && !loggedCastMultilines.contains(pageTitle)) {
				log.info("Cast multiline section found on page {}.", pageTitle);
				loggedCastMultilines.add(pageTitle);
			}
			if (STOPPING_SECTIONS.contains(subsectionTitleCandidate)) {
				couldBeCastMultiline = false;
			}
			String pageSectionLine = pageSectionLineList.get(i);
			subsectionTitleCandidate = getSubsectionTitleCandidate(pageSectionLine);
			boolean hasSubsectionMarkup = pageSectionLine.startsWith(";");
			boolean isListItem = pageSectionLine.startsWith("*");
			if (hasSubsectionMarkup && !couldBeCastMultiline) {
				createNewSection = true;
			} else if (isCast && couldBeCastMultiline) {
				if (hasSubsectionMarkup) {
					previousLine = pageSectionLine;
				} else {
					creditsPageSectionWikitextLines.add(previousLine + " &ndash; " + pageSectionLine.substring(1));
				}
			} else if (isListItem || pageSectionLine.startsWith(":")) {
				if (creditsPageSection == null) {
					log.warn("List item \"{}\" on page \"{}\" found before any section started", pageSectionLine, pageTitle);
					continue;
				}
				creditsPageSectionWikitextLines.add(pageSectionLine);
			} else if (pageSectionLine.length() > 2 && !isSkippable(pageSectionLine) && !isNonInclude(pageSectionLine)
					&& !isInterwiki(pageSectionLine)) {
				log.warn("List item \"{}\" on page \"{}\" found, but not a list item nor a section part", pageSectionLine, pageTitle);
			}

			if (createNewSection) {
				addWikitextToPageSection(creditsPageSection, creditsPageSectionList, creditsPageSectionWikitextLines);
				creditsPageSection = new PageSection();
				creditsPageSection.setText(subsectionTitleCandidate);
				creditsPageSectionWikitextLines = Lists.newArrayList();
				createNewSection = false;
			}
		}

		addWikitextToPageSection(creditsPageSection, creditsPageSectionList, creditsPageSectionWikitextLines);

		return creditsPageSectionList;
	}

	private String getSubsectionTitleCandidate(String pageSectionLine) {
		return StringUtils.trimWhitespace(StringUtils.trimLeadingCharacter(StringUtils.trimTrailingCharacter(pageSectionLine, ':'), ';'));
	}

	private boolean couldBeCastMultiline(List<String> pageSectionLineList, int startAt) {
		boolean couldBeCastMultiline;
		int previousIndexSubsectionMarkup = 0;
		int previousIndexListItem = 0;
		int hits = 0;
		int misses = 0;
		for (int j = startAt; j < pageSectionLineList.size(); j++) {
			String pageSectionLineSecondRun = pageSectionLineList.get(j);
			boolean hasSubsectionMarkupSecondRun = pageSectionLineSecondRun.startsWith(";");
			boolean isListItemSecondRun = pageSectionLineSecondRun.startsWith("*");
			final String subsectionTitleCandidate = getSubsectionTitleCandidate(pageSectionLineSecondRun);
			if (hasSubsectionMarkupSecondRun && STOPPING_SECTIONS.contains(subsectionTitleCandidate)) {
				break;
			}
			String firstPageLinkTitle = wikitextApi.getPageLinksFromWikitext(pageSectionLineSecondRun)
					.stream()
					.findFirst()
					.map(PageLink::getTitle)
					.orElse(null);

			if (hasSubsectionMarkupSecondRun) {
				if (previousIndexListItem + 1 == j
						&& movieExistingEntitiesHelper.isAnyKnownCharacter(subsectionTitleCandidate, firstPageLinkTitle)) {
					hits++;
				} else {
					misses++;
				}
				previousIndexSubsectionMarkup = j;
			} else if (isListItemSecondRun) {
				if (previousIndexSubsectionMarkup + 1 == j
						&& movieExistingEntitiesHelper.isAnyKnownPerformer(subsectionTitleCandidate, firstPageLinkTitle)) {
					hits++;
				} else {
					misses++;
				}
				previousIndexListItem = j;
			} else {
				misses++;
			}
		}
		couldBeCastMultiline = misses * 2 < hits;
		return couldBeCastMultiline;
	}

	private boolean isCastPageSection(PageSection creditsPageSection) {
		return creditsPageSection != null && "Cast".equals(creditsPageSection.getText());
	}

	private void addWikitextToPageSection(PageSection creditsPageSection, List<PageSection> creditsPageSectionList,
			List<String> creditsPageSectionWikitextLines) {
		if (creditsPageSection != null) {
			creditsPageSection.setWikitext(String.join("\n", creditsPageSectionWikitextLines));
			creditsPageSectionList.add(creditsPageSection);
		}
	}

	private boolean isNonInclude(String pageSectionLine) {
		return pageSectionLine.startsWith("<noinclude>") || pageSectionLine.startsWith("</noinclude>");
	}

	private boolean isInterwiki(String pageSectionLine) {
		return INTERWIKI.matcher(pageSectionLine).matches();
	}

	private boolean isSkippable(String pageSectionLine) {
		return SKIPPABLE_PAGE_SECTION_LINE_LIST.contains(pageSectionLine);
	}

}
