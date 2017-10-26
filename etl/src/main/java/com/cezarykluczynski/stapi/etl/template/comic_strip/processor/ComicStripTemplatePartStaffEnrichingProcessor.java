package com.cezarykluczynski.stapi.etl.template.comic_strip.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplate;
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplateParameter;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ComicStripTemplatePartStaffEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<ComicStripTemplate> {

	private final WikitextApi wikitextApi;

	private final EntityLookupByNameService entityLookupByNameService;

	public ComicStripTemplatePartStaffEnrichingProcessor(WikitextApi wikitextApi, EntityLookupByNameService entityLookupByNameService) {
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, ComicStripTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		ComicStripTemplate comicsTemplate = enrichablePair.getOutput();
		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(templatePart.getValue());
		Set<Staff> staffSet = Sets.newHashSet();
		String templatePartKey = templatePart.getKey();

		pageTitleList.forEach(pageLink -> entityLookupByNameService
				.findStaffByName(pageLink, MediaWikiSource.MEMORY_ALPHA_EN)
				.ifPresent(staffSet::add));

		if (ComicStripTemplateParameter.WRITER.equals(templatePartKey)) {
			comicsTemplate.getWriters().addAll(staffSet);
		} else if (ComicStripTemplateParameter.ARTIST.equals(templatePartKey)) {
			comicsTemplate.getArtists().addAll(staffSet);
		}
	}

}
