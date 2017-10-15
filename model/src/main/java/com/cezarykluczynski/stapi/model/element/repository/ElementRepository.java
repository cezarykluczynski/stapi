package com.cezarykluczynski.stapi.model.element.repository;

import com.cezarykluczynski.stapi.model.element.entity.Element;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementRepository extends JpaRepository<Element, Long>, ElementRepositoryCustom {
}
