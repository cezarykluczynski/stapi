package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor;

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftTypePageProcessor implements ItemProcessor<Page, SpacecraftType> {

	private final CategorySortingService categorySortingService;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	public SpacecraftTypePageProcessor(CategorySortingService categorySortingService, PageBindingService pageBindingService,
			UidGenerator uidGenerator) {
		this.categorySortingService = categorySortingService;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
	}

	@Override
	public SpacecraftType process(Page item) throws Exception {
		if (categorySortingService.isSortedOnTopOfAnyOfCategories(item, Lists.newArrayList(CategoryTitle.SPACECRAFT_CLASSIFICATIONS))) {
			return null;
		}

		SpacecraftType spacecraftType = new SpacecraftType();
		com.cezarykluczynski.stapi.model.page.entity.Page page = pageBindingService.fromPageToPageEntity(item);
		spacecraftType.setUid(uidGenerator.generateFromPage(page, SpacecraftType.class));
		spacecraftType.setPage(page);
		spacecraftType.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		return spacecraftType;
	}

}
