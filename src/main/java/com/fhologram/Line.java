package com.fhologram;

import org.bukkit.entity.Item;

public class Line {

	private String Txt;
	private Type Type;
	private Integer Amount;
	private Item item;
	
	public Line(String txt, Type type, int amount) {
		
		this.setTxt(txt);
		this.setType(type);
		this.setAmount(amount);
	}
	
	public Line(Item item, Type type, int amount) {
		
		this.setItem(item);
		this.setType(type);
		this.setAmount(amount);
	}

	
	public String getTxt() {
		return Txt;
	}
	
	public void setTxt(String txt) {
		Txt = txt;
	}
	
	public Type getType() {
		return Type;
	}
	
	public void setType(Type type) {
		Type = type;
	}
	
	public Integer getAmount() {
		return Amount;
	}
	
	public void setAmount(Integer amount) {
		Amount = amount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
