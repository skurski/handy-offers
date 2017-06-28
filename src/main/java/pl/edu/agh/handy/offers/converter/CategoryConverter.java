package pl.edu.agh.handy.offers.converter;

import org.springframework.stereotype.Component;
import pl.edu.agh.handy.offers.dto.CategoryDto;
import pl.edu.agh.handy.offers.model.Category;

@Component
public class CategoryConverter implements ModelConverter<CategoryDto, Category> {

    @Override
    public CategoryDto modelToDto(Category model) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(String.valueOf(model.getId()));
        categoryDto.setName(model.getName());
        return categoryDto;
    }

    @Override
    public Category dtoToModel(CategoryDto dto) {
        Category category = new Category();
        if (dto.getId() != null) {
            category.setId(Long.valueOf(dto.getId()));
        }
        category.setName(dto.getName());
        return category;
    }
}
