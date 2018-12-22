package org.jpastudy.example.item.subtype;

import org.jpastudy.example.item.Item;

import javax.persistence.Entity;

/**
 * Created by naver on 2018. 12. 15..
 */
@Entity
public class Movie extends Item {
	private String director;
	private String actor;
}
