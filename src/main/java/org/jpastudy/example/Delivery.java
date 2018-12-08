package org.jpastudy.example;

import lombok.*;

import javax.persistence.*;

/**
 * Created by naver on 2018. 12. 8..
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name="DELIVERY")
public class Delivery {
	@Id
	@GeneratedValue
	private int id;

	@Column
	private String city;
	@Column
	private String street;
	@Column
	private String zipCode;
	@Column
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
}
