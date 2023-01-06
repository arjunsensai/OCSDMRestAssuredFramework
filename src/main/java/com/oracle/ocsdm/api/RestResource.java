/**
 * @author Nagarjun Kepulu
 */

/***************************************************/

package com.oracle.ocsdm.api;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.oracle.ocsdm.constants.FrameworkConstants;
import com.oracle.ocsdm.reports.ExtentLogger;
import com.oracle.ocsdm.utils.ConfigLoader;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.PrintStream;
import java.io.StringWriter;

import static io.restassured.RestAssured.given;

public class RestResource {

/*	public static Response post(String path, Object payLoad, String loginSessionId) {

		StringWriter writerRequest;
		PrintStream captor;
		writerRequest = new StringWriter();
		captor = new PrintStream(new WriterOutputStream(writerRequest), true);

		//String User_ID = "x0308yuzh4ykcgbei7wypx4xw";
		Response response =
				given(SpecBuilder.getRequestSpec()).
						// body(payLoad).
								body(payLoad).
						//header("Authorization", "Bearer "+accessToken).
								headers("Content-Type", "application/json").
						headers("Accept", "application/json").
						headers("Cookie", "JSESSIONID="+loginSessionId).
						filter(new RequestLoggingFilter(captor)).
						when().
						//post("users/"+User_ID+"/playlists").
								post(path).
						then().
						spec(SpecBuilder.getResponseSpec()).
						extract().response();

		printDetailsInExtentReport(writerRequest, response);
//
//		SessionRequest loginResponse= response.getBody().as(SessionRequest.class);
//		String token = loginResponse.getSessionId();

		return response;
	}*/


    public static Response post(String path, Object payLoad, String loginSessionId) {

        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);
        System.out.println(captor);
        Response response =
                given(SpecBuilder.getRequestSpecs()).
                        // body(payLoad).
                                body(payLoad).
                        //header("Authorization", "Bearer "+accessToken).
                                headers("Content-Type", "application/json").
                        headers("Accept", "application/json").
                        headers("Cookie", "JSESSIONID=" + loginSessionId).
                        filter(new RequestLoggingFilter(captor)).
                        when().
                        //post("users/"+User_ID+"/playlists").
                                post(path).
                        then().
                        spec(SpecBuilder.getResponseSpec()).
                        extract().response();
        printDetailsInExtentReport(writerRequest, response);
//
//		SessionRequest loginResponse= response.getBody().as(SessionRequest.class);
//		String token = loginResponse.getSessionId();
        return response;
    }


    public static Response post(String path, Object payLoad) {

        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);

        //String User_ID = "x0308yuzh4ykcgbei7wypx4xw";
        Response response =
                given(SpecBuilder.getRequestSpecs()).
                        // body(payLoad).
                                body(payLoad).
                        //header("Authorization", "Bearer "+accessToken).
                                headers("Content-Type", "application/json").
                        headers("Accept", "application/json").
                        filter(new RequestLoggingFilter(captor)).
                        when().
                        //post("users/"+User_ID+"/playlists").
                                post(path).
                        then().
                        spec(SpecBuilder.getResponseSpec()).
                        extract().response();

        printDetailsInExtentReport(writerRequest, response);
//
//		SessionRequest loginResponse= response.getBody().as(SessionRequest.class);
//		String token = loginResponse.getSessionId();

        return response;
    }

    public static Response get(String path, String loginSessionId) {

        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);

        Response response =
                given(SpecBuilder.getRequestSpec()).
                        //header("Authorization", "Bearer "+accessToken).
                                headers("Content-Type", "application/json").
                        headers("Cookie", "JSESSIONID=" + loginSessionId).
                        headers("Accept", "application/json").
                        filter(new RequestLoggingFilter(captor)).
                        when().
                        //get("playlists/"+Playlist_ID).
                                get(path).
                        then().
                        spec(SpecBuilder.getResponseSpec()).
                        extract().
                        response();

        printDetailsInExtentReport(writerRequest, response);
        return response;
    }

    public static Response put(String path, Object payLoad, String loginSessionId) {

        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);

        //String User_ID = "x0308yuzh4ykcgbei7wypx4xw";
        Response response =
                given(SpecBuilder.getRequestSpecs()).
                        // body(payLoad).
                                body(payLoad).
                        //header("Authorization", "Bearer "+accessToken).
                                headers("Content-Type", "application/json").
                        headers("Accept", "application/json").
                        headers("Cookie", "JSESSIONID=" + loginSessionId).
                        filter(new RequestLoggingFilter(captor)).
                        when().
                        //post("users/"+User_ID+"/playlists").
                                put(path).
                        then().
                        spec(SpecBuilder.getResponseSpec()).
                        extract().response();

        printDetailsInExtentReport(writerRequest, response);
//
//		SessionRequest loginResponse= response.getBody().as(SessionRequest.class);
//		String token = loginResponse.getSessionId();

        return response;
    }

    public static Response put(String path, Object payLoad) {

        StringWriter writerRequest;
        PrintStream captor;
        writerRequest = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writerRequest), true);

        Response response =
                given(SpecBuilder.getRequestSpec()).
                        body(payLoad).
                        //header("Authorization", "Bearer "+accessToken).
                                filter(new RequestLoggingFilter(captor)).
                        when().
                        //put("playlists/"+playlistID).
                                put(path).
                        then().
                        //spec(getResponseSpec()).
                        //log().all().
                                extract().response();

        printDetailsInExtentReport(writerRequest, response);
        return response;
    }

    private static void printDetailsInExtentReport(StringWriter writer, Response response) {
        if (ConfigLoader.getInstance().getRequestDetailsInReports().equalsIgnoreCase(FrameworkConstants.getYes())) {
            ExtentLogger.info("<details><summary><i><font color=black> Request details: </font></i>" + "</summary>"
                    + "<pre>" + writer.toString() + "</pre>" + "</details> \n");
            ExtentLogger.info("<details><summary><i><font color=black> Response details: </font></i>" + "</summary>"
                    + "<pre>" + response.asString() + "</pre>" + "</details> \n");
            ExtentLogger.info(MarkupHelper.createCodeBlock(response.asString(), CodeLanguage.JSON));
        }
    }

}