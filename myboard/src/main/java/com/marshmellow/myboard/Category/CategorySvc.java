package com.marshmellow.myboard.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategorySvc {

	private final CategoryRepo ctgrRepo;
	
	public List<Category> getList(){
		return this.ctgrRepo.findAll();
	}
	
	public Optional<Category> get(Integer id) {
		return this.ctgrRepo.findById(id);
	}
}
