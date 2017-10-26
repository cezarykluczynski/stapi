package com.cezarykluczynski.stapi.model.animal.repository;

import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.animal.entity.Animal_;
import com.cezarykluczynski.stapi.model.animal.query.AnimalQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class AnimalRepositoryImpl implements AnimalRepositoryCustom {

	private final AnimalQueryBuilderFactory animalQueryBuilderFactory;

	public AnimalRepositoryImpl(AnimalQueryBuilderFactory animalQueryBuilderFactory) {
		this.animalQueryBuilderFactory = animalQueryBuilderFactory;
	}

	@Override
	public Page<Animal> findMatching(AnimalRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Animal> animalQueryBuilder = animalQueryBuilderFactory.createQueryBuilder(pageable);

		animalQueryBuilder.equal(Animal_.uid, criteria.getUid());
		animalQueryBuilder.like(Animal_.name, criteria.getName());
		animalQueryBuilder.equal(Animal_.earthAnimal, criteria.getEarthAnimal());
		animalQueryBuilder.equal(Animal_.earthInsect, criteria.getEarthInsect());
		animalQueryBuilder.equal(Animal_.avian, criteria.getAvian());
		animalQueryBuilder.equal(Animal_.canine, criteria.getCanine());
		animalQueryBuilder.equal(Animal_.feline, criteria.getFeline());
		animalQueryBuilder.setSort(criteria.getSort());

		return animalQueryBuilder.findPage();
	}

}
