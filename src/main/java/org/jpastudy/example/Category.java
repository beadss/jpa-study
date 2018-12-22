package org.jpastudy.example;

import lombok.Getter;
import lombok.Setter;
import org.jpastudy.example.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naver on 2018. 12. 8..
 */
@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> childList = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "category_item",
		joinColumns = @JoinColumn(name = "category_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<Item> itemList = new ArrayList<>();

	@Column
	private String name;

	public void addChild(Category child) {
		if(child.getParent() != null) {
			child.getParent().getChildList().remove(child);
		}

		childList.add(child);
	}

	public void addItem(Item item) {
		//TODO: 기존에 동일한 아이템이 있었는지는 확인 안해도 되나?
		itemList.add(item);
		item.getCategoryList().add(this);
	}

}
