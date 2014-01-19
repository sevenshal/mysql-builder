package com.sevenshal.sql.builder;

import java.util.LinkedList;

import com.sevenshal.sql.criteria.Criteria;
import com.sevenshal.sql.holder.SQLHolder;
import com.sevenshal.sql.holder.SQLHolderWriter;

public class CriteriaBuilder implements SQLHolder{
	
	public enum ClauseType
	{
		WHERE,HAVING,ON
	}
	
	LinkedList<Criteria> oredCriterias = new LinkedList<Criteria>();
	
	ClauseType clauseType;
	
	public CriteriaBuilder() {
		this.clauseType = ClauseType.WHERE;
	}
	
	public CriteriaBuilder(ClauseType clauseType)
	{
		this.clauseType = clauseType;
	}
	
	public Criteria createCriteria() {
		if (oredCriterias.size()==0) {
			return or();
		}
		return oredCriterias.getLast();
	}
	
	public Criteria or()
	{
		Criteria criteria = new Criteria();
		oredCriterias.add(criteria);
		return criteria;
	}

	@Override
	public void writeTo(SQLHolderWriter writer) {
		if(oredCriterias.size()==0)
		{
			return;
		}
		writer.writeBlank();
		writer.writeString(clauseType.name());
		writer.writeBlank();
		if(oredCriterias.size()==1)
		{
			writer.writeObject(oredCriterias.getFirst());
		}else {
			writer.getStringBuffer().append("(");
			writer.writeValues(") OR (", oredCriterias);
			writer.getStringBuffer().append(")");
		}
	}
	
	
	public ClauseType getClauseType() {
		return clauseType;
	}
	
	public void setClauseType(ClauseType clauseType) {
		this.clauseType = clauseType;
	}

}
