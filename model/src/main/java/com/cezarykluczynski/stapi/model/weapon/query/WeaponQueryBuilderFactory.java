package com.cezarykluczynski.stapi.model.weapon.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class WeaponQueryBuilderFactory extends AbstractQueryBuilderFactory<Weapon> {

	public WeaponQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Weapon.class);
	}

}
