package com.cezarykluczynski.stapi.model.literature.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiteratureRepository extends JpaRepository<Literature, Long>, PageAwareRepository<Food>, LiteratureRepositoryCustom {
}
