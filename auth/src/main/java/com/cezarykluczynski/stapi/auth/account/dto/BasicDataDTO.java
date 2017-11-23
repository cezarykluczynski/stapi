package com.cezarykluczynski.stapi.auth.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasicDataDTO {

	private String name;

	private String email;

}
