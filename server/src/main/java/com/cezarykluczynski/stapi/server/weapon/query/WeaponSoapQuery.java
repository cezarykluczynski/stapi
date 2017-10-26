package com.cezarykluczynski.stapi.server.weapon.query;

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest;
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponBaseSoapMapper;
import com.cezarykluczynski.stapi.server.weapon.mapper.WeaponFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class WeaponSoapQuery {

	private final WeaponBaseSoapMapper weaponBaseSoapMapper;

	private final WeaponFullSoapMapper weaponFullSoapMapper;

	private final PageMapper pageMapper;

	private final WeaponRepository weaponRepository;

	public WeaponSoapQuery(WeaponBaseSoapMapper weaponBaseSoapMapper, WeaponFullSoapMapper weaponFullSoapMapper, PageMapper pageMapper,
			WeaponRepository weaponRepository) {
		this.weaponBaseSoapMapper = weaponBaseSoapMapper;
		this.weaponFullSoapMapper = weaponFullSoapMapper;
		this.pageMapper = pageMapper;
		this.weaponRepository = weaponRepository;
	}

	public Page<Weapon> query(WeaponBaseRequest weaponBaseRequest) {
		WeaponRequestDTO weaponRequestDTO = weaponBaseSoapMapper.mapBase(weaponBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(weaponBaseRequest.getPage());
		return weaponRepository.findMatching(weaponRequestDTO, pageRequest);
	}

	public Page<Weapon> query(WeaponFullRequest weaponFullRequest) {
		WeaponRequestDTO seriesRequestDTO = weaponFullSoapMapper.mapFull(weaponFullRequest);
		return weaponRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
