package com.cezarykluczynski.stapi.model.common.repository

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Path
import javax.persistence.criteria.Root
import javax.persistence.metamodel.Attribute
import javax.persistence.metamodel.EntityType
import javax.persistence.metamodel.Metamodel

class InPageAwareRepositoryPageFinderTest extends Specification {

	private static final Long PAGE_ID_1 = 1L
	private static final Long PAGE_ID_2 = 2L

	private InPageAwareRepositoryPageFinder inPageAwareRepositoryPageFinder

	private JpaContext jpaContextMock

	private CriteriaQuery<Performer> baseCriteriaQuery

	private CriteriaQuery<Long> countCriteriaQuery

	private CriteriaBuilder criteriaBuilder

	private EntityManager entityManager

	private Root baseRoot

	private Path path

	private Set<Long> pageIds

	private TypedQuery baseTypedQuery

	def setup() {
		jpaContextMock = Mock(JpaContext)
		inPageAwareRepositoryPageFinder = new InPageAwareRepositoryPageFinder(jpaContextMock)

		given:
		baseCriteriaQuery = Mock(CriteriaQuery)
		countCriteriaQuery = Mock(CriteriaQuery)
		criteriaBuilder = Mock(CriteriaBuilder) {
			createQuery(Performer) >> baseCriteriaQuery
			createQuery(Long) >> countCriteriaQuery
		}
		entityManager = Mock(EntityManager) {
			getCriteriaBuilder() >> criteriaBuilder
			getMetamodel() >> Mock(Metamodel) {
				entity(Performer) >> Mock(EntityType) {
					getAttributes() >> Sets.newHashSet(
							Mock(Attribute) {
								getJavaType() >> Page.class
								getName() >> "page"
							}
					)
				}
			}
		}
		baseRoot = Mock(Root)
		path = Mock(Path)
		pageIds = Sets.newHashSet(PAGE_ID_1, PAGE_ID_2)
		baseTypedQuery = Mock(TypedQuery)

	}

	def "returns empty list if no entities were found"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList()

		when:
		List<Page> pageList = inPageAwareRepositoryPageFinder.findByPagePageIdIn(pageIds, Performer)

		then:
		1 * jpaContextMock.getEntityManagerByManagedType(Performer) >> entityManager
		1 * baseCriteriaQuery.from(Performer) >> baseRoot
		1 * baseRoot.get("page") >> path
		1 * path.get("pageId") >> path
		1 * path.in(pageIds)
		1 * entityManager.createQuery(baseCriteriaQuery) >> baseTypedQuery
		1 * baseTypedQuery.getResultList() >> pageAwareList
		pageList.empty
	}

	def "finds entities by page ids"() {
		given:
		List<PageAware> pageAwareList = Lists.newArrayList(
				Mock(PageAware) {
					getPage() >> Mock(Page) {
						getPageId() >> PAGE_ID_1
					}
				},
				Mock(PageAware) {
					getPage() >> Mock(Page) {
						getPageId() >> PAGE_ID_2
					}
				}
		)

		when:
		List<Page> pageList = inPageAwareRepositoryPageFinder.findByPagePageIdIn(pageIds, Performer)

		then:
		1 * jpaContextMock.getEntityManagerByManagedType(Performer) >> entityManager
		1 * baseCriteriaQuery.from(Performer) >> baseRoot
		1 * baseRoot.get("page") >> path
		1 * path.get("pageId") >> path
		1 * path.in(pageIds)
		1 * entityManager.createQuery(baseCriteriaQuery) >> baseTypedQuery
		1 * baseTypedQuery.getResultList() >> pageAwareList
		pageList.size() == 2
		pageList[0].pageId == PAGE_ID_1
		pageList[1].pageId == PAGE_ID_2
	}

}
