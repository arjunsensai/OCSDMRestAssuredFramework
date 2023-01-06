package com.oracle.ocsdm.pojo.lombok.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@FieldDefaults(makeFinal = true,level= AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Nf {
        @JsonProperty
         Groups groups;
         String groupType;
         boolean hidden;
         String id;
         String name;
         NfCategory nfCategory;
         NfType nfType;
         String parentGroupFullName;
         String parentGroupId;
}
