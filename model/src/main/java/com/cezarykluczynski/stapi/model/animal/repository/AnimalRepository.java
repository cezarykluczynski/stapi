package com.cezarykluczynski.stapi.model.animal.repository;

import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long>, PageAwareRepository<Animal>, AnimalRepositoryCustom {
}
