package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.rest.model.RequestSort;

import java.util.stream.Collectors;

public class StapiRestSortSerializer {

	public static String serialize(RequestSort requestSort) {
		if (requestSort == null) {
			return "";
		}

		return String.join(";", requestSort.getClauses().stream().map(requestSortClause ->
						requestSortClause.getName() + "," + requestSortClause.getDirection().name())
				.collect(Collectors.toList()));
	}

}
