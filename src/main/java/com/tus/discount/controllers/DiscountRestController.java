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

@SuppressWarnings("unused")
@RestController
@RequestMapping("/discountapi")
public class DiscountRestController {

	@Autowired
	DiscountRepo repo;

	@RequestMapping(value = "/discounts", method = RequestMethod.POST)
	public Discount create(@RequestBody Discount discount) {
		return repo.save(discount);
	}

	@RequestMapping(value = "/discounts/{code}", method = RequestMethod.GET)
	public Discount getDiscount(@PathVariable("code") String code) {
		return repo.findByCode(code);

	}
	
	@RequestMapping(value = "/discounts", method = RequestMethod.GET)
	public List<Discount> getDiscounts() {
		return repo.findAll();
	}
}
