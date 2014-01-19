package com.sevenshal.sql.builder.perform;

import com.sevenshal.sql.holder.SimpleSQLHolder;

public class SimplePerformBuilder extends SimpleSQLHolder implements PerformBuilder {

	public SimplePerformBuilder(String selectSQL) {
		super(selectSQL);
	}

}
