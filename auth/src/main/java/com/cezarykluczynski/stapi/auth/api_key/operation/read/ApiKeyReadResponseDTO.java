package com.cezarykluczynski.stapi.auth.api_key.operation.read;

import com.cezarykluczynski.stapi.auth.api_key.dto.ApiKeyDTO;
import com.cezarykluczynski.stapi.util.wrapper.Pager;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ApiKeyReadResponseDTO {

	private boolean read;

	private FailReason failReason;

	private List<ApiKeyDTO> apiKeys;

	private Pager pager;

	public enum FailReason {

		TOO_MUCH_ENTRIES_FOR_A_SINGLE_PAGE

	}

}
