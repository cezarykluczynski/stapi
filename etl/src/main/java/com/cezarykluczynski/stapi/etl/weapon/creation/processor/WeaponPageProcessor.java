package com.cezarykluczynski.stapi.etl.weapon.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.weapon.creation.service.WeaponPageFilter;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponPageProcessor implements ItemProcessor<Page, Weapon> {

	private static final String LASER = "laser";
	private static final String PLASMA = "plasma";
	private static final String PHOTONIC = "photonic";
	private static final String PHASE = "phase";

	private final WeaponPageFilter weaponPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final TemplateFinder templateFinder;

	public WeaponPageProcessor(WeaponPageFilter weaponPageFilter, PageBindingService pageBindingService, UidGenerator uidGenerator,
		CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, TemplateFinder templateFinder) {
		this.weaponPageFilter = weaponPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.templateFinder = templateFinder;
	}

	@Override
	public Weapon process(Page item) throws Exception {
		if (weaponPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Weapon weapon = new Weapon();
		String name = TitleUtil.getNameFromTitle(item.getTitle());
		weapon.setName(name);
		weapon.setPage(pageBindingService.fromPageToPageEntity(item));
		weapon.setUid(uidGenerator.generateFromPage(weapon.getPage(), Weapon.class));

		boolean hasLaserTechnologyTemplate = templateFinder.hasTemplate(item, TemplateTitle.LASER_TECHNOLOGY);
		boolean hasPhaserTechnologyTemplate = templateFinder.hasTemplate(item, TemplateTitle.PHASER_TECHNOLOGY);
		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		weapon.setLaserTechnology(hasLaserTechnologyTemplate || StringUtils.containsIgnoreCase(name, LASER));
		weapon.setPlasmaTechnology(StringUtils.containsIgnoreCase(name, PLASMA));
		weapon.setPhotonicTechnology(StringUtils.containsIgnoreCase(name, PHOTONIC));
		weapon.setPhaserTechnology(hasPhaserTechnologyTemplate || StringUtils.containsIgnoreCase(name, PHASE));
		weapon.setHandHeldWeapon(categoryTitleList.contains(CategoryTitle.HAND_HELD_WEAPONS));
		weapon.setMirror(categoryTitleList.contains(CategoryTitle.MIRROR_UNIVERSE));
		weapon.setAlternateReality(categoryTitleList.contains(CategoryTitle.ALTERNATE_REALITY));

		return weapon;
	}

}
