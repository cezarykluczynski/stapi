package com.cezarykluczynski.stapi.model.element.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO;
import com.cezarykluczynski.stapi.model.element.entity.Element;

public interface ElementRepositoryCustom extends CriteriaMatcher<ElementRequestDTO, Element> {
}
