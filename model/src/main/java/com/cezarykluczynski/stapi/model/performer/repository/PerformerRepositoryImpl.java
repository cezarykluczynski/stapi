package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PerformerRepositoryImpl implements PerformerRepositoryCustom {

	private JpaContext jpaContext;

	@Inject
	public PerformerRepositoryImpl(JpaContext jpaContext) {
		this.jpaContext = jpaContext;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Performer> findMatching(PerformerRequestDTO performerRequestDTO, Pageable pageable) {
		EntityManager entityManager = jpaContext.getEntityManagerByManagedType(Performer.class);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Performer> performerCriteriaQuery = criteriaBuilder.createQuery(Performer.class);
		Root<Performer> performerRoot = performerCriteriaQuery.from(Performer.class);
		performerCriteriaQuery.select(performerRoot);

		CriteriaQuery<Long> countCriteriaQuery = criteriaBuilder.createQuery(Long.class);
		countCriteriaQuery.from(Performer.class);
		countCriteriaQuery.select(criteriaBuilder.count(performerRoot));

		List<Predicate> performerCriteria = Lists.newArrayList();

		if (performerRequestDTO.getName() != null) {
			performerCriteria.add(criteriaBuilder.like(performerRoot.<String>get("name"), wildcardLike(performerRequestDTO.getName())));
		}

		if (performerCriteria.size() > 0) {
			Predicate performerPredicate = criteriaBuilder.and(performerCriteria.toArray(new Predicate[performerCriteria.size()]));
			performerCriteriaQuery.where(performerPredicate);
			countCriteriaQuery.where(performerPredicate);
		}

		TypedQuery<Performer> performerTypedQuery = entityManager.createQuery(performerCriteriaQuery);
		performerTypedQuery.setMaxResults(pageable.getPageSize());
		performerTypedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageNumber());
		TypedQuery<Long> countTypedQuery = entityManager.createQuery(countCriteriaQuery);

		List<Performer> performerList = performerTypedQuery.getResultList();
		Long count = countTypedQuery.getSingleResult();
		performerTypedQuery.getMaxResults();

		return new PageImpl<>(performerList, pageable, count);
	}

	protected String wildcardLike(String subject) {
		return "%" + subject.replaceAll("\\s", "%") + "%";
	}

}
