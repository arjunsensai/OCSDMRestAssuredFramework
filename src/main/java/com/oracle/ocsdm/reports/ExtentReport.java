/**
 * @author Nagarjun Kepulu
 * Tutor: Amuthan Sakthivel (https://www.testingminibytes.com/)
 */

/***************************************************/


package com.oracle.ocsdm.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.oracle.ocsdm.api.enums.AuthorType;
import com.oracle.ocsdm.api.enums.CategoryType;
import com.oracle.ocsdm.constants.FrameworkConstants;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.oracle.ocsdm.constants.FrameworkConstants.*;

public final class ExtentReport {

    private static ExtentReports extent;

    private ExtentReport() {
    }

    public static void initReports() {
        if (Objects.isNull(extent)) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportFilePath());
            /*
             * .viewConfigurer() .viewOrder() .as(new ViewName[] { ViewName.DASHBOARD,
             * ViewName.TEST, //ViewName.TAG, ViewName.CATEGORY, ViewName.AUTHOR,
             * ViewName.DEVICE, ViewName.EXCEPTION, ViewName.LOG }) .apply();
             */

            /*
             * You can even update the view of the ExtentRerport - Whta do you want to you
             * first, you can prioritize
             */
            /*
             * ExtentSparkReporter spark = new
             * ExtentSparkReporter(REPORTS_SPARK_CUSTOMISED_HTML).viewConfigurer().viewOrder
             * () .as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY
             * }).apply();
             */
            extent.attachReporter(spark);

            // spark.config().setEncoding("utf-8");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle(FrameworkConstants.getProjectName() + " - ALL");
            spark.config().setReportName(FrameworkConstants.getProjectName() + " - ALL");

//			extent.setSystemInfo("Organization", "Oracle");
//			extent.setSystemInfo("Employee", "Nagarjun Kepulu");
//			extent.setSystemInfo("Domain", "Engineering (IT - Software)");
//			extent.setSystemInfo("Skill", "Test Automation Engineer");
            extent.setSystemInfo("Organization", "Oracle");
            extent.setSystemInfo("Employee",
                    "<b> Nagarjun Kepulu </b>" + " " + ICON_SOCIAL_LINKEDIN + " " + ICON_SOCIAL_GITHUB);
            extent.setSystemInfo("Domain", "Engineering (IT - Software)" + "  " + ICON_LAPTOP);
            extent.setSystemInfo("Skill", "Test Automation Engineer");
        }
    }

    public static void flushReports() {

        if (Objects.nonNull(extent)) {
            extent.flush();
        }

        ExtentManager.unload();
        try {
            Desktop.getDesktop().browse(new File(FrameworkConstants.getExtentReportFilePath()).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTest(String testCaseName) {
        ExtentManager.setExtentTest(extent.createTest(testCaseName));
    }

    synchronized public static void addAuthors(AuthorType[] authors) {
        for (AuthorType author : authors) {
            ExtentManager.getExtentTest().assignAuthor(author.toString());
        }
    }

    synchronized public static void addCategories(CategoryType[] categories) {
        for (CategoryType category : categories) {
            ExtentManager.getExtentTest().assignCategory(category.toString());
        }
    }

}
