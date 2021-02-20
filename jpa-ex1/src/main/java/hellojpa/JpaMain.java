package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            member.setUserName("hello");

            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println(findMember.getClass());
//            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember.id = "+findMember.getId());
            System.out.println("findMember = "+findMember.getUserName());

            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally{
            em.close();
        }
        System.out.println("end");

        emf.close();
    }
}
