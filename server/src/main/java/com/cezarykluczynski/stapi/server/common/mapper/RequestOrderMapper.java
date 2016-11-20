package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.RequestOrder;
import com.cezarykluczynski.stapi.client.v1.soap.RequestOrderClause;
import com.cezarykluczynski.stapi.client.v1.soap.RequestOrderEnum;
import com.cezarykluczynski.stapi.model.common.dto.RequestOrderClauseDTO;
import com.cezarykluczynski.stapi.model.common.dto.RequestOrderDTO;
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestOrderEnumDTO;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface RequestOrderMapper {

	RequestOrderDTO mapRequestOrder(RequestOrder requestOrder);

	RequestOrderClauseDTO mapRequestOrderClause(RequestOrderClause requestOrderClause);

	RequestOrderEnumDTO mapRequestOrderEnum(RequestOrderEnum requestOrderEnum);

}
