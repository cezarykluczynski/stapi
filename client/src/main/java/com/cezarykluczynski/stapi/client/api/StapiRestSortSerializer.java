package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.api.dto.RestSortClause;
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection;

import java.util.List;
import java.util.stream.Collectors;

public class StapiRestSortSerializer {

	public static String serialize(List<RestSortClause> restSortClauseList) {
		restSortClauseList.forEach(restSortClause -> {
			if (restSortClause.getDirection() == null) {
				restSortClause.setDirection(RestSortDirection.ASC);
			}
		});

		return String.join(";", restSortClauseList.stream().map((restSortClause) ->
						restSortClause.getName() + "," + restSortClause.getDirection().name())
				.collect(Collectors.toList()));
	}

}
