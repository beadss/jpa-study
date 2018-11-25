package org.jpastudy.example;

import org.jpastudy.AutoScanProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
