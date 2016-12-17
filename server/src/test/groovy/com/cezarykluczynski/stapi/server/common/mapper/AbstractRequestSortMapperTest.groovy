package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO
import spock.lang.Specification

abstract class AbstractRequestSortMapperTest extends Specification {

	protected static final String NAME_1 = 'NAME_1'
	protected static final RequestSortDirectionEnum SOAP_SORT_DIRECTION_1 = RequestSortDirectionEnum.ASC
	protected static final RequestSortDirectionDTO SORT_DIRECTION_1 = RequestSortDirectionDTO.ASC
	protected static final Integer CLAUSE_ORDER_1 = 1
	protected static final String NAME_2 = 'NAME_2'
	protected static final RequestSortDirectionEnum SOAP_SORT_DIRECTION_2 = RequestSortDirectionEnum.DESC
	protected static final RequestSortDirectionDTO SORT_DIRECTION_2 = RequestSortDirectionDTO.DESC
	protected static final Integer CLAUSE_ORDER_2 = 2

}
