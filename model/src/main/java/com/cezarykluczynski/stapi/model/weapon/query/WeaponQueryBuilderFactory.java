package com.cezarykluczynski.stapi.model.weapon.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class WeaponQueryBuilderFactory extends AbstractQueryBuilderFactory<Weapon> {

	@Inject
	public WeaponQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Weapon.class);
	}

}
