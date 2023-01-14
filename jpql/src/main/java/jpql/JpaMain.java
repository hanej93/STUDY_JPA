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

            String sql = "select " +
                    "           case when m.age <= 10 then '학생요금'" +
                    "                when m.age >= 60 then '경로요금'" +
                    "                else '일반요금'" +
                    "           end as amount" +
                    "      from Member m";

            List<String> resultList = em.createQuery(sql, String.class).getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
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
