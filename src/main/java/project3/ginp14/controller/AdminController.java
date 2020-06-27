package project3.ginp14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project3.ginp14.entity.DishType;
import project3.ginp14.entity.RestaurantType;
import project3.ginp14.entity.User;
import project3.ginp14.service.DishTypeService;
import project3.ginp14.service.RestaurantTypeService;
import project3.ginp14.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    RestaurantTypeService restaurantTypeService;

    @Autowired
    UserService userService;

    @Autowired
    DishTypeService dishTypeService;

    // Restaurant type
    @GetMapping("/restaurant_type")
    public String showListRestaurantType(Model model, @PageableDefault(size = 5) Pageable pageable){
        Page<RestaurantType> listObj = restaurantTypeService.findAll(pageable);
        if (listObj.isEmpty()){
            model.addAttribute("isEmpty",true);
        } else {
            model.addAttribute("isEmpty",false);
        }
        RestaurantType restaurantType = new RestaurantType();
        model.addAttribute("obj", restaurantType);
        model.addAttribute("listObj", listObj);
        return "views/admin/restauranttype/list_restaurant_type";
    }

    @GetMapping("/restaurant_type/create")
    public String showFormCreateRestaurantType(Model model){
        RestaurantType restaurantType = new RestaurantType();
        model.addAttribute("obj", restaurantType);
        return "views/admin/restauranttype/create_restaurant_type";
    }

    @PostMapping("/restaurant_type/create")
    public String createNewRestaurantType(@ModelAttribute("obj") RestaurantType restaurantType, BindingResult bindingResult, Model model){
        try {
            restaurantTypeService.save(restaurantType);
            String message = "Create successfull !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", true);
        } catch (Exception e){
            String message = "Create failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:";
    }

    @GetMapping("/restaurant_type/edit")
    public String showFormEditRestaurantType(@RequestParam int id, Model model){
        RestaurantType restaurantType = restaurantTypeService.findById(id);
        model.addAttribute("obj", restaurantType);
        return "views/admin/restauranttype/edit_restaurant_type";
    }

    @PostMapping("/restaurant_type/edit")
    public String editRestaurantType(@ModelAttribute("obj") RestaurantType restaurantType, BindingResult bindingResult, Model model){
        try {
            restaurantTypeService.save(restaurantType);
            String message = "Edit successfull !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", true);
        } catch (Exception e){
            String message = "Edit failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:";
    }

    @GetMapping("/restaurant_type/delete")
    public String deleteRestaurantType(@RequestParam int id, Model model){
        try{
            restaurantTypeService.deleteById(id);
            String message = "Delete successfull !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", true);
        } catch (Exception e){
            String message = "Delete failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:";
    }



    // User Management
    @GetMapping("/users")
    public String showListUsers(Model model, @PageableDefault(size = 5) Pageable pageable){
        Page<User> listObj = userService.findAllByRoleIsNotAdmin(pageable);
        if (listObj.isEmpty()){
            model.addAttribute("isEmpty",true);
        } else {
            model.addAttribute("isEmpty",false);
        }
        model.addAttribute("listObj", listObj);
        return "views/admin/users/list_users";
    }

    @GetMapping("/users/block")
    public String blockUser(@RequestParam int id, Model model){
        try {
            userService.blockUser(id);
            String message = "Block user successfull !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", true);
        } catch (Exception e){
            String message = "Block user failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:";
    }

    @GetMapping("/users/unblock")
    public String unblockUser(@RequestParam int id, Model model){
        try {
            userService.unblockUser(id);
            String message = "Unblock user successfull !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", true);
        } catch (Exception e){
            String message = "Unblock user failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:";
    }


    // DishType
    @GetMapping("/dish_type")
    public String showListDishType(Model model, @PageableDefault(size = 5) Pageable pageable){
        Page<DishType> listObj = dishTypeService.findAll(pageable);
        model.addAttribute("listObj",listObj);
        return "views/admin/dishtype/list_dish_type";
    }

    @GetMapping("/dish_type/create")
    public String showFormCreateDishType(Model model){
        DishType dishType = new DishType();
        model.addAttribute("obj", dishType);
        return "views/admin/dishtype/create_dish_type";
    }

    @PostMapping("/dish_type/create")
    public String processCreateDishType(@ModelAttribute("obj") DishType dishType, Model model){
        try {
            dishTypeService.save(dishType);
            String message = "Create successfull !!!";
            model.addAttribute("isSuccess", true);
            model.addAttribute("message", message);
        } catch (Exception e){
            String message = "Create failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:/admin/dish_type";
    }

    @GetMapping("/dish_type/edit")
    public String showFormEditDishType(@RequestParam("id") int id, Model model){
        DishType dishType = dishTypeService.findById(id);
        model.addAttribute("obj", dishType);
        return "views/admin/dishtype/edit_dish_type";
    }

    @PostMapping("/dish_type/edit")
    public String processEditDishType(@ModelAttribute("obj") DishType dishType, Model model){
        try {
            dishTypeService.save(dishType);
            String message = "Create successfull !!!";

            model.addAttribute("isSuccess", true);
            model.addAttribute("message", message);
        } catch (Exception e){
            String message = "Create failed !!!";
            model.addAttribute("isSuccess", false);
            model.addAttribute("message", message);
        }
        return "redirect:/admin/dish_type";
    }
}
