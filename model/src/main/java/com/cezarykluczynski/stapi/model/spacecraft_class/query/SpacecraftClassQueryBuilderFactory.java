package com.cezarykluczynski.stapi.model.spacecraft_class.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassQueryBuilderFactory extends AbstractQueryBuilderFactory<SpacecraftClass> {

	public SpacecraftClassQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, SpacecraftClass.class);
	}

}
