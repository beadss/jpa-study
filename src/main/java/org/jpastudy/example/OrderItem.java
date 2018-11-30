package org.jpastudy.example;

import lombok.Getter;

import javax.persistence.*;

/**
 * Created by naver on 2018. 11. 20..
 */
@Getter
@Entity
@Table(name="ORDER_ITEM")
public class OrderItem {
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name="item_id")
	private Item item;

	private long orderPrice;

	private long count;

	public void setOrder(Order order) {
		if(this.order != null) {
			this.order.getItemList().remove(this);
		}

		order.getItemList().add(this);
		this.order = order;
	}

	public void setItem(Item item) {
		if(this.item != null) {
			this.item.getOrderItemList().remove(this);
		}

		item.getOrderItemList().add(this);
		this.item = item;
	}
}
