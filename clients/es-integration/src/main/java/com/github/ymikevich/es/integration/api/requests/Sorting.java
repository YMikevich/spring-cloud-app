package com.github.ymikevich.es.integration.api.requests;

import com.github.ymikevich.es.integration.api.requests.validation.ValidateEnumValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class Sorting {
    private String fieldName = "tweetId";
    @ValidateEnumValue(message = "Direction must be ASC or DESC", enumClass = Sort.Direction.class)
    private String direction = Sort.Direction.DESC.name();
}
