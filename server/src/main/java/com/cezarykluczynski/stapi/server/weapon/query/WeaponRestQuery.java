package com.cezarykluczynski.stapi.server.weapon.query;

import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class WeaponRestQuery {

	private final WeaponBaseRestMapper weaponBaseRestMapper;

	private final PageMapper pageMapper;

	private final WeaponRepository weaponRepository;

	public WeaponRestQuery(WeaponBaseRestMapper weaponBaseRestMapper, PageMapper pageMapper, WeaponRepository weaponRepository) {
		this.weaponBaseRestMapper = weaponBaseRestMapper;
		this.pageMapper = pageMapper;
		this.weaponRepository = weaponRepository;
	}

	public Page<Weapon> query(WeaponRestBeanParams weaponRestBeanParams) {
		WeaponRequestDTO weaponRequestDTO = weaponBaseRestMapper.mapBase(weaponRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(weaponRestBeanParams);
		return weaponRepository.findMatching(weaponRequestDTO, pageRequest);
	}

}
