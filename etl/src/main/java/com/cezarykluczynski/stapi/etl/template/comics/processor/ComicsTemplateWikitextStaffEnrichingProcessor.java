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

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ComicsTemplateWikitextStaffEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<String, ComicsTemplate>> {

	private static final String WRITER = "writer";
	private static final String WRITTEN_BY = "Written by";
	private static final String ADDITIONAL_DIALOG_BY = "Additional dialog by";
	private static final String FEATURING_CONCEPTS = "Featuring concepts";
	private static final String ORIGINAL_SCRIPT_BY = "original script by";
	private static final String PLOT_BY = "Plot by";
	private static final String ARTIST = "Artist";
	private static final String ART_DIRECTOR = "Art Director";
	private static final String COVER_ART = "Cover Art";
	private static final String SCHEDULED_ARTIST = "Scheduled Artist";
	private static final String EDITOR = "Editor";
	private static final String GUEST_EDITOR = "Guest Editor";
	private static final String BASED_ON = "Based on";
	private static final String CONSULTANT = "consultant";
	private static final String WITH_THANKS_TO = "with thanks to";
	private static final String CONCEPT_BY = "concept by";
	private static final String KLINGON_LANGUAGE = "Klingon Language";
	private static final String SPECIAL_THANKS = "Special thanks";
	private static final String THANKS_TO = "Thanks to";
	private static final String PUBLISHER = "Publisher";
	private static final String PUBLISHED_BY = "Published by";
	private static final String PRODUCTION_BY = "Production by";
	private static final String PRODUCTION_DESIGNER = "Production Designer";
	private static final String WITH_CHARACTERS_CREATED_BY = "With characters created by";
	private static final String DEDICATED_TO = "Dedicated to ";
	private static final List<String> STAR_TREK_CREATED_BY = Lists.newArrayList("Star Trek", "created by");

	private WikitextListsExtractor wikitextListsExtractor;

	private WikitextApi wikitextApi;

	private EntityLookupByNameService entityLookupByNameService;

	@Inject
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
				// do nothing
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
		return StringUtil.containsAnyIgnoreCase(wikitextListText, Lists.newArrayList(WRITER, ORIGINAL_SCRIPT_BY))
				|| StringUtil.startsWithAnyIgnoreCase(wikitextListText, Lists.newArrayList(WRITTEN_BY, ADDITIONAL_DIALOG_BY, PLOT_BY,
				FEATURING_CONCEPTS));
	}

	private boolean isArtistsList(String wikitextListText) {
		return StringUtil.startsWithAnyIgnoreCase(wikitextListText, Lists.newArrayList(ARTIST, COVER_ART, SCHEDULED_ARTIST,
				ART_DIRECTOR));
	}

	private boolean isEditorsList(String wikitextListText) {
		return StringUtil.startsWithAnyIgnoreCase(wikitextListText, Lists.newArrayList(EDITOR, GUEST_EDITOR));
	}

	private boolean isStaffList(String wikitextListText) {
		return StringUtil.containsAnyIgnoreCase(wikitextListText, Lists.newArrayList(CONSULTANT, SPECIAL_THANKS, WITH_THANKS_TO, CONCEPT_BY,
				KLINGON_LANGUAGE, THANKS_TO, PRODUCTION_BY, PRODUCTION_DESIGNER)) || StringUtil.startsWithAnyIgnoreCase(wikitextListText,
				Lists.newArrayList(PUBLISHER, PUBLISHED_BY, WITH_CHARACTERS_CREATED_BY));
	}

}
