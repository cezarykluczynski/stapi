package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort;
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause;
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum;
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort;
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSortClause;
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSortDirectionEnum;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SortMapper {

	public ResponseSort map(RequestSort sort) {
		ResponseSort responseSort = new ResponseSort();
		responseSort.getClauses().addAll(mapClauses(sort.getClauses()));
		return responseSort;
	}

	private List<ResponseSortClause> mapClauses(List<RequestSortClause> requestSortClauseList) {
		return Optional.ofNullable(requestSortClauseList).orElse(Lists.newArrayList()).stream()
				.map(this::mapClause)
				.sorted(Comparator.comparing(ResponseSortClause::getClauseOrder))
				.collect(Collectors.toList());
	}

	private ResponseSortClause mapClause(RequestSortClause requestSortClause) {
		ResponseSortClause responseSortClause = new ResponseSortClause();
		responseSortClause.setName(requestSortClause.getName());
		responseSortClause.setDirection(mapDirection(requestSortClause.getDirection()));
		responseSortClause.setClauseOrder(mapClauseOrder(requestSortClause.getClauseOrder()));
		return responseSortClause;
	}

	private ResponseSortDirectionEnum mapDirection(RequestSortDirectionEnum requestSortDirectionEnum) {
		return requestSortDirectionEnum == null ? ResponseSortDirectionEnum.ASC : ResponseSortDirectionEnum.valueOf(requestSortDirectionEnum.name());
	}

	private Integer mapClauseOrder(Integer clauseOrder) {
		return ObjectUtils.defaultIfNull(clauseOrder, 0);
	}

}
