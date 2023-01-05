package com.cezarykluczynski.stapi.model.animal.query;

import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class AnimalQueryBuilderFactory extends AbstractQueryBuilderFactory<Animal> {

	public AnimalQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Animal.class);
	}

}
