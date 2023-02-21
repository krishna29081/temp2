package com.project.shopping.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.shopping.entity.Categories;
import com.project.shopping.exceptions.ResourceNotFoundException;
import com.project.shopping.payloads.CategoryDTO;
import com.project.shopping.repo.CategoryRepo;
import com.project.shopping.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	ModelMapper modelmapper;
	
	@Autowired
	CategoryRepo categoryrepo;
	@Override
	public CategoryDTO addCategory(CategoryDTO categoryDTO) {
		Categories cat = DTOtoCategory(categoryDTO);
		Categories addedcat = categoryrepo.save(cat);
		return CategorytoDTO(addedcat);
	}

	@Override
	public void deleteCategory(int id) {
		Categories cat = categoryrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		categoryrepo.delete(cat);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, int id) {
		Categories cat = categoryrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		cat.setCategoryTitle(categoryDTO.getCategoryTitle());
		cat.setCategoryDescrip(categoryDTO.getCategoryDescrip());
		
		Categories updatedCategory = categoryrepo.save(cat);
		return CategorytoDTO(updatedCategory);
	}
	
	@Override
	public List<CategoryDTO> getAllCategories() {
	List<Categories> cat = categoryrepo.findAll(); 
	List<CategoryDTO> list = cat.stream().map(cate -> this.CategorytoDTO(cate)).collect(Collectors.toList());
	return list;
	}

	@Override
	public CategoryDTO getCategoryById(int id) {
		Categories cat = categoryrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		return CategorytoDTO(cat);
	}

	
	private Categories DTOtoCategory(CategoryDTO cat)
	{
		Categories categories =  modelmapper.map(cat, Categories.class);
		return categories;
	}
	
	private CategoryDTO CategorytoDTO(Categories cat)
	{
		CategoryDTO categoriesDTO = modelmapper.map(cat, CategoryDTO.class);
		return categoriesDTO;
	}

	

}
