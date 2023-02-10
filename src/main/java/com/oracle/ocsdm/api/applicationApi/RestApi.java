/**
 * @author Nagarjun Kepulu
 */

/***************************************************/

package com.oracle.ocsdm.api.applicationApi;

import com.oracle.ocsdm.api.RestResource;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class RestApi {
    @Step
    public static Response post(String path, Object payload) {
        return RestResource.post(path, payload);
    }

    public static Response post(String path, String loginSessionId) {
        return RestResource.post(path, loginSessionId);
    }

    public static Response post(String path, Object payload, String loginSessionId) {
        return RestResource.post(path, payload, loginSessionId);
    }

    public static Response get(String path, String loginSessionId) {
        return RestResource.get(path, loginSessionId);
    }

    public static Response put(String path, Object payload, String loginSessionId) {
        return RestResource.put(path, payload, loginSessionId);
    }

    public static Response delete(String path, String loginSessionId) {
        return RestResource.get(path, loginSessionId);
    }
}
