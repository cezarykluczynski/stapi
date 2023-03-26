package com.cezarykluczynski.stapi.client.rest.facade

import com.cezarykluczynski.stapi.client.rest.model.RequestSort
import com.cezarykluczynski.stapi.client.rest.model.RequestSortClause
import com.cezarykluczynski.stapi.client.rest.model.RequestSortDirection
import com.google.common.collect.Lists

abstract class AbstractFacadeTest {

	protected static final String SORT_SERIALIZED = 'title,ASC;numberOfPages,DESC;abbreviation,ASC'
	protected static final RequestSort SORT = new RequestSort(clauses: Lists.newArrayList(
			new RequestSortClause(name: 'title', direction: RequestSortDirection.ASC),
			new RequestSortClause(name: 'numberOfPages', direction: RequestSortDirection.DESC),
			new RequestSortClause(name: 'abbreviation', direction: RequestSortDirection.ASC
			))
	)

}
