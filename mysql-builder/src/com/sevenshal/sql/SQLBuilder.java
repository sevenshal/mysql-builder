package com.sevenshal.sql;

import java.util.Arrays;
import java.util.List;

import com.sevenshal.sql.builder.CriteriaBuilder;
import com.sevenshal.sql.builder.OrderByBuilder;
import com.sevenshal.sql.builder.CriteriaBuilder.ClauseType;
import com.sevenshal.sql.builder.OrderByBuilder.Order;
import com.sevenshal.sql.builder.OrderByBuilder.Order.OrderType;
import com.sevenshal.sql.builder.perform.PerformBuilder;
import com.sevenshal.sql.holder.SQLHolder;
import com.sevenshal.sql.holder.SQLHolderWriter;

public class SQLBuilder implements SQLHolder{
	
	private PerformBuilder performBuilder;
	
	private CriteriaBuilder whereClause;
	
	private String[] groupBy;
	
	private OrderByBuilder orderBy;
	
	private CriteriaBuilder havingClause;
	
	private Integer firstRow = 0;
	
	private Integer maxResult = null;
	
	private List<Object> params;

	public SQLBuilder(PerformBuilder performBuilder)
	{
		this.performBuilder = performBuilder;
	}
	
	
	public CriteriaBuilder where()
	{
		if(whereClause==null)
		{
			whereClause = new CriteriaBuilder(ClauseType.WHERE);
		}
		return whereClause;
	}

	public CriteriaBuilder having()
	{
		if(havingClause==null)
		{
			havingClause = new CriteriaBuilder(ClauseType.HAVING);
		}
		return havingClause;
	}
	
	private OrderByBuilder createOrderByBuilderInternal()
	{
		if(orderBy==null)
		{
			orderBy = new OrderByBuilder();
		}
		return orderBy;
	}
	
	public OrderByBuilder orderBy(String property,OrderType orderType)
	{
		return createOrderByBuilderInternal().orderBy(property, orderType);
	}
	
	public OrderByBuilder orderBy(Order order)
	{
		return createOrderByBuilderInternal().orderBy(order);
	}
	
	public OrderByBuilder orderBy(String property)
	{
		return createOrderByBuilderInternal().orderBy(property, OrderType.ASC);
	}
	

	@Override
	public void writeTo(SQLHolderWriter writer) {
		writer.writeHodler(performBuilder);
		writer.writeHodler(whereClause);
		if(groupBy!=null)
		{
			writer.writeString(" GROUP BY ");
			writer.writeValues(",", Arrays.asList(groupBy));
			writer.writeHodler(havingClause);
		}
		writer.writeHodler(orderBy);
		if(maxResult!=null)
		{
			writer.writeString(" LIMIT ");
			if(firstRow!=0)
			{
				writer.writeString(firstRow.toString());
				writer.writeString(",");
			}
			writer.writeString(maxResult.toString());
		}
	}
	
	public String createSQL()
	{
		SQLHolderWriter writer = new SQLHolderWriter();
		writeTo(writer);
		params = writer.getParams();
		return writer.getStringBuffer().toString();
	}
	
	public PerformBuilder getPerformBuilder() {
		return performBuilder;
	}

	public void setPerformBuilder(PerformBuilder performBuilder) {
		this.performBuilder = performBuilder;
	}

	public CriteriaBuilder getWhereClause() {
		return whereClause;
	}

	public String[] getGroupBy() {
		return groupBy;
	}
	
	public void groupBy(String... groupBy)
	{
		this.groupBy = groupBy;
	}

	public CriteriaBuilder getHavingClause() {
		return havingClause;
	}

	public void setHavingClause(CriteriaBuilder havingClause) {
		this.havingClause = havingClause;
	}
	
	public OrderByBuilder getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(OrderByBuilder orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	
	public List<Object> getParams()
	{
		return params;
	}

}
