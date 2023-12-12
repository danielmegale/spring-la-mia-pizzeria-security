package org.java.spring.db.service;

import java.util.List;

import org.java.spring.db.pojo.Coupon;
import org.java.spring.db.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;

	public List<Coupon> findAll() {

		return couponRepository.findAll();
	}
	
	public Coupon findById(int id) {
		return couponRepository.findById(id).get();
	}
	
	public void save(Coupon coupon) {
		couponRepository.save(coupon);
	}
	
	public void delete(Coupon coupon) {
		
		couponRepository.delete(coupon);
	}
}
