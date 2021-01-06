package hello.demo.discount;

import hello.demo.member.Grade;
import hello.demo.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_o(){
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        //when
        double discount = discountPolicy.discount(member, 10000);

        //then
        Assertions.assertThat(discount).isEqualTo((double)10000*0.1);
    }

    @Test
    @DisplayName("VIP가 아니면 10% 할인이 적용되어서는 안된다")
    void not_vip_o(){
        //given
        Member member = new Member(1L, "memberVIP", Grade.BASIC  );

        //when
        double discount = discountPolicy.discount(member, 10000);

        //then
        Assertions.assertThat(discount).isEqualTo((double)10000*0.1);
    }

}