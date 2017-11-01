package com.cezarykluczynski.stapi.server.security.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class PermissionEvaluatorImpl implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		String permissionName = (String) permission;
		return authentication.getAuthorities()
				.stream()
				.anyMatch(authority -> StringUtils.equals(permissionName, authority.getAuthority()));
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		return false;
	}

}
