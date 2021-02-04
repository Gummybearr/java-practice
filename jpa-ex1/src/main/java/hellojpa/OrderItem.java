//package hellojpa;
//
//import javax.persistence.*;
//
//@Entity
//public class OrderItem {
//    @Id @GeneratedValue
//    @Column(name = "ORDER_ITEM_ID")
//    private Long id;
//
//    @Column(name = "ORDER_ID")
//    private Long orderId;
//
//    @ManyToOne
//    @JoinColumn(name = "ORDER_ID")
//    private Order order;
//
////    @ManyToOne
////    @JoinColumn(name="ITEM_ID")
////    private Item item;
//
//}
