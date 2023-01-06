/**
 * @author Nagarjun Kepulu
 */

/***************************************************/

package com.oracle.ocsdm.listeners;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.*;

public class MethodInterceptor implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {

        System.out.println("----------------METHOD INTERCEPTOR: START--------------------------");
        System.out.println("methods: " + methods);

        List<IMethodInstance> result = new ArrayList<IMethodInstance>();

        // System.setProperty("testCategory", "BVT,Smoke");
        String testCategory = System.getProperty("testCategory");
        System.out.println("testCategory: " + testCategory);
        for (IMethodInstance method : methods) {
            Test testMethod = method.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
            Set<String> groups = new HashSet<String>();
            Collections.addAll(groups, testMethod.groups());
            // if (groups.contains("SMOKE")) {
            // if (groups.contains("BVT")) {
            if (groups.contains(testCategory)) {
                result.add(method);
            }
        }
        if (testCategory == null) {
            System.out.println(
                    "Either You are not running the test execution from Maven command line or you are not giving the property -D=testCategory");
            System.out.println("----------------METHOD INTERCEPTOR: END--------------------------");
            return methods;
        } else {
            return result;
        }

    }
}
