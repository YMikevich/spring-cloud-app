package com.github.ymikevich.es.integration.api.requests;

import com.github.ymikevich.es.integration.api.requests.validation.ValidateEnumValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Sorting {
    @NotBlank
    private String fieldName;
    @NotBlank
    @ValidateEnumValue(message = "Direction must be ASC or DESC", enumClass = Sort.Direction.class)
    private String direction;
}
