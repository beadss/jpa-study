package org.jpastudy.example;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by naver on 2018. 11. 20..
 */
@Entity
@Table(name="MEMBER_ORDER")
public class Order {
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	private Member member;

	@OneToMany
	@JoinColumn(name="member_order_id")
	private List<OrderItem> itemList;

	private Date orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

}
