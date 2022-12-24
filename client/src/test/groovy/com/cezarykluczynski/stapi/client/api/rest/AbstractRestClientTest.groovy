package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.google.common.collect.Lists

abstract class AbstractRestClientTest {

	protected static final String SORT_SERIALIZED = 'title,ASC;numberOfPages,DESC;abbreviation,ASC'
	protected static final List<RestSortClause> SORT = Lists.newArrayList(
			new RestSortClause(name: 'title', direction: RestSortDirection.ASC),
			new RestSortClause(name: 'numberOfPages', direction: RestSortDirection.DESC),
			new RestSortClause(name: 'abbreviation')
	)

}
