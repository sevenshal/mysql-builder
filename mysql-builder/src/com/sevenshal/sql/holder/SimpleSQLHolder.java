package com.sevenshal.sql.holder;

import java.util.List;

public class SimpleSQLHolder implements SQLHolder {
	
	private String sql;
	
	private List<?> params;
	
	public SimpleSQLHolder(String sql) {
		this.sql = sql;
	}
	
	public SimpleSQLHolder(String sql,List<?> params)
	{
		this.params = params;
	}

	@Override
	public void writeTo(SQLHolderWriter writer) {
		writer.writeString(sql);
		if(params!=null)
		{
			writer.getParams().addAll(params);
		}
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String getSql() {
		return sql;
	}
	
	
}
