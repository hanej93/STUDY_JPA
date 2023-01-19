package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
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
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            Team team = new Team();
            team.setName("testTeam");
            team.addMember(member);
            team.addMember(member2);

            em.persist(team);

            // flush -> commit, query
            em.flush();
            em.clear();

            String sql = "select m.username From Team t join t.members m";
            List<String> resultList = em.createQuery(sql, String.class).getResultList();

            System.out.println("resultList = " + resultList);
            
//            for (Object m : resultList) {
//                System.out.println("m = " + m);
//            }

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
