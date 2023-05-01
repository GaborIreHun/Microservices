package com.tus.discount.controllers;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.slf4j.Logger;

import com.tus.discount.model.Discount;
import com.tus.discount.repos.DiscountRepo;

/**
 * This class represents a REST controller that handles HTTP requests and responses
 * It also maps all endpoints in this class to the base URL '/discountapi'
 * @author A00304775
 * 
 */
@RestController
@RequestMapping("/discountapi")
public class DiscountRestController {
	
	@Value("${discountservice.dummyProperty1:not}")
	private String param1;
	
	@Value("${discountservice.dummyProperty2:found}")
	private String param2;

	// Setting up Logger
	Logger log = LoggerFactory.getLogger(DiscountRestController.class);
	
	// Auto-wiring a DiscountRepo instance into this class.
	@Autowired
	DiscountRepo repo;

	/**
     * Handling a POST request to create a new discount.
     * @param discount The discount to be created, provided as a request body.
     * @return ResponseEntity with a status code of 201 CREATED and the created discount in the response body.
     */
	@RequestMapping(value = "/discounts", method = RequestMethod.POST)
	public ResponseEntity<Discount> create(@NotNull @RequestBody Discount discount) {
		log.info("DiscountserviceApplication POST method called");
		if(discount.getDiscount() == null) {
			discount.setDiscount(BigDecimal.ZERO);
		}
		// Returning a bad request status if the parameters are less than zero
		if (discount.getDiscount().compareTo(BigDecimal.ZERO) < 0 || discount.getCode() == null || discount.getExpDate() == null) {
	        // Returning a bad request status if the input values are less than 0
	        return ResponseEntity.badRequest().build();
	    }
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
	public ResponseEntity<Discount> getDiscount(@Valid @PathVariable("code") String code) {
		log.info("DiscountserviceApplication GET method called with code param");
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
		log.info("DiscountserviceApplication GET method called");
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
