package com.aquent.crudapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aquent.crudapp.domain.Person;
import com.aquent.crudapp.domain.User;
import com.aquent.crudapp.service.PersonService;
import com.aquent.crudapp.service.UserService;
import com.aquent.crudapp.util.TheHelp;

/**
 * Controller for handling basic user management operations.
 */
@Controller
@RequestMapping("user")
public class UserController {

    public static final String COMMAND_DELETE = "Delete";

    @Inject private UserService userService;
    @Inject private PersonService personService;

    /**
     * Renders the listing page.
     *
     * @return list view populated with the current list of users
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("users", userService.listUsers());
        return mav;
    }
    
    /**
     * Renders an empty form used to login.
     *
     * @return create view populated with an empty user
     */
    @RequestMapping(value = "signin", method = RequestMethod.GET)
    public ModelAndView signin() {
    	ModelAndView mav = new ModelAndView("user/signin");
        return mav;
    }
    
    /**
     * Validates user login.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param user populated form bean for the user
     * @return redirect, or create view with errors
     */
    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public ModelAndView signin(User user) {
        User result = userService.validateUserLogin(user);
        if (result != null) {
            return new ModelAndView("redirect:/person/list");
        } else {
            ModelAndView mav = new ModelAndView("user/signin");
            mav.addObject("errors", "Username and/or password is not found.  Try again or create a user.");
            return mav;
        }
    }

    /**
     * Renders an empty form used to create a new user record.
     *
     * @return create view populated with an empty user
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("user/create");
        mav.addObject("user", new User());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    /**
     * Validates and saves a new user.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param user populated form bean for the user
     * @return redirect, or create view with errors
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ModelAndView create(User user) {
        List<String> errors = userService.validateUser(user);
        if (errors.isEmpty()) {
            userService.createUser(user);
            return new ModelAndView("redirect:/user/signin");
        } else {
            ModelAndView mav = new ModelAndView("user/create");
            mav.addObject("user", user);
            mav.addObject("errors", errors);
            return mav;
        }
    }
    
    /**
     * Renders an edit form for an existing user record.
     *
     * @param userId the ID of the user to edit
     * @return edit view populated from the user record
     */
    @RequestMapping(value = "edit/{userId}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer userId, Model model) {
        ModelAndView mav = new ModelAndView("user/edit");
        mav.addObject("user", userService.readUser(userId));
        mav.addObject("personList", personList());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    /**
     * Validates and saves an edited user.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param user populated form bean for the user
     * @return redirect, or edit view with errors
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(User user) {
        List<String> errors = userService.validateUser(user);
        if (errors.isEmpty()) {
            userService.updateUser(user);
            return new ModelAndView("redirect:/user/list");
        } else {
            ModelAndView mav = new ModelAndView("user/edit");
            mav.addObject("user", user);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    /**
     * Renders the deletion confirmation page.
     *
     * @param userId the ID of the user to be deleted
     * @return delete view populated from the user record
     */
    @RequestMapping(value = "delete/{userId}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Integer userId) {
        ModelAndView mav = new ModelAndView("user/delete");
        mav.addObject("user", userService.readUser(userId));
        return mav;
    }

    /**
     * Handles user deletion or cancellation, redirecting to the listing page in either case.
     *
     * @param command the command field from the form
     * @param userId the ID of the user to be deleted
     * @return redirect to the listing page
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam String command, @RequestParam Integer userId) {
        if (COMMAND_DELETE.equals(command)) {
            userService.deleteUser(userId);
        }
        return "redirect:/user/list";
    }

    @ModelAttribute("personList")
	public Set<Map.Entry<Integer, String>> personList() {
		Set<Map.Entry<Integer, String>> people;
		Map<Integer, String> selectItems = new HashMap<>();
		List<Person> jdbcPeople = personService.listPeople();
		for (Person person : jdbcPeople) {
			selectItems.put(person.getClientId(), TheHelp.getFullname(person.getFirstName(), person.getLastName()));
		}
		people = selectItems.entrySet();
		return people;
	}
}
