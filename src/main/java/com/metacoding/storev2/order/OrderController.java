package com.metacoding.storev2.order;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/save")
    public String save(@RequestParam("storeId") int storeId,
                       @RequestParam("buyer") String buyer,
                       @RequestParam("qty") int qty) {
        orderService.구매하기(storeId, buyer, qty);

        return "redirect:/order";
    }

    @GetMapping("/order")
    public String list(HttpServletRequest request) {
        List<OrederResponse.ListPage> listPage = orderService.구매목록();
        request.setAttribute("models", listPage);
        return "order/list";
    }

}