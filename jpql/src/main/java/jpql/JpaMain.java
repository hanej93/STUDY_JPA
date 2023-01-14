package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("member1");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member" + 1);
            member.setAge(20);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String sql = "select m" +
                    "   from Member m " +
                    "   left join Team t on m.username = t.name";
            List<Member> resultList = em.createQuery(sql, Member.class)
                    .getResultList();

            for (Member m : resultList) {
                System.out.println("m = " + m);
            }

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
