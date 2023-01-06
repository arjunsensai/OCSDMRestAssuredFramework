package com.oracle.ocsdm.pojo.lombok.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@FieldDefaults(makeFinal = true,level= AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NfCategory {
        String id;
        @JsonProperty
        String name;
        @JsonProperty
        String product;
        @JsonProperty
        String vendor;
    }

