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
            member.setUsername("회원1");
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("회원2");
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            em.persist(member3);

            Team team = new Team();
            team.setName("팀A");
            team.addMember(member);
            team.addMember(member2);
            em.persist(team);

            Team teamB = new Team();
            teamB.setName("팀B");
            teamB.addMember(member3);
            em.persist(teamB);

            // flush -> commit, query

            String sql = "update Member m set m.age = 20";

            // FLUSH 자동 호출
            int resultCount = em.createQuery(sql)
                    .executeUpdate();

            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            System.out.println("findMember.age = " + findMember.getAge());

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
