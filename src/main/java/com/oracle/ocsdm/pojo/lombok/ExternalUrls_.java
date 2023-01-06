/**
* @author Nagarjun Kepulu
 */

/***************************************************/


package com.oracle.ocsdm.pojo.lombok;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalUrls_ {

    @JsonProperty("oracle")
    private String oracle;

    @JsonProperty("oracle")
    public String getoracle() {
        return oracle;
    }

    @JsonProperty("oracle")
    public void setoracle(String oracle) {
        this.oracle = oracle;
    }

}
