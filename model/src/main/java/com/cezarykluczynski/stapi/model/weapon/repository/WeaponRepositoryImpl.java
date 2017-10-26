package com.cezarykluczynski.stapi.model.weapon.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon_;
import com.cezarykluczynski.stapi.model.weapon.query.WeaponQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class WeaponRepositoryImpl implements WeaponRepositoryCustom {

	private final WeaponQueryBuilderFactory weaponQueryBuilderFactory;

	public WeaponRepositoryImpl(WeaponQueryBuilderFactory weaponQueryBuilderFactory) {
		this.weaponQueryBuilderFactory = weaponQueryBuilderFactory;
	}

	@Override
	public Page<Weapon> findMatching(WeaponRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Weapon> weaponQueryBuilder = weaponQueryBuilderFactory.createQueryBuilder(pageable);

		weaponQueryBuilder.equal(Weapon_.uid, criteria.getUid());
		weaponQueryBuilder.like(Weapon_.name, criteria.getName());

		weaponQueryBuilder.equal(Weapon_.handHeldWeapon, criteria.getHandHeldWeapon());
		weaponQueryBuilder.equal(Weapon_.laserTechnology, criteria.getLaserTechnology());
		weaponQueryBuilder.equal(Weapon_.plasmaTechnology, criteria.getPlasmaTechnology());
		weaponQueryBuilder.equal(Weapon_.photonicTechnology, criteria.getPhotonicTechnology());
		weaponQueryBuilder.equal(Weapon_.phaserTechnology, criteria.getPhaserTechnology());
		weaponQueryBuilder.equal(Weapon_.mirror, criteria.getMirror());
		weaponQueryBuilder.equal(Weapon_.alternateReality, criteria.getAlternateReality());
		weaponQueryBuilder.setSort(criteria.getSort());

		return weaponQueryBuilder.findPage();
	}

}
