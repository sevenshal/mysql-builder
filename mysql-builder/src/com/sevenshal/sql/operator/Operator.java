package com.sevenshal.sql.operator;

import java.util.List;

import com.sevenshal.sql.holder.SQLHolderWriter;

/**
 * SQL比较符.支持SQL-92标准中定义的比较运算符。
 * @author qishuai
 *
 */
public enum Operator {
	
	equal("= ",ParamNum.ONE),
	notEqual("<> ",ParamNum.ONE),
	greaterThan("> ",ParamNum.ONE),
	greaterOrEqual(">= ",ParamNum.ONE),
	lessThan("< ",ParamNum.ONE),
	lessOrEqual("<= ",ParamNum.ONE),
	isNull("IS NULL",ParamNum.NONE),
	isNotNull("IS NOT NULL",ParamNum.NONE),
	in("IN ",ParamNum.MULTI),
	notIn("NOT IN ",ParamNum.MULTI),
	between("BETWEEN ",ParamNum.TWO),
	notBetween("NOT BETWEEN ",ParamNum.TWO),
	like("LIKE ",ParamNum.ONE),
	notLike("NOT LIKE ",ParamNum.ONE);
	
	private String simpleSQL;
	
	private ParamNum paramNum;
	
	private Operator(String operString,ParamNum paramNum) {
		this.simpleSQL = operString;
		this.paramNum = paramNum;
	}
	
	public ParamNum getParamNum() {
		return paramNum;
	}
	
	public void multiParamWriteTo(SQLHolderWriter writer,List<Object> params){
		writer.writeString(simpleSQL);
		this.paramNum.writeParams(writer, params);
	};
	
	public enum ParamNum
	{
		NONE,ONE{
			@Override
			public void writeParams(SQLHolderWriter writer,List<?> params) {
				writer.writeParam(params.get(0));
			}
		},TWO{
			@Override
			public void writeParams(SQLHolderWriter writer, List<?> params) {
				writer.writeParams(" AND ", params);
			}
		},MULTI{
			@Override
			public boolean allow(int value) {
				return value>0;
			}
			@Override
			public void writeParams(SQLHolderWriter writer, List<?> params) {
				writer.writeString("(");
				writer.writeParams(",", params);
				writer.writeString(")");
			}
		};
		
		public boolean allow(int value)
		{
			return this.ordinal()==value;
		}
		
		public void writeParams(SQLHolderWriter writer,List<?> params)
		{
			
		}
	}
	
}
