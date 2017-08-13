package com.cezarykluczynski.stapi.model.weapon.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;

public interface WeaponRepositoryCustom extends CriteriaMatcher<WeaponRequestDTO, Weapon> {
}
