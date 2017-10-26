package com.cezarykluczynski.stapi.server.magazine.query;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest;
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseSoapMapper;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MagazineSoapQuery {

	private final MagazineBaseSoapMapper magazineBaseSoapMapper;

	private final MagazineFullSoapMapper magazineFullSoapMapper;

	private final PageMapper pageMapper;

	private final MagazineRepository magazineRepository;

	public MagazineSoapQuery(MagazineBaseSoapMapper magazineBaseSoapMapper, MagazineFullSoapMapper magazineFullSoapMapper, PageMapper pageMapper,
			MagazineRepository magazineRepository) {
		this.magazineBaseSoapMapper = magazineBaseSoapMapper;
		this.magazineFullSoapMapper = magazineFullSoapMapper;
		this.pageMapper = pageMapper;
		this.magazineRepository = magazineRepository;
	}

	public Page<Magazine> query(MagazineBaseRequest magazineBaseRequest) {
		MagazineRequestDTO magazineRequestDTO = magazineBaseSoapMapper.mapBase(magazineBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(magazineBaseRequest.getPage());
		return magazineRepository.findMatching(magazineRequestDTO, pageRequest);
	}

	public Page<Magazine> query(MagazineFullRequest magazineFullRequest) {
		MagazineRequestDTO magazineRequestDTO = magazineFullSoapMapper.mapFull(magazineFullRequest);
		return magazineRepository.findMatching(magazineRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
