package com.example.shoesmanagement.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ROLE_ADMIN, ROLE_GUEST;

	public String getAuthority() {
		return name();
	}

}
