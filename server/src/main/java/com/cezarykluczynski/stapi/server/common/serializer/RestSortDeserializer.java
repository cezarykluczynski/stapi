package com.cezarykluczynski.stapi.server.common.serializer;

import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO;
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RestSortDeserializer {

	private static final String EXCEPTION_MESSAGE = "Sort clause should be in form of \"fieldName,ORDER\", where ORDER is \"ASC\" or \"DESC\", and "
			+ "fieldName is name of field in main entity that is queried.";
	private static final List<String> VALID_ORDERS = Lists.newArrayList(RestSortDirection.values())
			.stream()
			.map(RestSortDirection::name)
			.collect(Collectors.toList());

	public static List<RequestSortClauseDTO> deserialize(String sort) {
		List<RequestSortClauseDTO> requestSortClauseDTOList = Lists.newArrayList();

		if (sort == null || StringUtils.EMPTY.equals(sort)) {
			return requestSortClauseDTOList;
		}

		List<String> stringClauseList = Lists.newArrayList(sort.split(";"));

		int clauseOrder = 0;

		for (String stringClause : stringClauseList) {
			List<String> clausePartList = Lists.newArrayList(stringClause.split(","));
			if (clausePartList.size() != 2) {
				throw new RuntimeException(EXCEPTION_MESSAGE);
			}

			String fieldName = clausePartList.get(0);
			String orderName = clausePartList.get(1);

			if (fieldName.length() == 0 || !VALID_ORDERS.contains(orderName)) {
				throw new RuntimeException(EXCEPTION_MESSAGE);
			}

			RequestSortClauseDTO requestSortClauseDTO = new RequestSortClauseDTO();
			requestSortClauseDTO.setName(fieldName);
			requestSortClauseDTO.setDirection(RequestSortDirectionDTO.valueOf(orderName));
			requestSortClauseDTO.setClauseOrder(clauseOrder);
			requestSortClauseDTOList.add(requestSortClauseDTO);
			clauseOrder++;
		}

		return requestSortClauseDTOList;
	}

}
