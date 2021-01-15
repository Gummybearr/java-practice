package hello.demo.discount;

import hello.demo.member.Grade;
import hello.demo.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy{

    private int discountPercent = 10;

    @Override
    public double discount(Member member, int price) {
        return member.getGrade()==Grade.VIP?(double)price*0.1:0;
    }
}
