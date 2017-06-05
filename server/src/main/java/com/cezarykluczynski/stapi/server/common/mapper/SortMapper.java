package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSortDirection;
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort;
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause;
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum;
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort;
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSortClause;
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSortDirectionEnum;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO;
import com.cezarykluczynski.stapi.server.common.serializer.RestSortDeserializer;
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
		if (sort != null) {
			responseSort.getClauses().addAll(mapClauses(sort.getClauses()));
		} else {
			responseSort.getClauses();
		}
		return responseSort;
	}

	public com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort map(String sort) {
		List<RequestSortClauseDTO> requestSortClauseDTOList = RestSortDeserializer.deserialize(sort);
		List<com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSortClause> responseSortClauseList = requestSortClauseDTOList.stream()
				.map(this::mapClause)
				.collect(Collectors.toList());
		com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort responseSort
				= new com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort();
		responseSort.getClauses().addAll(responseSortClauseList);
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

	private com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSortClause mapClause(RequestSortClauseDTO requestSortClauseDTO) {
		com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSortClause responseSortClause
				= new com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSortClause();
		responseSortClause.setName(requestSortClauseDTO.getName());
		responseSortClause.setDirection(ResponseSortDirection.valueOf(requestSortClauseDTO.getDirection().name()));
		responseSortClause.setClauseOrder(requestSortClauseDTO.getClauseOrder());
		return responseSortClause;
	}

	private ResponseSortDirectionEnum mapDirection(RequestSortDirectionEnum requestSortDirectionEnum) {
		return requestSortDirectionEnum == null ? ResponseSortDirectionEnum.ASC : ResponseSortDirectionEnum.valueOf(requestSortDirectionEnum.name());
	}

	private Integer mapClauseOrder(Integer clauseOrder) {
		return ObjectUtils.defaultIfNull(clauseOrder, 0);
	}

}
