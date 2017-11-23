package com.cezarykluczynski.stapi.auth.account.dto;

import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

	private String name;

	private String email;

	private Set<String> permissions = Sets.newHashSet();

}
