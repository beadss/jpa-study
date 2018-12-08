package org.jpastudy.example;

import org.jpastudy.AutoScanProvider;

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
		OrderItem orderItem = em.find(OrderItem.class, 1);
		Item item = em.find(Item.class, 1);
		return em.find(Member.class, 1);
	}

	private static Member add(EntityManager em) {
		Member member = Member.builder()
				.id(1)
				.build();
		em.persist(member);

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

		Item item = new Item();

		item.setName("item");
		item.setPrice(1);
		item.setStockQuantity(1);

		Category parentCategory = new Category();
		parentCategory.setName("parentCategory");
		Category childCategory = new Category();
		childCategory.setName("childCategory");

		parentCategory.addChild(childCategory);

		childCategory.addItem(item);

		em.persist(childCategory);
		em.persist(parentCategory);

		em.persist(item);
		em.persist(delivery);
		em.persist(order);

		orderItem.setCount(1);
		orderItem.setItem(item);
		orderItem.setOrder(order);
		orderItem.setOrderPrice(1);

		em.persist(orderItem);

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
