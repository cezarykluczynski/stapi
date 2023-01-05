package com.cezarykluczynski.stapi.model.material.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class MaterialQueryBuilderFactory extends AbstractQueryBuilderFactory<Material> {

	public MaterialQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, Material.class);
	}

}
