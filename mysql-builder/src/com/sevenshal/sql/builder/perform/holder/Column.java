package com.sevenshal.sql.builder.perform.holder;

import com.sevenshal.sql.holder.SQLHolder;
import com.sevenshal.sql.holder.SQLHolderWriter;

public class Column implements SQLHolder {

	String name;
	String alias;
	
	@Override
	public void writeTo(SQLHolderWriter writer) {
		writer.writeString(name);
		if(alias!=null)
		{
			writer.writeAs();
			writer.writeString(alias);
		}
	}

}
