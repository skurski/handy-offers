package pl.edu.agh.handy.offers.dto;

/**
 * Created by psk on 05.05.17.
 */
public class CategoryDto extends BaseDto {

    private String name;

    public CategoryDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
