/**
* @author Nagarjun Kepulu
 */

/***************************************************/


package com.oracle.ocsdm.pojo.lombok.deviceGroupId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@FieldDefaults(makeFinal = false,level=AccessLevel.PUBLIC)
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
public class DeviceGroupIdResponse {


	@JsonProperty
	String fullName;
	@JsonProperty
	String id;
	@JsonProperty
	boolean isHidden;
	@JsonProperty
	String name;
	@JsonProperty
	String parentGroupId;
	
}
