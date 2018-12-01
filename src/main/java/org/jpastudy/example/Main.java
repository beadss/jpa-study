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
		return em.find(Member.class, 1);
	}

	private static Member add(EntityManager em) {
		Member member = Member.builder()
				.id(1)
				.city("seoul")
				.street("sinsuro")
				.zipCode("100")
				.build();
		em.persist(member);

		Order order = new Order();

		OrderItem orderItem = new OrderItem();

		order.setMember(member);
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(new Date());

		Item item = new Item();

		item.setName("item");
		item.setPrice(1);
		item.setStockQuantity(1);

		orderItem.setCount(1);
		orderItem.setItem(item);
		orderItem.setOrder(order);
		orderItem.setOrderPrice(1);

		em.persist(item);
		em.persist(order);
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
