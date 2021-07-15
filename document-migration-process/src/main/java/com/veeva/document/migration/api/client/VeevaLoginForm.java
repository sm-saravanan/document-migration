package com.veeva.document.migration.api.client;

import org.springframework.beans.factory.annotation.Value;

public class VeevaLoginForm {
    @Value("${veeva.api.user}")
	private String username;
    @Value("${veeva.api.password}")
	private String password;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
