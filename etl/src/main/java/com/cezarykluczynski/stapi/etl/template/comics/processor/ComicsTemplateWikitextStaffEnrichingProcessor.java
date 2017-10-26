package com.cezarykluczynski.stapi.etl.template.comics.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.WikitextList;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.common.service.WikitextListsExtractor;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ComicsTemplateWikitextStaffEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<String, ComicsTemplate>> {

	private static final String BASED_ON = "Based on";
	private static final String DEDICATED_TO = "Dedicated to ";
	private static final List<String> STAR_TREK_CREATED_BY = Lists.newArrayList("Star Trek", "created by");

	private static final List<String> WRITERS_TITLES = Lists.newArrayList("writer", "original script by");
	private static final List<String> WRITERS_PREFIXES = Lists.newArrayList("Written by", "Additional dialog by", "Plot by", "Featuring concepts");
	private static final List<String> ARTISTS_PREFIXES = Lists.newArrayList("Artist", "Cover Art", "Scheduled Artist", "Art Director", "Pencils",
			"Inks", "Colorists", "Letterers", "Art by", "Interior artist", "Inkers", "Colors by", "Letters by", "Interior art", "Color art",
			"Letter art", "Cover painted by");
	private static final List<String> EDITORS_PREFIXES = Lists.newArrayList("Editor", "Guest Editor", "Collection Edits", "Collection edited by",
			"Edits by", "Edited by");
	private static final List<String> STAFF_TITLES = Lists.newArrayList("Publisher", "Published by", "With characters created by",
			"Collection Design");
	private static final List<String> STAFF_PREFIXES = Lists.newArrayList("consultant", "Special thanks", "with thanks to", "concept by",
			"Klingon Language", "Thanks to", "Production by", "Production Designer", "Introduction by", "Book design by", "Design:", "Designed by",
			"Collection");

	private final WikitextListsExtractor wikitextListsExtractor;

	private final WikitextApi wikitextApi;

	private final EntityLookupByNameService entityLookupByNameService;

	public ComicsTemplateWikitextStaffEnrichingProcessor(WikitextListsExtractor wikitextListsExtractor, WikitextApi wikitextApi,
			EntityLookupByNameService entityLookupByNameService) {
		this.wikitextListsExtractor = wikitextListsExtractor;
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public void enrich(EnrichablePair<String, ComicsTemplate> enrichablePair) throws Exception {
		String pageSectionWikitext = enrichablePair.getInput();
		ComicsTemplate comicsTemplate = enrichablePair.getOutput();
		List<WikitextList> wikitextListList = wikitextListsExtractor.extractListsFromWikitext(pageSectionWikitext);

		for (WikitextList wikitextList : wikitextListList) {
			String wikitextListText = wikitextList.getText();

			if (shouldBeIngored(wikitextListText)) {
				return;
			} else if (isWritersList(wikitextListText)) {
				comicsTemplate.getWriters().addAll(staffFromWikitextList(wikitextList));
			} else if (isArtistsList(wikitextListText)) {
				comicsTemplate.getArtists().addAll(staffFromWikitextList(wikitextList));
			} else if (isEditorsList(wikitextListText)) {
				comicsTemplate.getEditors().addAll(staffFromWikitextList(wikitextList));
			} else if (isStaffList(wikitextListText)) {
				comicsTemplate.getStaff().addAll(staffFromWikitextList(wikitextList));
			} else {
				log.info("Unrecognized section \"{}\" found in {}", wikitextListText, comicsTemplate.getTitle());
			}
		}
	}

	private boolean shouldBeIngored(String wikitextListText) {
		return STAR_TREK_CREATED_BY.stream().allMatch(wikitextListText::contains) || wikitextListText.contains(BASED_ON)
				|| wikitextListText.startsWith(DEDICATED_TO);
	}

	private Set<Staff> staffFromWikitextList(WikitextList wikitextList) {
		List<String> wikitextListFlatten = flattenWikitextList(wikitextList);
		Set<Staff> staffSet = Sets.newHashSet();

		for (String wikitext : wikitextListFlatten) {
			List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(wikitext);
			pageTitleList.forEach(pageTitle -> entityLookupByNameService
					.findStaffByName(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN)
					.ifPresent(staffSet::add));
		}

		return staffSet;
	}

	private List<String> flattenWikitextList(WikitextList wikitextList) {
		List<String> wikitextListTextList = Lists.newArrayList();
		wikitextListTextList.add(wikitextList.getText());

		wikitextList.getChildren().forEach(wikitextListChild -> wikitextListTextList.addAll(flattenWikitextList(wikitextListChild)));

		return wikitextListTextList;
	}

	private boolean isWritersList(String wikitextListText) {
		return StringUtil.containsAnyIgnoreCase(wikitextListText, WRITERS_TITLES)
				|| StringUtil.startsWithAnyIgnoreCase(wikitextListText, WRITERS_PREFIXES);
	}

	private boolean isArtistsList(String wikitextListText) {
		return StringUtil.startsWithAnyIgnoreCase(wikitextListText, ARTISTS_PREFIXES);
	}

	private boolean isEditorsList(String wikitextListText) {
		return StringUtil.startsWithAnyIgnoreCase(wikitextListText, EDITORS_PREFIXES);
	}

	private boolean isStaffList(String wikitextListText) {
		return StringUtil.containsAnyIgnoreCase(wikitextListText, STAFF_PREFIXES) || StringUtil.startsWithAnyIgnoreCase(wikitextListText,
				STAFF_TITLES);
	}

}
