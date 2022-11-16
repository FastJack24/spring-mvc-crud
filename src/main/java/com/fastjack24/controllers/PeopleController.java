package com.fastjack24.controllers;

import com.fastjack24.dao.persondao.PersonDAO;
import com.fastjack24.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO; // Already final (Bean scope is singleton)

    @Autowired // Spring is going to do this anyway.
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        // Getting everyone from DAO and pass them to the view.
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { // Default for path variable is the one mentioned.
        // Getting one person from DAO with id and pass it to the view.
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {  // We can delete model and set @ModelAttribute deleting next row.
        model.addAttribute("person", new Person());
        return "/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        // Binding result always!!! goes after validated value.
        // Also, possible with @RequestParam("paramName").
        // ID is 0 at first if the default constructor is set.
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people"; // There we don't use view directly, but call another get method.
        // @ModelAttribute("name") on methods (not request mapping) adds attribute to the model with
        // the name "name" and value that is returned from this method. (We want attribute for every model).
        // On method's parameter:
        // 1) Creates object 2) Sets fields from the form parameters (if one parameter is missed,
        // default constructor is needed, then default value will be set)
        // 3) Adds this object to the model
        // But!!! Doesn't add anything to the DB.
        // Default values if no request parameters were passed (0, null, etc.).
        // Name in form is the parameter name, id is set for the label.
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    // Filter must be configured or Spring thinks it's a post method,
    // with Spring Boot we can configure this thing in one raw.
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

    @GetMapping("/test")
    public String testPerformance() {
        personDAO.testMultipleUpdate();
        return "people/index";
    }

    @GetMapping("/test-batch")
    public String testBatchPerformance() {
        personDAO.testBatchUpdate();
        return "people/index";
    }
}
