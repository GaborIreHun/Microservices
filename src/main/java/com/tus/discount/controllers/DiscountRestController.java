package com.tus.discount.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tus.discount.model.Discount;
import com.tus.discount.repos.DiscountRepo;

/**
 * This class represents a REST controller that handles HTTP requests and responses
 * It also maps all endpoints in this class to the base URL '/discountapi'
 * @author A00304775
 * 
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/discountapi")
public class DiscountRestController {

	// Auto-wiring a DiscountRepo instance into this class.
	@Autowired
	DiscountRepo repo;

	/**
     * Handling a POST request to create a new discount.
     * @param discount The Discount object to be created.
     * @return The created Discount object.
     */
	@RequestMapping(value = "/discounts", method = RequestMethod.POST)
	public Discount create(@RequestBody Discount discount) {
		return repo.save(discount);
	}

	/**
     * Handling a GET request to retrieve a discount by its code.
     * @param code The code of the discount to be retrieved.
     * @return The Discount object with the given code, or null if not found.
     */
	@RequestMapping(value = "/discounts/{code}", method = RequestMethod.GET)
	public Discount getDiscount(@PathVariable("code") String code) {
		return repo.findByCode(code);
	}
	
	/**
     * Handling a GET request to retrieve all discounts.
     * @return A List of all Discount objects in the repository.
     */
	@RequestMapping(value = "/discounts", method = RequestMethod.GET)
	public List<Discount> getDiscounts() {
		return repo.findAll();
	}
}
