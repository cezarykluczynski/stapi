package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.rest.model.ResponseSortDirection;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO;
import com.cezarykluczynski.stapi.server.common.serializer.RestSortDeserializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SortMapper {

	public com.cezarykluczynski.stapi.client.rest.model.ResponseSort map(String sort) {
		List<RequestSortClauseDTO> requestSortClauseDTOList = RestSortDeserializer.deserialize(sort);
		List<com.cezarykluczynski.stapi.client.rest.model.ResponseSortClause> responseSortClauseList = requestSortClauseDTOList.stream()
				.map(this::mapClause)
				.collect(Collectors.toList());
		com.cezarykluczynski.stapi.client.rest.model.ResponseSort responseSort
				= new com.cezarykluczynski.stapi.client.rest.model.ResponseSort();
		responseSort.getClauses().addAll(responseSortClauseList);
		return responseSort;
	}

	private com.cezarykluczynski.stapi.client.rest.model.ResponseSortClause mapClause(RequestSortClauseDTO requestSortClauseDTO) {
		com.cezarykluczynski.stapi.client.rest.model.ResponseSortClause responseSortClause
				= new com.cezarykluczynski.stapi.client.rest.model.ResponseSortClause();
		responseSortClause.setName(requestSortClauseDTO.getName());
		responseSortClause.setDirection(ResponseSortDirection.valueOf(requestSortClauseDTO.getDirection().name()));
		responseSortClause.setClauseOrder(requestSortClauseDTO.getClauseOrder());
		return responseSortClause;
	}

}
