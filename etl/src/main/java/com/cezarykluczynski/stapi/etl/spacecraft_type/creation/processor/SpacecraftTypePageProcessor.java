package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.service.SpacecraftTypePageFilter;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpacecraftTypePageProcessor implements ItemProcessor<Page, SpacecraftType> {

	private final SpacecraftTypePageFilter spacecraftTypePageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	@Override
	public SpacecraftType process(Page item) throws Exception {
		if (spacecraftTypePageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		SpacecraftType spacecraftType = new SpacecraftType();
		spacecraftType.setPage(pageBindingService.fromPageToPageEntity(item));
		spacecraftType.setUid(uidGenerator.generateFromPage(spacecraftType.getPage(), SpacecraftType.class));
		spacecraftType.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		return spacecraftType;
	}

}
