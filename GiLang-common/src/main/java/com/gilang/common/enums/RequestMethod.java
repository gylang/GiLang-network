package com.gilang.common.enums;

public enum RequestMethod {

	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;


	public boolean match(String method) {

		return this.name().equalsIgnoreCase(method);
	}
}
