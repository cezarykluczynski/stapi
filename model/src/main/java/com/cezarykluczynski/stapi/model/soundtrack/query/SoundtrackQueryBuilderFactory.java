package com.cezarykluczynski.stapi.model.soundtrack.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackQueryBuilderFactory extends AbstractQueryBuilderFactory<Soundtrack> {

	public SoundtrackQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, Soundtrack.class);
	}

}
