package com.presnakov.hotelbooking.http.controller;

import com.presnakov.hotelbooking.dto.OrderCreateEditDto;
import com.presnakov.hotelbooking.dto.OrderFilter;
import com.presnakov.hotelbooking.dto.OrderReadDto;
import com.presnakov.hotelbooking.dto.PageResponse;
import com.presnakov.hotelbooking.service.HotelService;
import com.presnakov.hotelbooking.service.OrderService;
import com.presnakov.hotelbooking.service.RoomService;
import com.presnakov.hotelbooking.service.UserService;
import com.presnakov.hotelbooking.validation.group.CreateAction;
import com.presnakov.hotelbooking.validation.group.UpdateAction;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final RoomService roomService;
    private final UserService userService;
    private final OrderService orderService;
    private final HotelService hotelService;

    @GetMapping
    public String findAll(Model model,
                          OrderFilter filter, Pageable pageable) {
        Page<OrderReadDto> page = orderService.findAll(filter, pageable);
        model.addAttribute("orders", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("hotels", hotelService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("hotels", hotelService.findAll());
                    model.addAttribute("users", userService.findAll());
                    model.addAttribute("rooms", roomService.findAll());
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(Model model, @ModelAttribute("order") OrderCreateEditDto order) {
        model.addAttribute("order", order);
        model.addAttribute("hotels", hotelService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        return "order/create";
    }

    @PostMapping
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) OrderCreateEditDto order,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("order", order);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/orders/create";
        }
        orderService.create(order);
        return "redirect:/orders";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) OrderCreateEditDto order) {
        return orderService.update(id, order)
                .map(it -> "redirect:/orders/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!orderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/orders";
    }
}
