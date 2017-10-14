package com.cezarykluczynski.stapi.model.animal.repository;

import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface AnimalRepositoryCustom extends CriteriaMatcher<AnimalRequestDTO, Animal> {
}
