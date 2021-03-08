package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        System.out.println("begin");
        try{
            Member member = new Member();
            member.setUserName("member1");
            member.setHomeAddress(new Address("a", "b", "c"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("보쌈");

            member.getAddressHistory().add(new Address("o1", "o1", "o1"));
            member.getAddressHistory().add(new Address("o2", "o2", "o2"));
            member.getAddressHistory().add(new Address("o3", "o3", "o3"));

            em.persist(member);

            em.flush();
            em.clear();

            List<MemberDto> resultList = em.createQuery("select new hellojpa.MemberDto(m.userName, m.id) from Member m", MemberDto.class).getResultList();
            System.out.println(resultList);

            tx.commit();
        } catch(Exception e){
            e.printStackTrace();
            tx.rollback();
        } finally{
            em.close();
        }
        System.out.println("end");

        emf.close();
    }
}
