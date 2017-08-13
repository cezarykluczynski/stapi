package com.cezarykluczynski.stapi.server.weapon.reader;

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullResponse;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseSoapMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullSoapMapper;
import com.cezarykluczynski.stapi.server.weapon.query.WeaponSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WeaponSoapReader implements BaseReader<WeaponBaseRequest, WeaponBaseResponse>,
		FullReader<WeaponFullRequest, WeaponFullResponse> {

	private final WeaponSoapQuery weaponSoapQuery;

	private final WeaponBaseSoapMapper weaponBaseSoapMapper;

	private final WeaponFullSoapMapper weaponFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public WeaponSoapReader(WeaponSoapQuery weaponSoapQuery, WeaponBaseSoapMapper weaponBaseSoapMapper,
			WeaponFullSoapMapper weaponFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.weaponSoapQuery = weaponSoapQuery;
		this.weaponBaseSoapMapper = weaponBaseSoapMapper;
		this.weaponFullSoapMapper = weaponFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public WeaponBaseResponse readBase(WeaponBaseRequest input) {
		Page<Weapon> weaponPage = weaponSoapQuery.query(input);
		WeaponBaseResponse weaponResponse = new WeaponBaseResponse();
		weaponResponse.setPage(pageMapper.fromPageToSoapResponsePage(weaponPage));
		weaponResponse.setSort(sortMapper.map(input.getSort()));
		weaponResponse.getWeapons().addAll(weaponBaseSoapMapper.mapBase(weaponPage.getContent()));
		return weaponResponse;
	}

	@Override
	public WeaponFullResponse readFull(WeaponFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Weapon> weaponPage = weaponSoapQuery.query(input);
		WeaponFullResponse weaponFullResponse = new WeaponFullResponse();
		weaponFullResponse.setWeapon(weaponFullSoapMapper.mapFull(Iterables.getOnlyElement(weaponPage.getContent(), null)));
		return weaponFullResponse;
	}

}
