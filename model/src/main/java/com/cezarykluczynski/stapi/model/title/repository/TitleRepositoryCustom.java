package com.cezarykluczynski.stapi.model.title.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO;
import com.cezarykluczynski.stapi.model.title.entity.Title;

public interface TitleRepositoryCustom extends CriteriaMatcher<TitleRequestDTO, Title> {
}
