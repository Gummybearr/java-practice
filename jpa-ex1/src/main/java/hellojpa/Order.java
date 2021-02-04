//package hellojpa;
//
//import lombok.Data;
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@Table(name="ORDERS")
//public class Order {
//
//    @Id @GeneratedValue
//    @Column(name = "ORDER_ID")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;
//
//    @OneToMany(mappedBy = "order")
//    private List<OrderItem> orderItemList = new ArrayList<>();
//
//    private LocalDateTime orderDate;
//
////    @Enumerated(EnumType.STRING)
////    private OrderStatus statue;
//
//}
