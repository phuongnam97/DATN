package project3.ginp14.controller;

import com.cloudinary.utils.ObjectUtils;
import com.sun.java.accessibility.util.EventID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project3.ginp14.config.CloudinaryConfig;
import project3.ginp14.dao.UserDao;
import project3.ginp14.entity.*;
import project3.ginp14.entity.enumObj.ItemStatus;
import project3.ginp14.entity.schema.*;
import project3.ginp14.service.*;
import sun.security.krb5.internal.Ticket;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantTypeService restaurantTypeService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TableService tableService;

    @Autowired
    private DishService dishService;

    @Autowired
    private DishTypeService dishTypeService;

    @Autowired
    private CloudinaryConfig cloudinary;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    // Dashboard
    @GetMapping("/home")
    public String showDashboard() {
        return "views/restaurant/dashboard";
    }

    @GetMapping("/")
    public String showRestaurantInformation(Model model, Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        int seatHold = bookingService.getTotalSeatHold(restaurant.getId());

        System.out.println(seatHold);
        model.addAttribute("seatNeed", seatHold);
        model.addAttribute("obj", restaurant);
        model.addAttribute("userFullName", user.getFullname());
        model.addAttribute("seatNeedProcess", bookingService.countAllByRestaurantAndVerifyStatus(restaurant, 0));
        return "views/restaurant/dashboard";
    }


    // Infomation edit
    @GetMapping("/info/edit")
    public String showFormEditRestaurantInformation(Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        List<RestaurantType> restaurantTypes = restaurantTypeService.findAll();

        System.out.println(restaurant.getId());
        model.addAttribute("restaurantTypes", restaurantTypes);
        model.addAttribute("obj", restaurant);
        model.addAttribute("userFullName", user.getFullname());
        return "views/restaurant/restaurantinfo/edit_restaurant_info";
    }

    @PostMapping("/info/edit")
    public String processEditRestaurantInformation(@ModelAttribute("obj") Restaurant restaurant, Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant1 = restaurantService.findByUserId(user.getId());
        restaurant.setId(restaurant1.getId());
        restaurant.setUser(user);

        restaurantService.save(restaurant);
        return "redirect:/restaurant/";
    }


    //Booking management
    @GetMapping("/booking")
    public String showListBooking(Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        List<Booking> listBooking = bookingService.findAllByRestaurantId(restaurant);

        model.addAttribute("userFullname", user.getFullname());
        model.addAttribute("isEmpty", listBooking.isEmpty());
        model.addAttribute("listObj", listBooking);
        return "views/restaurant/booking/list_booking";
    }

    @GetMapping("/booking/create")
    public String showFormCreateBooking(Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Booking booking = new Booking();
        model.addAttribute("obj", booking);
        model.addAttribute("userFullName", user.getFullname());
        return "views/restaurant/booking/create_booking";
    }

    @PostMapping("/booking/create")
    public String processCreateBooking(@ModelAttribute("obj") Booking booking, Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        booking.setBookingDatetime(booking.getBookingDatetime().replace("T", " "));
        booking.setRestaurant(restaurant);
        booking.setCreatedUser(user);
        booking.setVerifyStatus(1);
        booking.setCheckStatus(1);
        bookingService.save(booking);
        return "redirect:/restaurant/booking";
    }

    @GetMapping("/booking/edit")
    public String showEditForm(@RequestParam int id, Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Booking booking = bookingService.findById(id);
        System.out.println(booking.getCheckStatus());
        System.out.println(booking.getVerifyStatus());
        booking.setBookingDatetime(booking.getBookingDatetime().replace(" ", "T"));
        List<Dish> listDish = dishService.findByRestaurant(booking.getRestaurant());
        List<Order> listOrder = orderService.findByBooking(booking);
        List<Table> listTableAvaib = tableService.findEmptyTableByRestaurant(restaurantService.findByUserId(user.getId()), booking.getBookingDatetime(), booking.getQuantity());
        listTableAvaib.add(booking.getTable());
        listTableAvaib.sort((o1, o2) -> {
            if (o1.getId() > o2.getId()) {
                return 1;
            } else if (o1.getId() < o2.getId()) return -1;
            else return 0;
        });
        if (isBookingOfCurrentRestaurant(user, id)) {
            model.addAttribute("obj", booking);
            model.addAttribute("userFullName", user.getFullname());
            model.addAttribute("listDish", listDish);
            model.addAttribute("listOrder", listOrder);
            model.addAttribute("listTable", listTableAvaib);
            return "views/restaurant/booking/edit_booking";
        } else return "redirect:/restaurant/booking";
    }

    @PostMapping("/booking/edit")
    public String processEditBooking(@ModelAttribute("obj") Booking booking, Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        if (isBookingOfCurrentRestaurant(user, booking.getId())) {
            booking.setBookingDatetime(booking.getBookingDatetime().replace("T", " "));

            bookingService.save(booking);
            return "redirect:/restaurant/booking";
        }
        return "redirect:/restaurant/booking";
    }

    @GetMapping("/booking/delete")
    public String deleteRestaurantType(@RequestParam int id, Model model, Principal principal) {
        if (isBookingOfCurrentRestaurant(userDao.findByUsername(principal.getName()), id)) {
            try {
                Booking booking = bookingService.findById(id);
                booking.setVerifyStatus(3);
                bookingService.save(booking);
                String message = "Delete successfull !!!";
                model.addAttribute("message", message);
                model.addAttribute("isSuccess", true);
            } catch (Exception e) {
                String message = "Delete failed !!!";
                model.addAttribute("message", message);
                model.addAttribute("isSuccess", false);
            }
        } else {
            String message = "Delete failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:/restaurant/booking";
    }

    public boolean isBookingOfCurrentRestaurant(User user, int bookingId) {
        // nếu booking thuộc restaurant
        if (restaurantService.findByUserId(user.getId()).getId() == bookingService.findById(bookingId).getRestaurant().getId()) {
            return true;
        }
        return false;
    }

    @GetMapping("/booking/confirm-booking")
    public String confirmBooking(@RequestParam("bookingId") int id, Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        if (isBookingOfCurrentRestaurant(user, id)) {
            Booking booking = bookingService.findById(id);
            booking.setVerifyStatus(1);
            bookingService.save(booking);
        }
        return "redirect:/restaurant/booking";
    }

    @GetMapping("/booking/confirm-arrived")
    public String confirmGuestArrived(@RequestParam("bookingId") int id, Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        if (isBookingOfCurrentRestaurant(user, id)) {
            Booking booking = bookingService.findById(id);
            booking.setVerifyStatus(2);
            bookingService.save(booking);
        }
        return "redirect:/restaurant/booking";
    }


    // Table management
    @GetMapping("/table")
    public String showListTable(Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        model.addAttribute("userFullname", user.getFullname());
        List<Table> listObj = tableService.findByRestaurant(restaurant);
        model.addAttribute("listObj", listObj);
        model.addAttribute("userFullName", user.getFullname());
        return "views/restaurant/table/list_table";
    }

    @GetMapping("/table/create")
    public String showFormCreateTable(Model model, Principal principal, HttpSession httpSession) {
        Table table = new Table();
        model.addAttribute("obj", table);
        return "views/restaurant/table/create_table";
    }

    @PostMapping("/table/create")
    public String processCreateTable(@ModelAttribute("obj") Table table, @RequestParam("no_of_table") int quantity, Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        table.setRestaurant(restaurant);
        String code = "";
        code += "BAN";
        code += restaurant.getId();
        code += table.getQuantity();
        int number = 0;
        Table table0 = tableService.findTopByCodeContains("%" + code + "%");

        if (table0 != null) {
            number = Integer.parseInt(table0.getCode().replace(code, ""));
        }

        for (int i = 0; i < quantity; i++) {
            Table table1 = new Table();
            table1.setCode(code + (number + i + 1));
            table1.setRestaurant(restaurant);
            table1.setDescription(table.getDescription());
            table1.setQuantity(table.getQuantity());
            tableService.save(table1);
        }
        System.out.println(quantity);
        System.out.println(table.getDescription());
        return "redirect:/restaurant/table";
    }

    @GetMapping("/table/delete")
    public String deleteTable(@RequestParam int id, Model model, Principal principal) {
        if (isTableOfCurrentRestaurant(userDao.findByUsername(principal.getName()), id)) {
            try {
                Table table = tableService.findById(id);
                tableService.deleteById(id);
                String message = "Delete successfull !!!";
                model.addAttribute("message", message);
                model.addAttribute("isSuccess", true);
            } catch (Exception e) {
                String message = "Delete failed !!!";
                model.addAttribute("message", message);
                model.addAttribute("isSuccess", false);
            }
        } else {
            String message = "Delete failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:/restaurant/table";
    }

    public boolean isTableOfCurrentRestaurant(User user, int id) {
        if (restaurantService.findByUserId(user.getId()).getId() == tableService.findById(id).getRestaurant().getId()) {
            return true;
        }
        return false;
    }


    // Dish Management
    @GetMapping("/dish")
    public String showListDish(Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        List<Dish> listDish = dishService.findByRestaurant(restaurant);

        model.addAttribute("userFullname", user.getFullname());
        model.addAttribute("listObj", listDish);
        return "views/restaurant/dish/list_dish";
    }

    @GetMapping("/dish/create")
    public String showFormCreateDish(Model model, Principal principal, HttpSession httpSession) {
        Dish dish = new Dish();
        List<DishType> listDishType = dishTypeService.findAll();
        model.addAttribute("obj", dish);
        model.addAttribute("listDishType", listDishType);
        return "views/restaurant/dish/create_dish";
    }

    @PostMapping("/dish/create")
    public String processCreateDish(@ModelAttribute("obj") Dish dish, @RequestParam("file") MultipartFile file, Model model, Principal principal, HttpSession httpSession) {
        try {
            User user = userDao.findByUsername(principal.getName());
            Restaurant restaurant = restaurantService.findByUserId(user.getId());
            dish.setRestaurant(restaurant);
            Map uploadResult = cloudinary.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String url = uploadResult.get("url").toString();
            System.out.println(url);
            dish.setImage(url);
            dishService.save(dish);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/restaurant/dish";
    }

    @GetMapping("/dish/edit")
    public String showFormEditDish(@RequestParam int id, Model model, Principal principal, HttpSession httpSession) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        Dish dish = dishService.findById(id);
        List<DishType> listDishType = dishTypeService.findAll();
        model.addAttribute("listDishType", listDishType);
        model.addAttribute("obj", dish);
        dish.getPriceString();
        if (isDishOfCurrentRestaurant(user, id)) {
            model.addAttribute("obj", dish);
            model.addAttribute("userFullName", user.getFullname());
            return "views/restaurant/dish/edit_dish";
        } else return "redirect:/restaurant/dish";
    }

    @PostMapping("/dish/edit")
    public String processEditDish(@ModelAttribute("obj") Dish dish, @RequestParam("file") MultipartFile file, @RequestParam("currentUrlImage") String currentUrlImage, Model model, Principal principal, HttpSession httpSession) {
        try {
            User user = userDao.findByUsername(principal.getName());
            Restaurant restaurant = restaurantService.findByUserId(user.getId());
            dish.setRestaurant(restaurant);
            if (!file.isEmpty()) {
                Map uploadResult = cloudinary.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                String url = uploadResult.get("url").toString();
                dish.setImage(url);
            } else dish.setImage(currentUrlImage);
            dishService.save(dish);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/restaurant/dish";
    }

    @GetMapping("/dish/delete")
    public String deleteDish(@RequestParam int id, Model model, Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        Restaurant restaurant = restaurantService.findByUserId(user.getId());
        if (isDishOfCurrentRestaurant(user, id)) {
            try {
                Dish dish = dishService.findById(id);
                dishService.deleteById(id);
                String message = "Delete successfull !!!";
                model.addAttribute("message", message);
                model.addAttribute("isSuccess", true);
            } catch (Exception e) {
                String message = "Delete failed !!!";
                model.addAttribute("message", message);
                model.addAttribute("isSuccess", false);
            }
        } else {
            String message = "Delete failed !!!";
            model.addAttribute("message", message);
            model.addAttribute("isSuccess", false);
        }
        return "redirect:/restaurant/dish";
    }

    public boolean isDishOfCurrentRestaurant(User user, int id) {
        if (restaurantService.findByUserId(user.getId()).getId() == dishService.findById(id).getRestaurant().getId()) {
            return true;
        }
        return false;
    }

    @GetMapping("/order/create")
    public String createOrders(@RequestParam("listId") String listId, @RequestParam("bookingId") int bookingId) {
        System.out.println("========================");
        System.out.println(listId);
        System.out.println("========================");
        String[] listDishId = listId.split(",");
        try {
            for (int i = 0; i < listDishId.length; i++) {
                Booking booking = bookingService.findById(bookingId);
                Dish dish = dishService.findById(Integer.valueOf(listDishId[i]));
                Order order = orderService.findByBookingAndDish(booking, dish);
                if (order == null) {
                    order = new Order();
                    order.setBooking(booking);
                    order.setDish(dish);
                    order.setQuantity(1);
                    order.setUpdateDate(new Date());
                    order.setCreateDate(new Date());
                    orderService.save(order);

                    Item item = new Item(booking, dish, ItemStatus.PENDING);
                    itemService.save(item);
                }

            }
            return "redirect:/restaurant/booking/edit?id=" + bookingId + "#dish-item";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/restaurant/booking/edit?id=" + bookingId + "#dish-item";
    }

    @PostMapping("/order/update")
    public @ResponseBody
    List<Order> editOrder(@RequestBody List<OrderUpdate> listOrderUpdate) {
        List<Order> listOrder = new ArrayList<>();

        for (OrderUpdate order : listOrderUpdate) {
            Dish dish = dishService.findById(order.getDishId());
            Order order1 = orderService.findById(order.getOrderId());
            if (order1.getQuantity() < order.getQuantity()) {
                // Tăng số lượng suất ăn
                // Lưu thông tin mới với số lượng suất ăn lớn hơn
                order1.setQuantity(order.getQuantity());
                orderService.save(order1);

                // Kiểm tra số suất đã có
                List<Item> listCurrentItem = itemService.findByBookingAndDish(order1.getBooking(), dish);
                int noCurrentItem = listCurrentItem.size();
                if (noCurrentItem < order1.getQuantity()) {
                    int itemNeeded = order1.getQuantity() - noCurrentItem;
                    for (int i = 0; i < itemNeeded; i++) {
                        Item item = new Item(order1.getBooking(), dish, ItemStatus.PENDING);
                        itemService.save(item);
                    }
                }
            }

            if (order1.getQuantity() > order.getQuantity()) {
                // Giảm số lượng suất ăn
                // Kiểm tra số suất ăn Pending
                List<Item> listCurrentPendingItem = itemService.findByBookingAndDishAndItemStatus(order1.getBooking(), dish, ItemStatus.PENDING);

                int noItemNeedDelete = order1.getQuantity() - order.getQuantity();
                // Trường hợp có pending đủ để xóa
                if (listCurrentPendingItem.size() >= noItemNeedDelete) {
                    for (int i = 0; i < noItemNeedDelete; i++) {
                        Item item = listCurrentPendingItem.get(i);
                        itemService.deleteById(item.getId());
                    }
                    order1.setQuantity(order.getQuantity());
                    orderService.save(order1);
                }
                // trường hợp ko đủ
                // xóa tất cả những item pending
                // trả về số lượng đã xóa và chưa xóa được
                if (listCurrentPendingItem.size() < noItemNeedDelete) {
                    // số lượng đã xóa
                    int noDeleted = listCurrentPendingItem.size();
                    int noUnDeleted = noItemNeedDelete - noDeleted;
                    for (Item item : listCurrentPendingItem) {
                        itemService.deleteById(item.getId());
                    }
                    order1.setQuantity(order1.getQuantity() - noDeleted);
                    orderService.save(order1);
                }
            }
            listOrder.add(order1);
        }
        // Trả về message
        return listOrder;
    }

    @PostMapping("/table/getListAvailable")
    public @ResponseBody
    List<Table> editOrder(@RequestBody BookingRequest bookingRequest, Principal principal) {
        Restaurant restaurant = restaurantService.findByUserId(userDao.findByUsername(principal.getName()).getId());
        List<Table> listTable = tableService.findEmptyTableByRestaurant(restaurant, bookingRequest.getBookingDateTime(), bookingRequest.getQuantity());
        return listTable;
    }

    // Kitchen management
    @GetMapping("/kitchen/list-items")
    public String showListItems(Model model, Principal principal) {
        Restaurant restaurant = restaurantService.findByUserId(userDao.findByUsername(principal.getName()).getId());
        List<Item> listItemPending = itemService.findByItemStatusAndBooking_Restaurant(ItemStatus.PENDING, restaurant);
        List<Item> listItemCooking = itemService.findByItemStatusAndBooking_Restaurant(ItemStatus.COOKING, restaurant);
        List<Item> listItemFinish = itemService.findByItemStatusAndBooking_Restaurant(ItemStatus.FINISH, restaurant);

        Collections.sort(listItemPending);
        Collections.sort(listItemCooking);
        Collections.sort(listItemFinish);
        List<KitchenItem> listPending = convertToKitchenItemArray(listItemPending);
        List<KitchenItem> listCooking = convertToKitchenItemArray(listItemCooking);
        List<KitchenItem> listFinish = convertToKitchenItemArray(listItemFinish);

        model.addAttribute("listItemPending", listPending);
        model.addAttribute("listItemCooking", listCooking);
        model.addAttribute("listItemFinish", listFinish);

        return "views/restaurant/item/list_items";
    }

    public static List<KitchenItem> convertToKitchenItemArray(List<Item> listItem) {
        List<KitchenItem> listKitchenItem = new ArrayList<>();
        int count = 1;
        if (listItem.size() > 1) {
            for (int i = 0; i < listItem.size(); i++) {
                if (i > 0) {
                    if (listItem.get(i).getDish().getId() == listItem.get(i - 1).getDish().getId()) {
                        count += 1;
                    } else {
                        KitchenItem kitchenItem = new KitchenItem(listItem.get(i - 1).getDish(), count);
                        listKitchenItem.add(kitchenItem);
                        count = 1;
                    }
                    if (i == listItem.size() - 1) {
                        KitchenItem kitchenItem = new KitchenItem(listItem.get(i).getDish(), count);
                        listKitchenItem.add(kitchenItem);
                    }
                }
            }
        }
        if (listItem.size() == 1) {
            KitchenItem kitchenItem = new KitchenItem(listItem.get(0).getDish(), 1);
            listKitchenItem.add(kitchenItem);
        }
        return listKitchenItem;
    }

    @PostMapping("/kitchen/process-item")
    public @ResponseBody
    List<List<KitchenItem>> processItem(@RequestBody ProcessItemRequest request, Principal principal) {
        List<List<KitchenItem>> listUpdate = new ArrayList<>();
        Restaurant restaurant = restaurantService.findByUserId(userDao.findByUsername(principal.getName()).getId());
        Dish dish = dishService.findById(request.getDishId());
        ItemStatus itemStatus = ItemStatus.PENDING;
        ItemStatus nextStatus = ItemStatus.COOKING;
        if (request.getType().equals("cooking")) {
            itemStatus = ItemStatus.COOKING;
            nextStatus = ItemStatus.FINISH;
        }
        List<Item> listItem = itemService.findByItemStatusAndBooking_RestaurantAndDish(itemStatus, restaurant, dish);
        for (int i = 0; i < request.getQuantity(); i++) {
            Item item = listItem.get(i);
            item.setItemStatus(nextStatus);
            itemService.save(item);
        }

        listUpdate = getListKitchenItem(restaurant);
        return listUpdate;
    }

    @PostMapping("/kitchen/create-ticket")
    public @ResponseBody
    List createNewTicket(Principal principal) {
        Restaurant restaurant = restaurantService.findByUserId(userDao.findByUsername(principal.getName()).getId());
        List<Item> listItemFinish = itemService.findByItemStatusAndBooking_RestaurantOrderById(ItemStatus.FINISH, restaurant);
        List<Item> updatedItem = new ArrayList<>();
        List response = new ArrayList();

        if (listItemFinish.size() > 5) {
            for (int i = 0; i < 5; i++) {
                Item item = listItemFinish.get(i);
                item.setItemStatus(ItemStatus.SERVED);
                itemService.save(item);
                updatedItem.add(item);
            }
        } else {
            for (int i = 0; i < listItemFinish.size(); i++) {
                Item item = listItemFinish.get(i);
                item.setItemStatus(ItemStatus.SERVED);
                itemService.save(item);
                updatedItem.add(item);
            }
        }

        updatedItem.sort((o1, o2) -> {
            int t1 = (o1.getDish().getId() > o2.getDish().getId()) ? 1 : (o1.getDish().getId() == o2.getDish().getId()) ? 0 : -1;
            int t2 = (o1.getBooking().getId() > o2.getBooking().getId()) ? 1 : (o1.getBooking().getId() == o2.getBooking().getId()) ? 0 : -1;
            return (t2 > 0) ? t2 : t1;
        });
        List<List<KitchenItem>> listKitchenItem = getListKitchenItem(restaurant);
        List<TicketDetail> listTicketDetail = getTicketDetail(updatedItem);
        response.add(listTicketDetail);
        response.add(listKitchenItem);
        return response;
    }

    @PostMapping("/kitchen/update")
    public @ResponseBody
    List<List<KitchenItem>> updateItem(Principal principal) {
        Restaurant restaurant = restaurantService.findByUserId(userDao.findByUsername(principal.getName()).getId());
        List<List<KitchenItem>> listKitchenItem = getListKitchenItem(restaurant);

        return listKitchenItem;
    }

    public List<List<KitchenItem>> getListKitchenItem(Restaurant restaurant) {
        List<List<KitchenItem>> listKitchenItem = new ArrayList<>();

        List<Item> listPending = itemService.findByItemStatusAndBooking_Restaurant(ItemStatus.PENDING, restaurant);
        List<Item> listCooking = itemService.findByItemStatusAndBooking_Restaurant(ItemStatus.COOKING, restaurant);
        List<Item> listFinish = itemService.findByItemStatusAndBooking_Restaurant(ItemStatus.FINISH, restaurant);

        Collections.sort(listPending);
        Collections.sort(listCooking);
        Collections.sort(listFinish);

        List<KitchenItem> list1 = convertToKitchenItemArray(listPending);
        List<KitchenItem> list2 = convertToKitchenItemArray(listCooking);
        List<KitchenItem> list3 = convertToKitchenItemArray(listFinish);

        listKitchenItem.add(list1);
        listKitchenItem.add(list2);
        listKitchenItem.add(list3);
        return listKitchenItem;
    }

    public List<TicketDetail> getTicketDetail(List<Item> listItems) {
        List<TicketDetail> listTicketDetail = new ArrayList<>();
        for (Item item : listItems) {
            Booking booking = item.getBooking();
            Dish dish = item.getDish();
            int index = checkExistBooking(booking, listTicketDetail);
            if (index >= 0) {
                List<DishOrderServed> listDishOrderServed = listTicketDetail.get(index).getListDishOrderServed();
                int index2 = checkExistDish(dish, listDishOrderServed);
                if (index2 >= 0){
                    listTicketDetail.get(index).getListDishOrderServed().get(index2).setQuantity(listTicketDetail.get(index).getListDishOrderServed().get(index2).getQuantity()+1);
                } else {
                    DishOrderServed dishOrderServed = new DishOrderServed(dish, 1);
                    listTicketDetail.get(index).getListDishOrderServed().add(dishOrderServed);
                }
            } else {
                TicketDetail ticketDetail = new TicketDetail();
                List<DishOrderServed> listDishOrderServed = new ArrayList<>();
                listDishOrderServed.add(new DishOrderServed(dish, 1));
                ticketDetail.setBooking(booking);
                ticketDetail.setListDishOrderServed(listDishOrderServed);
                listTicketDetail.add(ticketDetail);
            }
        }
        return listTicketDetail;
    }

    public int checkExistBooking(Booking booking, List<TicketDetail> listTicketDetail) {
        for (int i = 0; i < listTicketDetail.size(); i++) {
            if (listTicketDetail.get(i).getBooking() == booking) {
                return i;
            }
        }
        return -1;
    }

    public int checkExistDish(Dish dish, List<DishOrderServed> listDistOrderServed){
        for (int i = 0; i< listDistOrderServed.size(); i++){
            if (listDistOrderServed.get(i).getDish() == dish){
                return i;
            }
        }
        return -1;
    }

    // Xem trước thông tin thanh toán
    @GetMapping("/payment")
    public String showPayment(Model model, @RequestParam int bookingId){
        Booking booking = bookingService.findById(bookingId);
        // List order
        List<Order> listOrder = orderService.findByBooking(booking);

        int sum = 0;

        for ( Order order: listOrder){
            int price = order.getDish().getPrice();
            int quantity = order.getQuantity();
            sum += price*quantity;
        }

        int principalSum = sum;
        int tax = sum*10/100;

        sum += tax;

        model.addAttribute("tax", decimalFormat.format((double)tax));
        model.addAttribute("sum", decimalFormat.format((double) sum));
        model.addAttribute("principalSum", decimalFormat.format((double)principalSum));
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("booking", booking);
        model.addAttribute("restaurant", booking.getRestaurant());

        return "views/restaurant/payment/payment-view";
    }
}
