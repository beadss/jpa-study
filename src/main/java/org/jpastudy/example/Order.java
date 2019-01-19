package org.jpastudy.example;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by naver on 2018. 11. 20..
 */
@Getter
@Setter
@Entity
@Table(name="MEMBER_ORDER")
public class Order {
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> itemList = new ArrayList<>();

	private Date orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	public void setMember(Member member) {
		if(this.member != null) {
			this.member.getOrderList().remove(this);
		}

		member.getOrderList().add(this);
		this.member = member;
	}

}
