package com.sevenshal.sql.criteria;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.sevenshal.sql.exception.OperatorBuilderException;
import com.sevenshal.sql.holder.SQLHolder;
import com.sevenshal.sql.holder.SQLHolderWriter;
import com.sevenshal.sql.operator.Operator;

public class Criteria implements SQLHolder {

	List<Criterion> criteriones = new LinkedList<Criterion>();

	public List<Criterion> getAllCriteria() {
		return criteriones;
	}

	public Criteria addCriterion(String property, Operator operator,
			Object... values) {
		return this.addCriterion(property, operator, Arrays.asList(values));
	}

	public Criteria addCriterion(String property, Operator operator,
			List<Object> values) {
		if(!operator.getParamNum().allow(values.size()))
		{
			throw new OperatorBuilderException(operator, values.size());
		}
		criteriones.add(new Criterion(property, operator, values));
		return this;
	}
	
	public Criteria equalTo(String property,Object value)
	{
		return addCriterion(property, Operator.equal, value);
	}
	
	public Criteria notEqualTo(String property,Object value)
	{
		return addCriterion(property, Operator.notEqual, value);
	}
	
	public Criteria greaterThan(String property,Object value)
	{
		return addCriterion(property, Operator.greaterThan, value);
	}
	
	public Criteria greaterOrEqual(String property,Object value)
	{
		return addCriterion(property, Operator.greaterOrEqual, value);
	}
	
	public Criteria lessThan(String property,Object value)
	{
		return addCriterion(property, Operator.lessThan, value);
	}
	
	public Criteria lessOrEqual(String property,Object value)
	{
		return addCriterion(property, Operator.lessOrEqual, value);
	}
	
	public Criteria isNull(String property)
	{
		return addCriterion(property, Operator.isNull);
	}
	
	public Criteria isNotNull(String property)
	{
		return addCriterion(property, Operator.isNotNull);
	}
	
	public Criteria in(String property,Object...values)
	{
		return addCriterion(property, Operator.in, values);
	}
	
	public Criteria notIn(String property,Object... values)
	{
		return addCriterion(property, Operator.notIn,values);
	}
	
	public Criteria between(String property,Object value1,Object value2)
	{
		return addCriterion(property, Operator.between, value1,value2);
	}
	
	public Criteria notBetween(String property,Object value1,Object value2)
	{
		return addCriterion(property, Operator.notBetween,value1,value2);
	}
	
	public Criteria like(String property,Object value)
	{
		return addCriterion(property, Operator.like,value);
	}
	
	public Criteria contain(String property,Object value)
	{
		return addCriterion(property, Operator.like,"%"+value+"%");
	}
	
	public Criteria beginWith(String property,Object value)
	{
		return addCriterion(property, Operator.like,value+"%");
	}
	
	public Criteria entWith(String property,Object value)
	{
		return addCriterion(property, Operator.like,"%"+value);
	}
	
	public Criteria notLike(String property,Object value)
	{
		return addCriterion(property, Operator.notLike,value);
	}

	@Override
	public void writeTo(SQLHolderWriter writer) {
		writer.writeValues(" AND ", criteriones);
	}

	public class Criterion implements SQLHolder {

		Operator operator;
		String property;
		List<Object> values;

		protected Criterion(String property, Operator operator,
				List<Object> values) {
			this.property = property;
			this.operator = operator;
			this.values = values;
		}

		public Operator getOperator() {
			return operator;
		}

		public String getProperty() {
			return property;
		}

		public Object getValues() {
			return values;
		}

		@Override
		public void writeTo(SQLHolderWriter writer) {
			writer.writeProperty(this.property);
			writer.writeBlank();
			this.operator.multiParamWriteTo(writer, values);
		}
	}

}
