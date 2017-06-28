package pl.edu.agh.handy.offers.converter;

import pl.edu.agh.handy.offers.dto.BaseDto;
import pl.edu.agh.handy.offers.model.BaseModel;

import java.io.IOException;

/**
 * Abstract converter for model conversion.
 *
 * Converts from model to DTO and from DTO to model
 */
public interface ModelConverter<T extends BaseDto, M extends BaseModel> {

    T modelToDto(M model);

    M dtoToModel(T dto) throws IOException;
}
