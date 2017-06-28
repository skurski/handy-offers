package pl.edu.agh.handy.offers.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Common attributes for every model object.
 */
public class BaseModel implements Serializable {

    private LocalDateTime createDate;

}
