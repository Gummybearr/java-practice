package hello.demo.discount;

import hello.demo.member.Grade;
import hello.demo.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000;

    @Override
    public double discount(Member member, int price) {
        return member.getGrade()== Grade.VIP?discountFixAmount:0;
    }
}
