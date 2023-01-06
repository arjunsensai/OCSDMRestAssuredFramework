package com.oracle.ocsdm.pojo.lombok.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@FieldDefaults(makeFinal = false,level= AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
/**
 * No need of private access modifier with every field
 *
 * private Boolean collaborative; Boolean collaborative;
 */
/* Implement without Builder */
@Data
//@Getter @Setter
/* Implement with Builder */
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriseResponse {
    public class group {
        @JsonProperty
        String id;
        @JsonProperty
        public String ipAddress;
        @JsonProperty
        public String name;
        @JsonProperty
        public String parentGroupFullName;
        @JsonProperty
        public String parentGroupId;
    }

    public class groups {
        @JsonProperty
        public EnterpriseResponse.group group;
    }

    public class nfCategory {
        @JsonProperty
        public String id;
        @JsonProperty
        public String name;
        @JsonProperty
        public String product;
        @JsonProperty
        public String vendor;
    }

    public class nfType {
        @JsonProperty
        public String name;
    }

    public class nf {

        @JsonProperty
        public EnterpriseResponse.groups groups;
        @JsonProperty
        public String groupType;
        @JsonProperty
        public boolean hidden;
        @JsonProperty
        public String id;
        @JsonProperty
        public String name;
        @JsonProperty
        public EnterpriseResponse.nfCategory nfCategory;
        @JsonProperty
        public EnterpriseResponse.nfType nfType;
        @JsonProperty
        public String parentGroupFullName;
        @JsonProperty
        public String parentGroupId;
    }

}
