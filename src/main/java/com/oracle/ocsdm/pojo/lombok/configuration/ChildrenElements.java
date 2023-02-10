package com.oracle.ocsdm.pojo.lombok.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@FieldDefaults(makeFinal = true,level= AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChildrenElements {

    String elementTypePath;

    @JsonProperty
    ChildrenElement childrenElement;

    List<Attributes> attributes;

}
