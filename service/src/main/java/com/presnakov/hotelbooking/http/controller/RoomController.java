package com.presnakov.hotelbooking.http.controller;

import com.presnakov.hotelbooking.database.entity.RoleEnum;
import com.presnakov.hotelbooking.database.entity.RoomClassEnum;
import com.presnakov.hotelbooking.dto.PageResponse;
import com.presnakov.hotelbooking.dto.RoomCreateEditDto;
import com.presnakov.hotelbooking.dto.RoomFilter;
import com.presnakov.hotelbooking.dto.RoomReadDto;
import com.presnakov.hotelbooking.dto.UserCreateEditDto;
import com.presnakov.hotelbooking.service.HotelService;
import com.presnakov.hotelbooking.service.RoomService;
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
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;

    @GetMapping
    public String findAll(Model model,
                          RoomFilter filter, Pageable pageable) {
        Page<RoomReadDto> page = roomService.findAll(filter, pageable);
        model.addAttribute("rooms", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("hotels", hotelService.findAll());
        model.addAttribute("roomClasses", RoomClassEnum.values());
        return "room/rooms";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return roomService.findById(id)
                .map(room -> {
                    model.addAttribute("room", room);
                    model.addAttribute("hotels", hotelService.findAll());
                    model.addAttribute("roomClasses", RoomClassEnum.values());
                    return "room/room";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/save-room")
    public String create(Model model, @ModelAttribute("room") RoomCreateEditDto room) {
        model.addAttribute("room", room);
        model.addAttribute("hotels", hotelService.findAll());
        model.addAttribute("roomClasses", RoomClassEnum.values());
        return "room/save-room";
    }

    @PostMapping
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) RoomCreateEditDto room,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("room", room);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/rooms/save-room";
        }
        roomService.create(room);
        return "redirect:/rooms";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) RoomCreateEditDto room) {
        return roomService.update(id, room)
                .map(it -> "redirect:/rooms/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!roomService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/rooms";
    }
}
