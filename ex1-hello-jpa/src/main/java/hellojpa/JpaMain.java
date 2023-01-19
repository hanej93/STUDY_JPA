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
            em.flush();
            em.clear();

            String sql = "select t From Team t join t.members";
//            String sql = "select m From Member m";
            List<Team> resultList = em.createQuery(sql, Team.class).getResultList();

//            System.out.println("resultList = " + resultList);
            
            for (Team t : resultList) {
//                System.out.println("m = " + m.getUsername() + " ||| m.getTeam().getName() = " + m.getTeam().getName());
                System.out.println("teamName = " + t.getName() + " ||| members = " + t.getMembers().size());
                for( Member m : t.getMembers()) {
                    System.out.println("-> m = " + m);
                }

                // Case 1 member만 단독 조회시 (FetchType.LAZY)
                // 회원
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차캐시)
                // 회원3, 팀B(SQL)
                // N+1번 조회!!
                
                // join fetch -> 멤버와 팀을 조인해서 모든(멤버, 팀) 결과를 가져옴 한번 수행
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
