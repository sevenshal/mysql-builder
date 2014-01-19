package com.sevenshal.sql.holder;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SQLHolderWriter {
	
	static private Set<String> keywords;
	
	static private String[] keywordStrings =
		{"action","add","aggregate","all","alter","after","and","as","asc","avg",
		"avg_row_length","auto_increment","between","bigint","bit","binary","blob",
		"bool","both","by","cascade","case","char","character","change","check",
		"checksum","column","columns","comment","constraint","create","cross",
		"current_date","current_time","current_timestamp","data","database","databases",
		"date","datetime","day","day_hour","day_minute","day_second","dayofmonth",
		"dayofweek","dayofyear","dec","decimal","default","delayed","delay_key_write",
		"delete","desc","describe","distinct","distinctrow","double","drop","end",
		"else","escape","escaped","enclosed","enum","explain","exists","fields",
		"file","first","float","float4","float8","flush","foreign","from","for",
		"full","function","global","grant","grants","group","having","heap",
		"high_priority","hour","hour_minute","hour_second","hosts","identified",
		"ignore","in","index","infile","inner","insert","insert_id","int","integer",
		"interval","int1","int2","int3","int4","int8","into","if","is","isam","join",
		"key","keys","kill","last_insert_id","leading","left","length","like","lines",
		"limit","load","local","lock","logs","long","longblob","longtext","low_priority",
		"max","max_rows","match","mediumblob","mediumtext","mediumint","middleint",
		"min_rows","minute","minute_second","modify","month","monthname","myisam",
		"natural","numeric","no","not","null","on","optimize","option","optionally",
		"or","order","outer","outfile","pack_keys","partial","password","precision",
		"primary","procedure","process","processlist","privileges","read","real",
		"references","reload","regexp","rename","replace","restrict","returns","revoke",
		"rlike","row","rows","second","select","set","show","shutdown","smallint",
		"soname","sql_big_tables","sql_big_selects","sql_low_priority_updates",
		"sql_log_off","sql_log_update","sql_select_limit","sql_small_result",
		"sql_big_result","sql_warnings","straight_join","starting","status","string",
		"table","tables","temporary","terminated","text","then","time","timestamp",
		"tinyblob","tinytext","tinyint","trailing","to","type","use","using","unique",
		"unlock","unsigned","update","usage","values","varchar","variables","varying",
		"varbinary","with","write","when","where","year","year_month","zerofill"};
	
	static{
		keywords = new HashSet<String>(Arrays.asList(keywordStrings));
	}
	
	StringBuffer buffer = new StringBuffer(1024);
	
	List<Object> params = new LinkedList<Object>();
	
	static public interface ForEachWriteCallback {
		void writeObject(Object object);
	}
	
	public void writeValues(String separator,Collection<?> values,ForEachWriteCallback callback)
	{
		boolean writeSeperator = false;
		for (Object value : values) {
			if (writeSeperator) {
				buffer.append(separator);
			}
			else {
				writeSeperator = true;
			}
			callback.writeObject(value);
		}
	}
	
	public void writeValues(String separator,Collection<?> values)
	{
		writeValues(separator, values, new ForEachWriteCallback() {
			@Override
			public void writeObject(Object object) {
				SQLHolderWriter.this.writeObject(object);
			}
		});
	}
	
	public void writeParams(String separator,Collection<?> params)
	{
		writeValues(separator, params, new ForEachWriteCallback() {
			@Override
			public void writeObject(Object object) {
				SQLHolderWriter.this.writeParam(object);
			}
		});
	}
	
	
	public void writeBlank()
	{
		buffer.append(' ');
	}
	
	public void writeSelect()
	{
		buffer.append("SELECT ");
	}
	
	public void writeUpdate()
	{
		buffer.append("UPDATE");
	}
	
	public void writeFrom()
	{
		buffer.append(" FROM ");
	}
	
	public void writeInsertInto()
	{
		buffer.append("INSERT INTO ");
	}
	
	public void writeAs()
	{
		buffer.append(" AS ");
	}
	
	public void writeIfNotNull(String value)
	{
		if(value==null)
		{
			return;
		}
		buffer.append(value);
	}
	
	public void writeString(String value)
	{
		buffer.append(value);
	}
	
	public void writeDot()
	{
		buffer.append('.');
	}
	
	public void writeBackQuote(String string)
	{
		buffer.append("`");
		buffer.append(string);
		buffer.append("`");
	}
	
	public void writeHodler(SQLHolder sqlHolder)
	{
		if(sqlHolder!=null)
		{
			sqlHolder.writeTo(this);
		}
	}
	
	public void writeParam(Object value)
	{
		if(value instanceof SQLHolder)
		{
			writeHodler((SQLHolder)value);
		}
		else {
			writeString("?");
			params.add(value);
		}
	}
	
	public void writeObject(Object value)
	{
		if(value instanceof SQLHolder)
		{
			writeHodler((SQLHolder)value);
		}
		else{
			buffer.append(value);
		}
	}
	
	public void writeProperty(String property)
	{
		if(keywords.contains(property))
		{
			writeBackQuote(property);
		}else {
			buffer.append(property);
		}
	}
	
	public StringBuffer getStringBuffer()
	{
		return this.buffer;
	}
	
	public void repeatWriteValue(String separator,Object value,int length)
	{
		if(length<=0)
		{
			return;
		}
		while (--length>0) {
			writeObject(value);
			buffer.append(separator);
		}
		writeObject(value);
	}
	
//	public boolean appendParams(List<Object> params)
//	{
//		return this.params.addAll(params);
//	}
	
	public List<Object> getParams() {
		return params;
	}
}
