package jpql;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String sql = "select m.username, m.type, 'HELLO', TRUE " +
                    "   from Member m " +
                    "   where m.type = :userType";
            List<Object[]> resultList = em.createQuery(sql)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : resultList) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[0] = " + objects[1]);
                System.out.println("objects[0] = " + objects[2]);
                System.out.println("objects[0] = " + objects[3]);
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
