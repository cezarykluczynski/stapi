package com.cezarykluczynski.stapi.model.weapon.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<Weapon, Long>, PageAwareRepository<Weapon>, WeaponRepositoryCustom {
}
