/**
* @author Nagarjun Kepulu
 */
/***************************************************/

package com.oracle.ocsdm.api.enums;

public enum StatusCode {
    CODE_200(200, "200"),
    CODE_201(201, "201"),
    CODE_204(204, "204"),
    CODE_400(400, "400"),
    CODE_401(401, "401");

    public final int code;
    public final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

/*    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }*/
}
