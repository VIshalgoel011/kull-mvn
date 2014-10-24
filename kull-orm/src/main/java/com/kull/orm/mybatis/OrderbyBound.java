package com.kull.orm.mybatis;

public class OrderbyBound {

	public static enum Order{
		desc,asc
	}
	
	public static enum F{
		sort,order,column
	}
	
	public OrderbyBound(String sort,Order order){
		this.sort=sort;
		this.order=order;
	}
	
	public OrderbyBound(String name,Order type,String column){
		this.sort=sort;
		this.order=order;
		this.column=column;
	}
	
	public static OrderbyBound Desc(String name){
		return new OrderbyBound(name,Order.desc);
	}
	
	public static OrderbyBound Asc(String name){
		return new OrderbyBound(name,Order.desc);
	}
	
	private Order order;
	private String sort;
	private String column;
	public Order getOrder() {
		return order;
	}

	public String getSort() {
		return sort;
	}

	public String getColumn() {
		return column;
	}

	public void setColum(String column) {
		this.column = column;
	}
	
    

	


	
	
	
	
	
	
}
