package com.cezarykluczynski.stapi.etl.weapon.creation.processor;

import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WeaponWriter implements ItemWriter<Weapon> {

	private final WeaponRepository weaponRepository;

	public WeaponWriter(WeaponRepository weaponRepository) {
		this.weaponRepository = weaponRepository;
	}

	@Override
	public void write(Chunk<? extends Weapon> items) throws Exception {
		weaponRepository.saveAll(items.getItems());
	}

}
