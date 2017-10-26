package com.cezarykluczynski.stapi.server.weapon.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFullResponse;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullRestMapper;
import com.cezarykluczynski.stapi.server.weapon.query.WeaponRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WeaponRestReader implements BaseReader<WeaponRestBeanParams, WeaponBaseResponse>, FullReader<String, WeaponFullResponse> {

	private final WeaponRestQuery weaponRestQuery;

	private final WeaponBaseRestMapper weaponBaseRestMapper;

	private final WeaponFullRestMapper weaponFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public WeaponRestReader(WeaponRestQuery weaponRestQuery, WeaponBaseRestMapper weaponBaseRestMapper, WeaponFullRestMapper weaponFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.weaponRestQuery = weaponRestQuery;
		this.weaponBaseRestMapper = weaponBaseRestMapper;
		this.weaponFullRestMapper = weaponFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public WeaponBaseResponse readBase(WeaponRestBeanParams input) {
		Page<Weapon> weaponPage = weaponRestQuery.query(input);
		WeaponBaseResponse weaponResponse = new WeaponBaseResponse();
		weaponResponse.setPage(pageMapper.fromPageToRestResponsePage(weaponPage));
		weaponResponse.setSort(sortMapper.map(input.getSort()));
		weaponResponse.getWeapons().addAll(weaponBaseRestMapper.mapBase(weaponPage.getContent()));
		return weaponResponse;
	}

	@Override
	public WeaponFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		WeaponRestBeanParams weaponRestBeanParams = new WeaponRestBeanParams();
		weaponRestBeanParams.setUid(uid);
		Page<Weapon> weaponPage = weaponRestQuery.query(weaponRestBeanParams);
		WeaponFullResponse weaponResponse = new WeaponFullResponse();
		weaponResponse.setWeapon(weaponFullRestMapper.mapFull(Iterables.getOnlyElement(weaponPage.getContent(), null)));
		return weaponResponse;
	}
}
