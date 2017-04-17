package com.cezarykluczynski.stapi.etl.common.processor;

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
public class WikitextStaffProcessor implements ItemProcessor<String, Set<Staff>> {

	private final WikitextApi wikitextApi;

	private final EntityLookupByNameService entityLookupByNameService;

	@Inject
	public WikitextStaffProcessor(WikitextApi wikitextApi, EntityLookupByNameService entityLookupByNameService) {
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
	}

	@Override
	public Set<Staff> process(String item) throws Exception {
		List<String> pageTitleList = wikitextApi.getPageTitlesFromWikitext(item);
		Set<Staff> staffSet = Sets.newHashSet();

		pageTitleList.forEach(pageLink -> entityLookupByNameService
				.findStaffByName(pageLink, MediaWikiSource.MEMORY_ALPHA_EN)
				.ifPresent(staffSet::add));

		return staffSet;
	}

}
