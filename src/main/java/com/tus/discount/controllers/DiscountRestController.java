package com.tus.discount.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
     * @param discount The discount to be created, provided as a request body.
     * @return ResponseEntity with a status code of 201 CREATED and the created discount in the response body.
     */
	@RequestMapping(value = "/discounts", method = RequestMethod.POST)
	public ResponseEntity<Discount> create(@RequestBody Discount discount) {
		// Creating Discount object 
		Discount createdDiscount = repo.save(discount);
		// Using ServletUriComponentsBuilder to build the Location header URL
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
	            .path("/discounts")
	            .buildAndExpand(createdDiscount.getId())
	            .toUri();
		// Returning the ResponseEntity with the created discount in the response body
	    return ResponseEntity.created(location).body(createdDiscount);
	}

	/**
     * Handling a GET request to retrieve a discount by its code.
     * @param code The code of the discount to be retrieved.
     * @return A ResponseEntity containing the retrieved discount or a not found status if the discount was not found.
     */
	@RequestMapping(value = "/discounts/{code}", method = RequestMethod.GET)
	public ResponseEntity<Discount> getDiscount(@PathVariable("code") String code) {
		// Finding Discount objects with provided code and saving it into soughtDiscounts object
		Discount soughtDiscounts = repo.findByCode(code);
		// Returning a not found status if no matches found
		if(soughtDiscounts == null) {
			return ResponseEntity.notFound().build();
		}
		// Returning a response entity with the retrieved discount and an OK status
		return ResponseEntity.status(HttpStatus.OK).body(soughtDiscounts);
	}
	
	
	/**
     * Handling a GET request to retrieve all discounts.
     * @return A ResponseEntity containing the retrieved a List of all discounts or a not found status if no discount was not found.
     */
	@RequestMapping(value = "/discounts", method = RequestMethod.GET)
	public ResponseEntity<List<Discount>> getDiscounts() {
		// Finding Discount objects and saving them into a list
		List<Discount> allDiscounts = repo.findAll();
		// Returning a not found status if no matches found
		if(allDiscounts == null) {
			return ResponseEntity.notFound().build();
		}
		// Returning a response entity with the retrieved discounts and an OK status
		return ResponseEntity.status(HttpStatus.OK).body(allDiscounts); 
	}
}
