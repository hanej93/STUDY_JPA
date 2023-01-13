package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homecity", "street", "10000"));

            member.getFavoriteFood().add("치킨");
            member.getFavoriteFood().add("족발");
            member.getFavoriteFood().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street1", "10001"));
            member.getAddressHistory().add(new AddressEntity("old2", "street2", "10002"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("!! START ================");
            Member findMember = em.find(Member.class, member.getId());

            // homeCity -> newCity
//            Address findAddress = findMember.getHomeAddress();
//            Address newAddress = new Address("newCity", findAddress.getStreet(), findAddress.getZipcode());
//            findMember.setHomeAddress(newAddress);
//
//            // 치킨 -> 한식
//            findMember.getFavoriteFood().remove("치킨");
//            findMember.getFavoriteFood().add("한식");

//            findMember.getAddressHistory().remove(new AddressEntity("old1", "street1", "10001"));
//            findMember.getAddressHistory().add(new AddressEntity("newold1", "street1", "10001"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
