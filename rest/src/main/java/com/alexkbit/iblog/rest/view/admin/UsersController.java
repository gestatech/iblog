package com.alexkbit.iblog.rest.view.admin;

import com.alexkbit.iblog.model.User;
import com.alexkbit.iblog.rest.RESTController;
import com.alexkbit.iblog.rest.dto.UserDTO;
import com.alexkbit.iblog.servvices.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for page with list of users
 */
@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("admin")
public class UsersController extends RESTController<User, UserDTO> {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public ModelAndView get(@RequestParam(value = "page", defaultValue = "0") Integer page,
                            @RequestParam(value = "count", defaultValue = "25") Integer count) {
        return new ModelAndView("admin/users", "page", mapToDTO(userService.get(page, count)));
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView enable(@ModelAttribute("page") Integer page,
                               @ModelAttribute("count") Integer count,
                               @ModelAttribute("userId") String userId,
                               @ModelAttribute("enable") Boolean enable) {
        userService.enableUser(userId, enable);
        return get(page, count);
    }
}
