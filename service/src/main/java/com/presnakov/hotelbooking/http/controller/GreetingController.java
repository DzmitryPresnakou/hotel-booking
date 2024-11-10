package com.presnakov.hotelbooking.http.controller;

import com.presnakov.hotelbooking.dto.UserReadDto;
import com.presnakov.hotelbooking.entity.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes({"user"})
public class GreetingController {

    @ModelAttribute("roles")
    public List<RoleEnum> roles() {
        return Arrays.asList(RoleEnum.values());
    }

    @GetMapping("/hello")
    public String hello(Model model,
                        HttpServletRequest request,
                        @ModelAttribute("userReadDto") UserReadDto userReadDto) {
        model.addAttribute("user", userReadDto);

        return "greeting/hello";
    }

    @GetMapping("/bye")
    public String bye(@SessionAttribute("user") UserReadDto user, Model model) {
        return "greeting/bye";
    }
}
