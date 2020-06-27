package project3.ginp14.controller;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project3.ginp14.config.CloudinaryConfig;
import project3.ginp14.dao.UserDao;
import project3.ginp14.entity.Restaurant;
import project3.ginp14.entity.RestaurantType;
import project3.ginp14.entity.User;
import project3.ginp14.service.RestaurantService;
import project3.ginp14.service.RestaurantTypeService;
import project3.ginp14.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantTypeService restaurantTypeService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CloudinaryConfig cloudinary;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "views/admin/register-form";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "views/admin/login-form";
    }

    @PostMapping("/process-register")
    public String processRegister(@ModelAttribute("user") User user, BindingResult bindingResult, Model model, HttpSession httpSession) {
        if (userService.checkExistInfo(user.getUsername(), user.getEmail(), user.getTelephone())) {
            System.out.println("Existed User have that username, email or telephone");
            return "redirect:register";
        } else {
            userService.save(user);
            if (user.getRole().getId() == 2) {
                model.addAttribute("newUser", user);
                httpSession.setAttribute("newUser", user);
                httpSession.setAttribute("isRestaurantRegister", true);
                return "redirect:create-restaurant";
            }

        }
        return "redirect:login";
    }

    @GetMapping("/create-restaurant")
    public String showCreateRestaurantForm(Model model, HttpSession httpSession) {
        System.out.println(httpSession.getAttribute("isRestaurantRegister"));
        System.out.println(httpSession.getAttribute("newUser"));
        try {
            if ((Boolean) httpSession.getAttribute("isRestaurantRegister")) {
                Restaurant restaurant = new Restaurant();
                List<RestaurantType> restaurantTypes = restaurantTypeService.findAll();
                model.addAttribute("obj", restaurant);
                model.addAttribute("restaurantTypes", restaurantTypes);
            }
        } catch (Exception e) {
            System.out.println("You can't");
            return "redirect:login";
        }
        return "views/restaurant/restaurant-create";
    }

    @PostMapping("/create-restaurant")
    public String createRestaurant(@ModelAttribute("obj") Restaurant restaurant, @RequestParam("file") MultipartFile file, Model model, HttpSession httpSession) {
        try {
            User user = (User) httpSession.getAttribute("newUser");
            restaurant.setUser(user);

            Map uploadResult = cloudinary.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String url = uploadResult.get("url").toString();

            restaurant.setImages(url);
            restaurantService.save(restaurant);

        } catch (Exception e) {
            model.addAttribute("isSuccess", false);
            model.addAttribute("message", "Failed");
            return "redirect:create-restaurant";
        }
        return "redirect:/users/login";
    }
}
