package com.cezarykluczynski.stapi.server.magazine.query;

import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MagazineRestQuery {

	private final MagazineBaseRestMapper magazineBaseRestMapper;

	private final PageMapper pageMapper;

	private final MagazineRepository magazineRepository;

	public MagazineRestQuery(MagazineBaseRestMapper magazineBaseRestMapper, PageMapper pageMapper, MagazineRepository magazineRepository) {
		this.magazineBaseRestMapper = magazineBaseRestMapper;
		this.pageMapper = pageMapper;
		this.magazineRepository = magazineRepository;
	}

	public Page<Magazine> query(MagazineRestBeanParams magazineRestBeanParams) {
		MagazineRequestDTO magazineRequestDTO = magazineBaseRestMapper.mapBase(magazineRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(magazineRestBeanParams);
		return magazineRepository.findMatching(magazineRequestDTO, pageRequest);
	}

}
