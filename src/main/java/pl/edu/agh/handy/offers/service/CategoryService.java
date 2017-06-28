package pl.edu.agh.handy.offers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.handy.offers.converter.CategoryConverter;
import pl.edu.agh.handy.offers.dto.CategoryDto;
import pl.edu.agh.handy.offers.exception.CategoryNotEmpty;
import pl.edu.agh.handy.offers.exception.EntityNotFound;
import pl.edu.agh.handy.offers.model.Category;
import pl.edu.agh.handy.offers.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements DaoService<Category, CategoryDto> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private OfferService offerService;

    @Override
    public Category create(CategoryDto dto) {
        Category category = categoryConverter.dtoToModel(dto);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void delete(CategoryDto dto) throws EntityNotFound, CategoryNotEmpty {
        Category category = categoryRepository.getOne(Long.valueOf(dto.getId()));
        if (offerService.findByCategory(category).isEmpty()) {
            categoryRepository.delete(Long.valueOf(dto.getId()));
            return;
        }

        throw new CategoryNotEmpty("Category not present in database!");
    }

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryDto> categories = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            categories.add(categoryConverter.modelToDto(category));
        }
        return categories;
    }

    @Override
    public Category update(CategoryDto dto) throws EntityNotFound {
        Category category = categoryConverter.dtoToModel(dto);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public CategoryDto findById(long id) {
        return categoryConverter.modelToDto(categoryRepository.findById(id));
    }

    public Category findOne(long id) {
        return categoryRepository.findOne(id);
    }
}
