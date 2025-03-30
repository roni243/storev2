package com.metacoding.storev2.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository // IoC
public class OrderRepository {
    private EntityManager em;

    // DI
    public OrderRepository(EntityManager em) {
        this.em = em;
    }

    public List<OrderResponse.OrderDTO> findAllOrders() {
        String sql = """
        select ot.id, st.name, ot.qty, ot.total_price
        from order_tb ot inner join store_tb st
        on ot.store_id = st.id
        order by ot.id desc;
        """;
        Query q = em.createNativeQuery(sql);
        List<Object[]> orderList = q.getResultList();
        List<OrderResponse.OrderDTO> orderDTOList = new ArrayList<>();

        for(Object[] order : orderList) {
            OrderResponse.OrderDTO orderDto = new OrderResponse.OrderDTO(
                    (Integer) order[0],
                    (String) order[1],
                    (Integer) order[2],
                    (Integer) order[3]
            );
            orderDTOList.add(orderDto);
        }

        return orderDTOList;
    }

    public void saveOrder(int storeId, int userId, int qty, int totalPrice) {
        Query q = em.createNativeQuery("insert into order_tb(store_id, user_id, qty, total_price) values(?, ?, ?, ?);");
        q.setParameter(1, storeId);
        q.setParameter(2, userId);
        q.setParameter(3, qty);
        q.setParameter(4, totalPrice);
        q.executeUpdate();
    }

    public void updateStock(int storeId, int stock) {
        Query q = em.createNativeQuery("update store_tb set stock = ? where id = ?;");
        q.setParameter(1, stock);
        q.setParameter(2, storeId);
        q.executeUpdate();
    }

}