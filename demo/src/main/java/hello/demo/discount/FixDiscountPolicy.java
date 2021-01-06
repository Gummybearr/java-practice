package hello.demo.discount;

import hello.demo.member.Grade;
import hello.demo.member.Member;

public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000;

    @Override
    public double discount(Member member, int price) {
        return member.getGrade()== Grade.VIP?discountFixAmount:0;
    }
}
