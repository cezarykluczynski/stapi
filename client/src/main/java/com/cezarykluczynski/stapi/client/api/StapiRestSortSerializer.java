package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.api.dto.RestSortClause;
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortOrder;

import java.util.List;
import java.util.stream.Collectors;

public class StapiRestSortSerializer {

	public static String serialize(List<RestSortClause> restSortClauseList) {
		restSortClauseList.forEach(restSortClause -> {
			if (restSortClause.getSortOrder() == null) {
				restSortClause.setSortOrder(RestSortOrder.ASC);
			}
		});

		return String.join(";", restSortClauseList.stream().map((restSortClause) ->
						restSortClause.getName() + "," + restSortClause.getSortOrder().name())
				.collect(Collectors.toList()));
	}

}
