package com.sevenshal.sql.builder;

import java.util.LinkedList;
import java.util.List;

import com.sevenshal.sql.holder.SQLHolder;
import com.sevenshal.sql.holder.SQLHolderWriter;

public class OrderByBuilder implements SQLHolder {
	
	public static class Order implements SQLHolder
	{	
		public enum OrderType {
			ASC,DESC
		}
		
		String property;
		OrderType order;
		public Order(String property,OrderType order) {
			this.property = property;
			this.order = order;
		}
		
		@Override
		public void writeTo(SQLHolderWriter buffer) {
			buffer.writeProperty(property);
			buffer.writeBlank();
			buffer.writeString(order.name());
		}
		
	}
	
	private List<Order> orders = new LinkedList<Order>();
	
	public OrderByBuilder orderBy(String property,Order.OrderType orderType)
	{
		orders.add(new Order(property, orderType));
		return this;
	}
	
	public OrderByBuilder orderBy(Order order)
	{
		orders.add(order);
		return this;
	}
	
	@Override
	public void writeTo(SQLHolderWriter buffer) {
		if(orders.size()==0)
		{
			return;
		}
		buffer.writeString(" ORDER BY ");
		buffer.writeValues(",", orders);
	}

}
