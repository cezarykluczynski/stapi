package com.cezarykluczynski.stapi.etl.weapon.creation.processor;


import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WeaponWriter implements ItemWriter<Weapon> {

	private final WeaponRepository weaponRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public WeaponWriter(WeaponRepository weaponRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.weaponRepository = weaponRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Weapon> items) throws Exception {
		weaponRepository.save(process(items));
	}

	private List<Weapon> process(List<? extends Weapon> weaponList) {
		List<Weapon> weaponListWithoutExtends = fromExtendsListToWeaponList(weaponList);
		return filterDuplicates(weaponListWithoutExtends);
	}

	private List<Weapon> fromExtendsListToWeaponList(List<? extends Weapon> weaponList) {
		return weaponList
				.stream()
				.map(pageAware -> (Weapon) pageAware)
				.collect(Collectors.toList());
	}

	private List<Weapon> filterDuplicates(List<Weapon> weaponList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(weaponList.stream()
				.map(weapon -> (PageAware) weapon)
				.collect(Collectors.toList()), Weapon.class).stream()
				.map(pageAware -> (Weapon) pageAware)
				.collect(Collectors.toList());
	}

}
