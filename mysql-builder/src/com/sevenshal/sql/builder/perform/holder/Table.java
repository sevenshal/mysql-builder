package com.sevenshal.sql.builder.perform.holder;

import com.sevenshal.sql.holder.SQLHolder;
import com.sevenshal.sql.holder.SQLHolderWriter;

public class Table implements SQLHolder {

	private String schema;
	private String name;
	private String alias;
	
	@Override
	public void writeTo(SQLHolderWriter writer) {
		if(schema!=null)
		{
			writer.writeString(schema);
			writer.writeDot();
		}
		writer.writeString(name);
		if(alias!=null)
		{
			writer.writeAs();
			writer.writeString(alias);
		}
	}

}
