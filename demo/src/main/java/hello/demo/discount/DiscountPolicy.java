package hello.demo.discount;

import hello.demo.member.Member;

public interface DiscountPolicy {

    /*

    @return 할인 대상 금액
    */
    double discount(Member member, int price);
}
