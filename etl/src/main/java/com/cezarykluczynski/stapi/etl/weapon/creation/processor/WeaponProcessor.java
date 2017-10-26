package com.cezarykluczynski.stapi.etl.weapon.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class WeaponProcessor extends CompositeItemProcessor<PageHeader, Weapon> {

	public WeaponProcessor(PageHeaderProcessor pageHeaderProcessor, WeaponPageProcessor weaponPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, weaponPageProcessor));
	}

}
