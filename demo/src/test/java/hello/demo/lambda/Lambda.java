package hello.demo.lambda;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

public class Lambda {

    Predicate<String> isEmptyStr = s-> s.length()==0;

    String s = "";

    @Test
    void test(){
        System.out.println(isEmptyStr.test(s));
    }
}
