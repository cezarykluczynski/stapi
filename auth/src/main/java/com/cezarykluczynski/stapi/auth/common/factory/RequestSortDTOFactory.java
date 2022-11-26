package com.cezarykluczynski.stapi.auth.common.factory;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(SpringProfile.AUTH)
public class RequestSortDTOFactory {

	public RequestSortDTO create() {
		RequestSortDTO requestSortDTO = new RequestSortDTO();
		RequestSortClauseDTO requestSortClauseDTO = new RequestSortClauseDTO();
		requestSortClauseDTO.setDirection(RequestSortDirectionDTO.ASC);
		requestSortClauseDTO.setName("id");
		requestSortDTO.setClauses(Lists.newArrayList(requestSortClauseDTO));
		return requestSortDTO;
	}

}
