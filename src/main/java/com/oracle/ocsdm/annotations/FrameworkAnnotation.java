/**
* @author Nagarjun Kepulu
 * Tutor: Amuthan Sakthivel (https://www.testingminibytes.com/)
 */

/***************************************************/


package com.oracle.ocsdm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.oracle.ocsdm.api.enums.AuthorType;
import com.oracle.ocsdm.api.enums.CategoryType;

//This is a Custom Annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FrameworkAnnotation {

	// This is not a method
	AuthorType[] author();

	// public String[] category();
	//AuthorType[] category();

	CategoryType[] category();
}
