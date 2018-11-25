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

	@ManyToOne(optional=false)
	private Order order;

	@ManyToOne(optional=false)
	private Item item;

	private long orderPrice;

	private long count;
}
