package org.jpastudy.example.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jpastudy.example.Category;
import org.jpastudy.example.OrderItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naver on 2018. 11. 20..
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="ITEM")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Item {
	@Id
	@GeneratedValue
	private int id;

	private String name;
	private long price;
	private long stockQuantity;

	@OneToMany(mappedBy = "item")
	private List<OrderItem> orderItemList = new ArrayList<>();

	@ManyToMany(mappedBy = "itemList")
	private List<Category> categoryList = new ArrayList<>();
}
