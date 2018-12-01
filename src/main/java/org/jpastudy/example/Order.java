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

	@ManyToOne
	private Member member;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> itemList = new ArrayList<>();

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
