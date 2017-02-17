package com.cezarykluczynski.stapi.model.comics.repository;

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface ComicsRepositoryCustom extends CriteriaMatcher<ComicsRequestDTO, Comics> {
}
