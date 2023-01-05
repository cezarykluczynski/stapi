package com.cezarykluczynski.stapi.model.common.repository

import com.cezarykluczynski.stapi.model.performer.entity.Performer
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class PageAwareQueryBuilderSingletonFactoryProducerTest extends Specification {

	private JpaContext jpaContextMock

	private PageAwareQueryBuilderSingletonFactoryProducer pageAwareQueryBuilderSingletonFactoryProducer

	void setup() {
		jpaContextMock = Mock()
		pageAwareQueryBuilderSingletonFactoryProducer = new PageAwareQueryBuilderSingletonFactoryProducer(jpaContextMock)
	}

	void "creates factories only once"() {
		when:
		PageAwareQueryBuilderFactory pageAwareQueryBuilderFactory1 = pageAwareQueryBuilderSingletonFactoryProducer.create(Performer)
		PageAwareQueryBuilderFactory pageAwareQueryBuilderFactory2 = pageAwareQueryBuilderSingletonFactoryProducer.create(Performer)

		then:
		pageAwareQueryBuilderFactory1 == pageAwareQueryBuilderFactory2
	}

}
