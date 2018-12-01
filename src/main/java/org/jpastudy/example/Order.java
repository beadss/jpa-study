package org.jpastudy.example;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by naver on 2018. 11. 20..
 */
@Getter
@Entity
@Table(name="MEMBER_ORDER")
public class Order {
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(optional = false)
	private Member member;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> itemList;

	private Date orderDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public void setMember(Member member) {
		if(this.member != null) {
			this.member.getOrderList().remove(this);
		}

		member.getOrderList().add(this);
		this.member = member;
	}

}
