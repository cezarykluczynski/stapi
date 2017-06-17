package com.cezarykluczynski.stapi.model.literature.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;

public interface LiteratureRepositoryCustom extends CriteriaMatcher<LiteratureRequestDTO, Literature> {
}
