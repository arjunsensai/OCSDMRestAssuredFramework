package com.oracle.ocsdm.pojo.lombok;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
/* Implement with Builder */
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Session {
    @JsonProperty
    String loginSessionId;
}
