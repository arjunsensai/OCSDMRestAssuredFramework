/**
 * @author Nagarjun Kepulu
 */

/***************************************************/

package com.oracle.ocsdm.listeners;

import com.oracle.ocsdm.constants.FrameworkConstants;
import com.oracle.ocsdm.utils.ConfigLoader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTests implements IRetryAnalyzer {

    private final int retries = 1;
    private int count = 0;

    @Override
    public boolean retry(ITestResult result) {

        boolean value = false;
        if (ConfigLoader.getInstance().getRetryFailedTests().equalsIgnoreCase(FrameworkConstants.getYes())) {
            if (count < retries) {
                count++;
                return true;
            }
        }
        return value;
    }
}
