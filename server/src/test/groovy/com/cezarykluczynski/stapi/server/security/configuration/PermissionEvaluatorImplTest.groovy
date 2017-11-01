package com.cezarykluczynski.stapi.server.security.configuration

import com.google.common.collect.Lists
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import spock.lang.Specification

class PermissionEvaluatorImplTest extends Specification {

	private static final String ROLE = 'ROLE'
	private static final String OTHER_ROLE = 'OTHER_ROLE'

	private PermissionEvaluatorImpl permissionEvaluatorImpl

	void setup() {
		permissionEvaluatorImpl = new PermissionEvaluatorImpl()
	}

	void "when target domain object is given, and permission is found, true is returned"() {
		given:
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ROLE)
		Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, Lists.newArrayList(grantedAuthority))
		Object targetDomainObject = Mock()
		Object permission = ROLE

		when:
		boolean result = permissionEvaluatorImpl.hasPermission(authentication, targetDomainObject, permission)

		then:
		result
	}

	void "when target domain object is given, and permission is not found, false is returned"() {
		given:
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(OTHER_ROLE)
		Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, Lists.newArrayList(grantedAuthority))
		Object targetDomainObject = Mock()
		Object permission = ROLE

		when:
		boolean result = permissionEvaluatorImpl.hasPermission(authentication, targetDomainObject, permission)

		then:
		!result
	}

	void "returns false when target domain object is not present"() {
		given:
		Authentication authentication = Mock()
		Serializable targetId = Mock()
		String targetType = ''
		Object permission = Mock()

		when:
		boolean result = permissionEvaluatorImpl.hasPermission(authentication, targetId, targetType, permission)

		then:
		!result
	}

}
