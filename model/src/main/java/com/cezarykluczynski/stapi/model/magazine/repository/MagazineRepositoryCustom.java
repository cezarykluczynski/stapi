package com.cezarykluczynski.stapi.model.magazine.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;

public interface MagazineRepositoryCustom extends CriteriaMatcher<MagazineRequestDTO, Magazine> {
}
