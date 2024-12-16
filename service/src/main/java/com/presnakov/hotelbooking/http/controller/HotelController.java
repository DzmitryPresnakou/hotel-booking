package com.presnakov.hotelbooking.http.controller;

import com.presnakov.hotelbooking.dto.HotelCreateEditDto;
import com.presnakov.hotelbooking.dto.HotelReadDto;
import com.presnakov.hotelbooking.dto.PageResponse;
import com.presnakov.hotelbooking.service.HotelService;
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
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public String findAll(Model model, Pageable pageable) {
        Page<HotelReadDto> page = hotelService.findAll(pageable);
        model.addAttribute("hotels", PageResponse.of(page));
        return "hotel/hotels";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return hotelService.findById(id)
                .map(hotel -> {
                    model.addAttribute("hotel", hotel);
                    return "hotel/hotel";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(Model model, @ModelAttribute("hotel") HotelCreateEditDto hotel) {
        model.addAttribute("hotel", hotel);
        return "hotel/create";
    }

    @PostMapping
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) HotelCreateEditDto hotel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("hotel", hotel);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/hotels/create";
        }
        hotelService.create(hotel);
        return "redirect:/hotels";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) HotelCreateEditDto hotel) {
        return hotelService.update(id, hotel)
                .map(it -> "redirect:/hotels/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!hotelService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/hotels";
    }
}
