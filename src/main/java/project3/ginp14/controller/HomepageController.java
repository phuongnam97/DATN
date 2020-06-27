package project3.ginp14.controller;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project3.ginp14.entity.*;
import project3.ginp14.service.*;
import project3.ginp14.utils.MailSender;

import java.security.Principal;
import java.util.*;

@Controller
public class HomepageController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantTypeService restaurantTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TableService tableService;

    public int getBookingTime(String username) {
        return bookingService.countAllByGuestTelephone(userService.findUserbyUsername(username).getTelephone());
    }

    @GetMapping("")
    public String showHomepage(Model model, Principal principal) {
        model.addAttribute("listRestaurantType", getListRestaurantType());
        try {
            model.addAttribute("bookingTime", getBookingTime(principal.getName()));
        } catch (Exception ex) {
        }
        return "views/user/homepage";
    }

    @GetMapping("/login-successful")
    public String processLogin(Principal principal) {
        User currentUser = userService.findUserbyUsername(principal.getName());
        if (currentUser.getRole().getName().equals("Role_User")) {
            return "redirect:/";
        }
        if (currentUser.getRole().getName().equals("Role_Restaurant")) {
            return "redirect:/restaurant/";
        }
        if (currentUser.getRole().getName().equals("Role_Admin")) {
            return "redirect:/admin/users";
        }
        return "redirect:/home";
    }

    @GetMapping("/list-restaurant")
    public String showRestaurantByRestaurantType(@RequestParam int restaurantTypeId, Model model, Principal principal, @PageableDefault(size = 6) Pageable pageable) {
        Page<Restaurant> listRestaurant;
        if (restaurantTypeId == 0) {
            listRestaurant = restaurantService.findAll(pageable);
        } else {
            listRestaurant = restaurantService.findAllByRestaurantType(restaurantTypeService.findById(restaurantTypeId), pageable);
        }
        try {
            model.addAttribute("bookingTime", getBookingTime(principal.getName()));
        } catch (Exception ex) {
        }
        model.addAttribute("listRestaurantType", getListRestaurantType());
        model.addAttribute("listRestaurant", listRestaurant);
        model.addAttribute("restaurantTypeId", restaurantTypeId);
        return "views/user/list-restaurant";
    }

    @GetMapping("/restaurant")
    public String showRestaurantDetail(@RequestParam int id, Model model, Principal principal) {
        Restaurant restaurant = restaurantService.findById(id);
        model.addAttribute("obj", restaurant);
        model.addAttribute("listRestaurantType", getListRestaurantType());
        try {
            model.addAttribute("bookingTime", getBookingTime(principal.getName()));
        } catch (Exception ex) {
        }
        return "views/user/restaurant-detail";
    }

    public List<RestaurantType> getListRestaurantType() {
        List<RestaurantType> listRestaurantType = restaurantTypeService.findAll();
        return listRestaurantType;
    }

    @GetMapping("/booking")
    public String showFormCreateBooking(@RequestParam int restaurantId, Model model, Principal principal) {
        User currentUser = new User();
        try {
            currentUser = userService.findUserbyUsername(principal.getName());
        } catch (Exception e) {
            return "redirect:/restaurant?id=" + restaurantId;
        }

        Restaurant restaurant = restaurantService.findById(restaurantId);
        List<Table> listTable = tableService.findByRestaurant(restaurant);
        Booking booking = new Booking();
        model.addAttribute("obj", booking);
        model.addAttribute("user_id", currentUser.getId());
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("listRestaurantType", getListRestaurantType());
        model.addAttribute("listTable", listTable);
        try {
            model.addAttribute("bookingTime", getBookingTime(principal.getName()));
        } catch (Exception ex) {
        }
        return "views/user/booking";
    }

    @PostMapping("/booking")
    public String processCreateBooking(@ModelAttribute("obj") Booking booking, Model model, Principal principal) {
        User user = userService.findUserbyUsername(principal.getName());
        booking.setGuestName(user.getFullname());
        booking.setGuestTelephone(user.getTelephone());
        booking.setCreatedUser(user);
        booking.setVerifyStatus(0);
        booking.setCheckStatus(0);
        booking.setBookingDatetime(booking.getBookingDatetime().replace("T", " "));
        List<Table> listTable = tableService.findEmptyTableByRestaurant(booking.getRestaurant(), booking.getBookingDatetime().replace("T", " "), booking.getQuantity());

        if (listTable.size() > 0) {
            Random random = new Random();
            Table table = listTable.get(random.nextInt(listTable.size()));
            booking.setTable(table);

            bookingService.save(booking);


            MailSender mailSender = new MailSender();
            mailSender.sendMail(user.getEmail(), "Đặt bàn tại cửa hàng " + booking.getRestaurant().getName(), "Gửi " + user.getFullname() + ",\n\nBạn đã đặt bàn tại nhà hàng " + booking.getRestaurant().getName() + "\nMã bàn là: " + table.getCode() + "\nBạn sẽ nhận được điện thoại xác nhận trong thời gian sớm nhất.\n\n Cảm ơn \n Hệ thống quản lý nhà hàng");
            mailSender.sendMail(booking.getRestaurant().getUser().getEmail(), "Khách hàng " + user.getFullname() + " đặt bàn tại nhà hàng của bạn", "Gửi " + booking.getRestaurant().getName() + ",\n\nKhách hàng " + user.getFullname() + " đã đặt bàn " + table.getCode() + " tại nhà hàng của bạn. \nVui lòng kiểm tra và xác nhận lại ngay khi có thể.\n\n Cảm ơn \n Hệ thống quản lý nhà hàng");
            return "redirect:/";
        } else {
            model.addAttribute("message", "Không còn bàn phù hợp với yêu cầu của bạn");
            return "redirect:/booking?restaurantId=" + booking.getRestaurant().getId();
        }
    }

    @GetMapping("/booking-history")
    public String showBookingHistory(Model model, Principal principal, @PageableDefault(size = 10) Pageable pageable) {
        try {
            System.out.println(principal.getName());
        } catch (Exception e) {
            return "redirect:/";
        }
        if (principal.getName().equals("anonymousUser")) {
            return "redirect:/";
        }
        User user = userService.findUserbyUsername(principal.getName());
        try {
            model.addAttribute("bookingTime", getBookingTime(principal.getName()));
        } catch (Exception ex) {
        }
        Page<Booking> listObj = bookingService.findAllByGuestNameAndGuestTelephone(user.getFullname(), user.getTelephone(), pageable);
        model.addAttribute("listObj", listObj);
        model.addAttribute("listRestaurantType", getListRestaurantType());
        return "views/user/booking-history";
    }

    @GetMapping("/cancel-booking")
    public String processCancelBooking(@RequestParam("bookingId") int id, Principal principal) {
        Booking booking = bookingService.findById(id);
        User user = userService.findUserbyUsername(principal.getName());
        if (user.getFullname().equals(booking.getGuestName()) && user.getTelephone().equals(booking.getGuestTelephone())) {
            booking.setVerifyStatus(3);
            bookingService.save(booking);
        }
        return "redirect:/booking-history";
    }
}
