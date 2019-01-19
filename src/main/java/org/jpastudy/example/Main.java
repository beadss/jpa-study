package org.jpastudy.example;

import org.jpastudy.AutoScanProvider;
import org.jpastudy.example.item.Item;
import org.jpastudy.example.item.subtype.Album;
import org.jpastudy.example.item.subtype.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.function.Function;

/**
 * Created by naver on 2018. 11. 10..
 */
public class Main {
	private static EntityManagerFactory emf;

	public static void main(String args[]) {
		emf = new AutoScanProvider().createEntityManagerFactory("jpastudy");

		run(Main::add);
		run(Main::find);

		emf.close();

	}

	private static Member find(EntityManager em) {
		Album orderItem = em.find(Album.class, 3);

		return em.find(Member.class, 1);
	}

	private static Member add(EntityManager em) {
		Member member = Member.builder()
				.id(1)
				.build();

		Order order = new Order();

		OrderItem orderItem = new OrderItem();

		Delivery delivery = Delivery.builder()
			.city("seoul")
			.street("sinsuro")
			.zipCode("100")
			.build();

		order.setMember(member);
		order.setDelivery(delivery);
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(new Date());

		Item album1 = new Album();
		Item album2 = new Album();
		Item book = new Book();

		album1.setName("album1");
		album1.setPrice(1);
		album1.setStockQuantity(1);

		album2.setName("album2");
		album2.setPrice(1);
		album2.setStockQuantity(1);

		book.setName("book");
		book.setPrice(1);
		book.setStockQuantity(1);

		Category parentCategory = new Category();
		parentCategory.setName("parentCategory");
		Category childCategory = new Category();
		childCategory.setName("childCategory");

		parentCategory.addChild(childCategory);

		childCategory.addItem(album1);
		childCategory.addItem(album2);
		childCategory.addItem(book);

		orderItem.setCount(1);
		orderItem.setItem(album1);
		orderItem.setOrder(order);
		orderItem.setOrderPrice(1);

		em.persist(childCategory);
		em.persist(parentCategory);

		em.persist(member);

		return member;
	}

	private static void run(Function<EntityManager, Member> runner) {
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			runner.apply(em);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
