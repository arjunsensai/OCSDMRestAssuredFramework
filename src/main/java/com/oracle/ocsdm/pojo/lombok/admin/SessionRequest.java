/**
 * @author Nagarjun Kepulu
 */

/***************************************************/


package com.oracle.ocsdm.pojo.lombok.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;


@Data
@Builder
@Jacksonized
@FieldDefaults(makeFinal = true,level=AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionRequest {
    //Request
    @JsonProperty
    String userName;
    @JsonProperty
    String password;
}
