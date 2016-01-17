package com.aquent.crudapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aquent.crudapp.domain.Client;
import com.aquent.crudapp.domain.Person;
import com.aquent.crudapp.service.ClientService;
import com.aquent.crudapp.service.PersonService;

/**
 * Controller for handling basic client management operations.
 */
@Controller
@RequestMapping("client")
public class ClientController {

    public static final String COMMAND_DELETE = "Delete";
    public static final String COMMAND_ADD_CONTACT = "Add Contact";

    @Inject private ClientService clientService;
    @Inject private PersonService personService;

    /**
     * Renders the listing page.
     *
     * @return list view populated with the current list of people
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("client/list");
        mav.addObject("clients", clientService.listClients());
        return mav;
    }

    /**
     * Renders an empty form used to create a new client record.
     *
     * @return create view populated with an empty client
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("client/create");
        mav.addObject("client", new Client());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    /**
     * Validates and saves a new client.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param client populated form bean for the client
     * @return redirect, or create view with errors
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ModelAndView create(@RequestParam String command, Client client) {
        List<String> errors = clientService.validateClient(client);
        if (COMMAND_ADD_CONTACT.equals(command)){
        	Client updatedClient = clientService.updateAndReturnClient(client);
        	 ModelAndView mav = new ModelAndView("client/edit");
             mav.addObject("client", updatedClient);
             mav.addObject("contacts", contactList(updatedClient));
             mav.addObject("errors", errors);
             return mav;
        } else if (errors.isEmpty()) {
            clientService.createClient(client);
            return new ModelAndView("redirect:/client/list");
        } else {
            ModelAndView mav = new ModelAndView("client/create");
            mav.addObject("client", client);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    /**
     * Renders an edit form for an existing client record.
     *
     * @param clientId the ID of the client to edit
     * @return edit view populated from the client record
     */
    @RequestMapping(value = "edit/{clientId}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable Integer clientId) {
        ModelAndView mav = new ModelAndView("client/edit");
        mav.addObject("client", clientService.readClient(clientId));
        mav.addObject("fullContactList", fullContactList());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }
    
    /**
     * Renders a view form for an existing client record.
     *
     * @param clientId the ID of the client to view
     * @return view populated from the client record
     */
    @RequestMapping(value = "view/{clientId}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable Integer clientId) {
        ModelAndView mav = new ModelAndView("client/view");
        Client client = clientService.readClient(clientId);
        mav.addObject("client", client);
        mav.addObject("contactList", contactList(client));
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    /**
     * Validates and saves an edited client.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param client populated form bean for the client
     * @return redirect, or edit view with errors
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam String command, Client client) {
        List<String> errors = clientService.validateClient(client);
        
        if (COMMAND_ADD_CONTACT.equals(command)){
        	Client updatedClient = clientService.updateAndReturnClient(client);
        	 ModelAndView mav = new ModelAndView("client/edit");
             mav.addObject("client", updatedClient);
             mav.addObject("contacts", contactList(updatedClient));
             mav.addObject("errors", errors);
             return mav;
        } else if (errors.isEmpty()) {
            clientService.updateClient(client);
            return new ModelAndView("redirect:/client/list");
        } else {
            ModelAndView mav = new ModelAndView("client/edit");
            mav.addObject("client", client);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    /**
     * Renders the deletion confirmation page.
     *
     * @param clientId the ID of the client to be deleted
     * @return delete view populated from the client record
     */
    @RequestMapping(value = "delete/{clientId}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable Integer clientId) {
        ModelAndView mav = new ModelAndView("client/delete");
        mav.addObject("client", clientService.readClient(clientId));
        return mav;
    }

    /**
     * Handles client deletion or cancellation, redirecting to the listing page in either case.
     *
     * @param command the command field from the form
     * @param clientId the ID of the client to be deleted
     * @return redirect to the listing page
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam String command, @RequestParam Integer clientId) {
        if (COMMAND_DELETE.equals(command)) {
            clientService.deleteClient(clientId);
        }
        return "redirect:/client/list";
    }
    
    @ModelAttribute("contactList")
   	public Set<Map.Entry<Integer, String>> contactList(Client client) {
   		Set<Map.Entry<Integer, String>> contacts;
   		Map<Integer, String> selectItems = new HashMap<>();
   		List<Person> jdbcContacts = personService.listPeople();
   		
		if (client.getContacts() != null) {
			String[] contactIds = client.getContacts().split(",");
			for (Person contact : jdbcContacts) {
				if(Arrays.asList(contactIds).contains(contact.getPersonId().toString())){
					selectItems.put(contact.getPersonId(), contact.getFirstName() + " " + contact.getLastName());
				}
			}
		}
   		contacts = selectItems.entrySet();
   		return contacts;
   	}
    
	@ModelAttribute("fullContactList")
	public Set<Map.Entry<Integer, String>> fullContactList() {
		Set<Map.Entry<Integer, String>> contacts;
		Map<Integer, String> selectItems = new HashMap<>();
		List<Person> jdbcContacts = personService.listPeople();
		for (Person contact : jdbcContacts) {
			selectItems.put(contact.getPersonId(), contact.getFirstName() + " " + contact.getLastName());
		}

		contacts = selectItems.entrySet();
		return contacts;
	}
    
}
