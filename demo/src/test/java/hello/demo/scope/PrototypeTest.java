package hello.demo.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototype bean 1");
        PrototypeBean singletonBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototype bean 2");
        PrototypeBean singletonBean2 = ac.getBean(PrototypeBean.class);

        assertThat(singletonBean1).isNotSameAs(singletonBean2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void close(){
            System.out.println("SingletonBean.close");
        }
    }
}
