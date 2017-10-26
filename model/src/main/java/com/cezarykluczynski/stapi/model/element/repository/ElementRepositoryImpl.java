package com.cezarykluczynski.stapi.model.element.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.model.element.entity.Element_;
import com.cezarykluczynski.stapi.model.element.query.ElementQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ElementRepositoryImpl implements ElementRepositoryCustom {

	private final ElementQueryBuilderFactory elementQueryBuilderFactory;

	public ElementRepositoryImpl(ElementQueryBuilderFactory elementQueryBuilderFactory) {
		this.elementQueryBuilderFactory = elementQueryBuilderFactory;
	}

	@Override
	public Page<Element> findMatching(ElementRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Element> elementQueryBuilder = elementQueryBuilderFactory.createQueryBuilder(pageable);

		elementQueryBuilder.equal(Element_.uid, criteria.getUid());
		elementQueryBuilder.like(Element_.name, criteria.getName());
		elementQueryBuilder.like(Element_.symbol, criteria.getSymbol());
		elementQueryBuilder.equal(Element_.transuranium, criteria.getTransuranium());
		elementQueryBuilder.equal(Element_.gammaSeries, criteria.getGammaSeries());
		elementQueryBuilder.equal(Element_.hypersonicSeries, criteria.getHypersonicSeries());
		elementQueryBuilder.equal(Element_.megaSeries, criteria.getMegaSeries());
		elementQueryBuilder.equal(Element_.omegaSeries, criteria.getOmegaSeries());
		elementQueryBuilder.equal(Element_.transonicSeries, criteria.getTransonicSeries());
		elementQueryBuilder.equal(Element_.worldSeries, criteria.getWorldSeries());
		elementQueryBuilder.setSort(criteria.getSort());

		return elementQueryBuilder.findPage();
	}

}
