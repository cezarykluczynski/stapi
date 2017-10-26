package com.cezarykluczynski.stapi.etl.movie.creation.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
@SuppressWarnings("MultipleStringLiterals")
public class MovieClosingCreditsProcessor implements ItemProcessor<Page, List<PageSection>> {

	private static final List<String> SKIPPABLE_PAGE_SECTION_LINE_LIST = Lists.newArrayList(
			"Miniature Crash Sequence Photography Unit", "A [[Rick Berman]] Production",
			"[[Category:Star Trek credits|Search for Spock, The]]",
			"[[Category:Star Trek credits|Voyage Home, The]]"
	);

	private static final List<String> SECTIONS_WITHOUT_DEFINITION_MARKUP = Lists.newArrayList(
			"Rigging Grips:", "Casting Assistants:"
	);

	private static final Pattern INTERWIKI = Pattern.compile("\\[\\[[a-z\\-]{2,5}:.*");

	private final PageApi pageApi;

	private final PageSectionExtractor pageSectionExtractor;

	public MovieClosingCreditsProcessor(PageApi pageApi, PageSectionExtractor pageSectionExtractor) {
		this.pageApi = pageApi;
		this.pageSectionExtractor = pageSectionExtractor;
	}

	@Override
	public List<PageSection> process(Page page) throws Exception {
		List<PageSection> creditsPageSectionList = Lists.newArrayList();
		String pageTitle = page.getTitle();
		Page creditsPage = pageApi.getPage("Credits for " + pageTitle, page.getMediaWikiSource());

		if (creditsPage == null) {
			log.warn("Could not find credits page fom movie {}", pageTitle);
			return creditsPageSectionList;
		}

		List<PageSection> pageSectionList = pageSectionExtractor.findByTitles(creditsPage, "Closing credits",
				"Closing Credits", "Cast", "Crew");

		if (CollectionUtils.isEmpty(pageSectionList)) {
			log.info("Could not find closing credits section for page {}", pageTitle);
			return creditsPageSectionList;
		}

		List<String> pageSectionLineList = Lists.newArrayList();

		pageSectionList.forEach(pageSection -> {
			if (Lists.newArrayList("Cast", "Crew").contains(pageSection.getText())) {
				pageSectionLineList.add(";" + pageSection.getText());
			}
			pageSectionLineList.addAll(Lists.newArrayList(pageSection.getWikitext().split("\n")));
		});

		return getPageSectionList(pageSectionLineList, pageTitle, creditsPageSectionList);
	}

	private List<PageSection> getPageSectionList(List<String> pageSectionLineList, String pageTitle, List<PageSection> creditsPageSectionList) {
		PageSection creditsPageSection = null;
		List<String> creditsPageSectionWikitextLines = null;
		boolean createNewSection = false;
		String previousLine = null;

		for (String pageSectionLine : pageSectionLineList) {
			boolean isSectionWithoutDefinitionMarkup = SECTIONS_WITHOUT_DEFINITION_MARKUP.contains(pageSectionLine);
			boolean hasDefinitionMarkup = pageSectionLine.startsWith(";");
			boolean isListItem = pageSectionLine.startsWith("*");
			boolean isCast = isCastPageSection(creditsPageSection);
			boolean couldBeCastMultiline = hasDefinitionMarkup || isListItem && previousLine != null;
			if (creditsPageSection != null && ";Stunt Coordinator:".equals(pageSectionLine)) {
				createNewSection = true;
			} else if (isCast && couldBeCastMultiline) {
				if (hasDefinitionMarkup) {
					previousLine = pageSectionLine;
				} else {
					creditsPageSectionWikitextLines.add(previousLine + " " + pageSectionLine);
				}
			} else if (hasDefinitionMarkup || isSectionWithoutDefinitionMarkup) {
				createNewSection = true;
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
				creditsPageSection.setText(isSectionWithoutDefinitionMarkup ? pageSectionLine : pageSectionLine.substring(1));
				creditsPageSectionWikitextLines = Lists.newArrayList();
				createNewSection = false;
			}
		}

		addWikitextToPageSection(creditsPageSection, creditsPageSectionList, creditsPageSectionWikitextLines);

		return creditsPageSectionList;
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
