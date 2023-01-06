/**
 * @author Nagarjun Kepulu
 */

/***************************************************/


package com.oracle.ocsdm.pojo.lombok.admin;

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
@FieldDefaults(makeFinal = true,level=AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionResponse {
    //Response
    @JsonProperty
    String userName;
    @JsonProperty
    int idleTimeout;
    @JsonProperty
    String serverInfo;
    @JsonProperty
    String sessionId;
    @JsonProperty
    String userGroup;
    @JsonProperty
    String validUntil;
}
