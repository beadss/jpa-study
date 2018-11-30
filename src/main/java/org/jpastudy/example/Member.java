package org.jpastudy.example;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by naver on 2018. 11. 10..
 */
@Entity
@Table(name="MEMBER")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
	@Id
	private int id;
	@Column
	private String name;
	@Column
	private String city;
	@Column
	private String street;
	@Column
	private String zipCode;

	@OneToMany(mappedBy = "member")
	private List<Order> orderList;
}
