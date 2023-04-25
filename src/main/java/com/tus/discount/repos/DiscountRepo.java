package com.tus.discount.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tus.discount.model.Discount;

public interface DiscountRepo extends JpaRepository<Discount, Long> {

	Discount findByCode(String code);
	List<Discount> findAll();
}
