package com.cezarykluczynski.stapi.model.comics.repository;

import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicsRepository extends JpaRepository<Comics, Long>, PageAwareRepository<Comics>, ComicsRepositoryCustom {
}
