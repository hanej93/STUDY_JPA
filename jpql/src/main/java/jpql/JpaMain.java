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

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();


            String sql = "select m " +
                    "   from Member m " +
                    "   order by m.age desc";
            List<Member> resultList = em.createQuery(sql, Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();

            System.out.println("resultList.size() = " + resultList.size());
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
