package com.cezarykluczynski.stapi.server.weapon.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2FullResponse;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponV2RestBeanParams;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseRestMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullRestMapper;
import com.cezarykluczynski.stapi.server.weapon.query.WeaponRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WeaponV2RestReader implements BaseReader<WeaponV2RestBeanParams, WeaponV2BaseResponse>,
		FullReader<String, WeaponV2FullResponse> {

	private final WeaponRestQuery weaponRestQuery;

	private final WeaponBaseRestMapper weaponBaseRestMapper;

	private final WeaponFullRestMapper weaponFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public WeaponV2RestReader(WeaponRestQuery weaponRestQuery, WeaponBaseRestMapper weaponBaseRestMapper,
			WeaponFullRestMapper weaponFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.weaponRestQuery = weaponRestQuery;
		this.weaponBaseRestMapper = weaponBaseRestMapper;
		this.weaponFullRestMapper = weaponFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public WeaponV2BaseResponse readBase(WeaponV2RestBeanParams input) {
		Page<Weapon> weaponV2Page = weaponRestQuery.query(input);
		WeaponV2BaseResponse weaponV2Response = new WeaponV2BaseResponse();
		weaponV2Response.setPage(pageMapper.fromPageToRestResponsePage(weaponV2Page));
		weaponV2Response.setSort(sortMapper.map(input.getSort()));
		weaponV2Response.getWeapons().addAll(weaponBaseRestMapper.mapV2Base(weaponV2Page.getContent()));
		return weaponV2Response;
	}

	@Override
	public WeaponV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		WeaponV2RestBeanParams weaponV2RestBeanParams = new WeaponV2RestBeanParams();
		weaponV2RestBeanParams.setUid(uid);
		Page<Weapon> weaponPage = weaponRestQuery.query(weaponV2RestBeanParams);
		WeaponV2FullResponse weaponV2Response = new WeaponV2FullResponse();
		weaponV2Response.setWeapon(weaponFullRestMapper.mapV2Full(Iterables.getOnlyElement(weaponPage.getContent(), null)));
		return weaponV2Response;
	}
}
