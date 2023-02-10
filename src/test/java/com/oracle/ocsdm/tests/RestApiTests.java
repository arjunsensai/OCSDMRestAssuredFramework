/**
 * @author Nagarjun Kepulu
 */

/***************************************************/

package com.oracle.ocsdm.tests;

import com.oracle.ocsdm.annotations.FrameworkAnnotation;
import com.oracle.ocsdm.api.Path;
import com.oracle.ocsdm.api.applicationApi.RestApi;
import com.oracle.ocsdm.api.enums.AuthorType;
import com.oracle.ocsdm.api.enums.CategoryType;
import com.oracle.ocsdm.api.enums.StatusCode;
import com.oracle.ocsdm.constants.FrameworkConstants;
import com.oracle.ocsdm.pojo.lombok.admin.SessionRequest;
import com.oracle.ocsdm.pojo.lombok.admin.SessionResponse;
import com.oracle.ocsdm.pojo.lombok.commons.DeviceActionRequest;
import com.oracle.ocsdm.pojo.lombok.configuration.ConfigElement;
import com.oracle.ocsdm.pojo.lombok.configuration.DeviceAssociation;
import com.oracle.ocsdm.pojo.lombok.deviceGroupId.DeviceGroupIdRequest;
import com.oracle.ocsdm.pojo.lombok.inventory.DeviceGroupRequest;
import com.oracle.ocsdm.pojo.lombok.inventory.NfCategory;
import com.oracle.ocsdm.pojo.lombok.inventory.Parameter;
import com.oracle.ocsdm.utils.ExcelUtils;
import com.oracle.ocsdm.utils.VerificationManager;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.oracle.ocsdm.utils.BuildRequestPOJO.buildMainElementRequestPOJO902;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Oracle OCSDM")
@Feature("REST API")
public class RestApiTests extends _BaseTest {

    public static String loginSessionId;
    static String deviceGroupId = "";
    private final String deviceGroupID = "ID2";
    private final String deviceID = "ID6"; //ObjectID under sd157 after expansion in Device/Config Manager

    @Story("SessionRequest to OCSDM")
    @Link("http://localhost:8080")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("this is the description - From allure")
    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to login to OCSDM - Description from TestNG")
    public void ShouldBeAbleToLoginToSDM() {
        SessionRequest requestSessionRequest = loginBuilder("admin", "admin");
        Response response = RestApi.post(Path.ADMIN + Path.LOGIN, requestSessionRequest, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should be able to login");
        SessionResponse sessionResponseBody = response.getBody().as(SessionResponse.class);
        loginSessionId = sessionResponseBody.sessionId;
        System.out.println("This is Cookie Parameter for further requests: JSESSIONID=" + loginSessionId);
        assertThat(sessionResponseBody.userName, equalTo(requestSessionRequest.userName));
    }

 /*   @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration")
    public void ShouldBeAbleToLoadDeviceConf() {
        //Response response = RestApi.get(Path.CONFIGURATION + Path.DEVICES + "/" + deviceID + Path.LOAD_CONFIGURATION, loginSessionId);
        //assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to load device configuration");
    }
*/


    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dataProvider = "configDataProvider", dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "Should Be Able To Add Configuration Translation Rules")
    public void ShouldBeAbleToAddConfigurationPhyInterface() throws IOException {
        ExcelUtils excelUtils = new ExcelUtils();

        String[][] arrObj = excelUtils.getExcelData("D:\\repo\\OCSDMRestAssuredFramework\\src\\main\\java\\com\\oracle\\ocsdm\\utils\\configData.xlsx");
        //public void ShouldBeAbleToAddConfigurationPhyInterface(String[] key, String[] value) {

            // String[] keys = getDatafromXL(){};
//        String[] values = getDatafromXL(){};
//        String DependsOn = getDatafromXL(){};


        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
       /* String[] key = {
                "MainElement",
                "name",
                "operationType",
                "port",
                "slot"
        };
        String[] value = {
                "phyInterfaceConfig",
                "kepulu-phyInterfaceConfig",
                "Media",
                "2",
                "2"
        };*/
        ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration: TranslationRules");
    }

    /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
             category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
     @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationPhyInterface"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "Should Be Able To Add Configuration Translation Rules")
     public void ShouldBeAbleToAddConfigurationNetworkInterface() {
         //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
         String[] key = {
                 "MainElement",
                 "name",
                 "subPortId",
                 "ipAddress",
                 "utilityAddress",
                 "secondUtilityAddress"
         };
         String[] value = {
                 "networkInterface",
                 "kepulu-phyInterfaceConfig",
                 "2",
                 FakerUtils.generateIPv4Address(),
                 FakerUtils.generateIPv4Address(),
                 FakerUtils.generateIPv4Address()
         };
         ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
         Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
         assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration: TranslationRules");
     }

     @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
             category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
     @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationNetworkInterface"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "Should Be Able To Add Configuration Translation Rules")
     public void ShouldBeAbleToAddConfigurationRealmConfig() {
         //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
         String[] key = {
                 "MainElement",
                 "id",
                 "SubElement",
                 "name",
                 "subPortId",
                 "family"
         };
         String[] value = {
                 "realmConfig",
                 "kepulu-realmConfig",
                 "realmConfig/networkInterfaceId",
                 "kepulu-phyInterfaceConfig",
                 "2",
                 "4"
         };
         ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
         Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
         assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration: TranslationRules");
     }


     @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
             category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
     @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceConf"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "Should Be Able To Add Configuration Translation Rules")
     public void ShouldBeAbleToAddConfigurationPhyInterfaceIPV6() {
         //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
         String[] key = {
                 "MainElement",
                 "name",
                 "operationType",
                 "port",
                 "slot"
         };
         String[] value = {
                 "phyInterfaceConfig",
                 "kepulu-phyInterfaceConfigIPv6",
                 "Media",
                 "1",
                 "1"
         };
         ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
         Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
         assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration: TranslationRules");
     }

     @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
             category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
     @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationPhyInterfaceIPV6"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "Should Be Able To Add Configuration Translation Rules")
     public void ShouldBeAbleToAddConfigurationNetworkInterfaceIPV6() {
         //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
         String[] key = {
                 "MainElement",
                 "name",
                 "subPortId",
                 "ipAddress",
                 "utilityAddress",
                 "secondUtilityAddress"
         };
         String[] value = {
                 "networkInterface",
                 "kepulu-phyInterfaceConfigIPv6",
                 "2",
                 FakerUtils.generateIPv6Address(),
                 FakerUtils.generateIPv6Address(),
                 FakerUtils.generateIPv6Address()
         };
         ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
         Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
         assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration: TranslationRules");
     }

     @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
             category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
     @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationNetworkInterfaceIPV6"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "Should Be Able To Add Configuration Translation Rules")
     public void ShouldBeAbleToAddConfigurationRealmConfigIPV6() {
         //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
         String[] key = {
                 "MainElement",
                 "id",
                 "SubElement",
                 "name",
                 "subPortId",
                 "family"
         };
         String[] value = {
                 "realmConfig",
                 "kepulu-realmConfigIPv6",
                 "realmConfig/networkInterfaceId",
                 "kepulu-phyInterfaceConfigIPv6",
                 "2",
                 "6"
         };
         ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
         Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
         assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration: TranslationRules");
     }
 */
    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dataProvider = "configDataProvider", dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationSteeringPool(String[] key, String[] value) {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
     /*   String[] key = {
                "MainElement",
                "startPort",
                "realmID",
                "ipAddress",
                "endPort",
                "port-allocation-strategy"
        };
        String[] value = {
                "steeringPool",
                "0",
                "kepulu-realmConfig",
                FakerUtils.generateIPv6Address(),
                "0",
                "single"
        };*/

        ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration Changes Sti_Configuration");

    }
/*
    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceConf"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "Should Be Able To Add Configuration Translation Rules")
    public void ShouldBeAbleToAddConfigurationTranslationRules() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] key = {
                "MainElement",
                "id",
                "description",
                "inputType",
                "inputValue",
                "outputType",
                "outputValue"
        };
        String[] value = {
                "translationRules",
                "kepulu-translationRules",
                "kepulu-translationRules-description",
                "calling-header",
                "input-header-value",
                "called-header",
                "output-header-value"
        };
        ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration: TranslationRules");
    }*/


   /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationSteeringPool"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationSessionTranslationWithSessionTranslationRule() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] key = {
                "MainElement",
                "id",
                "SubElement",
                "state",
                "ruleId",
                "mandatory"};
        String[] value = {
                "sessionTranslation",
                "kepulu-sessionTranslation",
                "sessionTranslation/sessionTransRule",
                "enabled",
                "kepulu-translationRules",
                "enabled"};


        ConfigElement configElement = buildMainElementRequestPOJO902(key, value);

        // ConfigElement configElement = buildSubElementRequestPOJO("realmConfig", "realmConfig/networkInterfaceId", name, value, 1, 3);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to load device configuration: SessionTranslation With SessionTranslationRule");
    }


    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationNetworkInterface"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "Should Be Able To Add Configuration Translation Rules")
    public void ShouldBeAbleToUpdateConfigurationRealmConfig() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] key = {
                "MainElement",
                "id",
                "steeringPoolLowerThreshold",
                "steeringPoolThreshold",
                "steeringPoolAlarmMonitoringTime",
                "SubElement",
                "name",
                "subPortId",
                "family",
                "SubElement",
                "inSessionTranslationId",
                "state",
                "SubElement",
                "inSessionTranslationId",
                "state"
        };
        String[] value = {
                "realmConfig",
                "kepulu-realmConfig",
                "35",
                "55",
                "555",
                "realmConfig/networkInterfaceId",
                "kepulu-phyInterfaceConfig",
                "2",
                "4",
                "realmConfig/InSessionTranslationList",
                "kepulu-sessionTranslation",
                "enabled",
                "realmConfig/OutSessionTranslationList",
                "kepulu-sessionTranslation",
                "enabled"
        };
        ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.UPDATE, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration: TranslationRules");
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SCZ920, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationRealmConfig"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationSessionAgent() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] key = {
                "MainElement",
                "startPort",
                "realmID",
                "ipAddress",
                "endPort",
                "port-allocation-strategy"
        };
        String[] value = {
                "steeringPool",
                "0",
                "kepulu-realmConfig",
                FakerUtils.generateIPv6Address(),
                "0",
                "single"
        };

        ConfigElement configElement = buildMainElementRequestPOJO902(key, value);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration Changes Sti_Configuration");

    }

/*

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceConf"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationLocalPolicyWithSubElements() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] key = {
                "MainElement",
                "description",
                "state",

                "SubElement",
                "name",

                "SubElement",
                "addr",

                "SubElement",
                "lookup",
                "nextHop",

                "SubElement",
                "addr"
                };

        String[] value = {
                "localPolicy",
                "value",
                "enabled",

                "localPolicy/sourceRealm",
                "core-diameter",

                "localPolicy/from",
                "1.1.1.1",

                "localPolicy/policyAttribute",
                "single",
                "destination",

                "localPolicy/to",
                "2.2.2.2",
        };

        ConfigElement configElement = buildMainElementRequestPOJO902(key, value);

        // ConfigElement configElement = buildSubElementRequestPOJO("realmConfig", "realmConfig/networkInterfaceId", name, value, 1, 3);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to load device configuration: SessionTranslation With SessionTranslationRule");
    }
*/


   /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationSessionTranslationWithSessionTranslationRule"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to delete a configuration element")
    public void ShouldBeAbleToDeleteConfigChanges() {

        Response response = RestApi.delete(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_CHANGES, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to delete Configuration Changes");

    }
/*

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceConf"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationSubElementNetworkInterfaceToRealmConfig() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] name = {
                "id",
                "name",
                "subPortId",
                "family"};
        String[] value = {
                "binay-realm",
                "M01",
                "0",
                "4"};

        ConfigElement configElement = buildMainElementRequestPOJO("realmConfig", "realmConfig/networkInterfaceId", name, value, 1, 3);

        // ConfigElement configElement = buildSubElementRequestPOJO("realmConfig", "realmConfig/networkInterfaceId", name, value, 1, 3);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to load device configuration");

    }



    }*/
/*      @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to add ESBC Device to OCSDM - Description from TestNG")
    public void ShouldBeAbleToAddESBCDevice() {

        List<Parameter> list = new ArrayList<>();
        Parameter field1 = Parameter.builder().name("primary.ip")
                .value("10.184.17.195")
                .build();
        Parameter field2 = Parameter.builder().name("snmp.community.name")
                .value("raghav")
                .build();
        Parameter field3 = Parameter.builder().name("snmp.port")
                .value("161")
                .build();
        Parameter field4 = Parameter.builder().name("username")
                .value("admin")
                .build();
        Parameter field5 = Parameter.builder().name("password")
                .value("abcd1234!")
                .build();
        list.add(field1);
        list.add(field2);
        list.add(field3);
        list.add(field4);
        list.add(field5);
        NfCategory cat = NfCategory.builder().product("ESBC/ECB").name("Enterprise Edge & Core").vendor("Oracle").build();
        NfType nfType = NfType.builder().name("ESBC").build();
        NfConfig con = NfConfig.builder().name("sd195").parentGroupFullName("Home").nfCategory(cat).nfType(nfType).parameters(list).build();
        Response response = RestApi.post(Path.INVENTORY + Path.NETWORK_FUNCTION_MANAGEMENT + Path.NETWORK_FUNCTIONS, con, loginSessionId);
        System.out.println("deviceGroupID is " + deviceGroupID);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add ESBC Device to OCSDM");
        NfConfigResponse nfConfigResponse = response.getBody().as(NfConfigResponse.class);
        System.out.println(nfConfigResponse);
//        System.out.println("deviceGroupID is " + deviceGroupID);

        //Assert is Failing: assertError(response.as(Error.class), StatusCode.CODE_400);
    }*/
/*
    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddESBCDevice"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to associate specific device to OCSDM - Description from TestNG")
    public void ShouldBeAbleToAssociateDeviceToSDM() {
        DeviceAssociation request = DeviceAssociationBuilder("esbc183", "sd183");
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICES, request, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should be able to associate specific device");
        //SessionResponse sessionResponseBody = response.getBody().as(SessionResponse.class);
        //assertThat(sessionResponseBody.nfName, equalTo(request.nfName));
    }*/
/*
    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToLoadDeviceToSDM() {
        Response response = RestApi.get(Path.CONFIGURATION + Path.DEVICES + "/" + deviceID + Path.LOAD_CONFIGURATION, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to load device configuration to OCSDM");
    }*/

/*    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigElementSurrogateToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to apply an action on the specified managed device to OCSDM")
    public void ShouldBeAbleToRetrieveConfigChangesToSDM(){

        Response response = RestApi.get(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/"+deviceID + Path.CONFIGURATION_CHANGES, loginSessionId);

        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to retrieve configuration changes to OCSDM");

    }*/


   /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationElementTranslationRules() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] name = {
                "outputValue",
                "inputValue",
                "description",
                "outputType",
                "inputType",
                "id"};
        String[] value = {
                "$01$02",
                "(.{0})(.*)$",
                "this is translationRules by ",
                "calling-header",
                "called-header",
                "kepulu-translationRules"};

        ConfigElement configElement = buildSubElementRequestPOJO("translationRules", null, name, value, 6, 0);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
    }

    public ConfigElement buildSubElementRequestPOJO(String elementTypePath, String subElementTypePath, String[] name, String[] value, int elementAttributeCount, int subElementAttributeCount) {
        List<Attribute> elementAttributeList = new ArrayList<>();
        for (int i = 0; i < (elementAttributeCount); i++) {
            Attribute elementAttribute = Attribute.builder().name(name[i])
                    .value(value[i])
                    .build();
            elementAttributeList.add(elementAttribute);
        }
        List<Attribute> subElementAttributeList = new ArrayList<>();
        if (subElementTypePath != null) {
            for (int i = elementAttributeCount; i <= (subElementAttributeCount); i++) {
                Attribute subElementAttribute = Attribute.builder().name(name[i])
                        .value(value[i])
                        .build();
                subElementAttributeList.add(subElementAttribute);
            }
        }
        Attributes elementAttributes = Attributes.builder().attribute(elementAttributeList).build();
       // Attributes subElementAttributes = Attributes.builder().attribute(subElementAttributeList).build();
       // ChildrenElement childrenElement = ChildrenElement.builder().elementTypePath(subElementTypePath).attributesList(subElementAttributes).build();
       // ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();
        ConfigElement configElement;
        configElement = ConfigElement.builder().elementTypePath(elementTypePath).attributesList(elementAttributes).build();

        //configElement = ConfigElement.builder().elementTypePath("realmConfig").attributesList((Attributes) attr).childrenElements().build();
        return configElement;
    }*/


   /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationElementNtpConfig() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] name = {
                "dnsRealm"
        };
        String[] value = {
                "abc"
        };

        ConfigElement configElement=  buildMainElementRequestPOJO("ntpConfig", null, name, value, 1, 0);


        //ConfigElement configElement = buildSubElementRequestPOJO("sessionAgent", null, name, value, 3, 0);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationElementStiServer() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] name = {
                "name",
                "httpClient",
                "stiAttest",
                "stiHeaderMappingRulesetName",
                "options"
        };
        String[] value = {
                "stiServer2",
                "httpClient1",
                "A",
                "sti-header-mapping-ruleset3",
                "options1"
        };

        ConfigElement configElement=  buildMainElementRequestPOJO("stiServer", null, name, value, 5, 0);

        //ConfigElement configElement = buildSubElementRequestPOJO("sessionAgent", null, name, value, 3, 0);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
    }
*/

/*
    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceConf"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationElementStiConfig() {
        //Make sure to follow the order:- First mention the Main Element attribute names/values followed by Sub Element
        String[] name = {
                "stiHeaderMappingRulesetName",
                "stiSignalingAttestInfoMandatory",
                "stiASCorrelationId",
                "reasonJsonSipTranslation"
        };
        String[] value = {
                null,
                "enabled",
                "enabled",
                "enabled"
        };

        ConfigElement configElement=  buildMainElementRequestPOJO("stiConfig", null, name, value, 4, 0);
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS + Path.ADD, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add Configuration Changes Sti_Configuration");

    }
*/

/*
    public ConfigElement buildSubElementRequestPOJO(String elementTypePath, String subElementTypePath, String[] name, String[] value, int elementAttributeCount, int subElementAttributeCount) {
        List<Attribute> elementAttributeList = new ArrayList<>();
        for (int i = 0; i < (elementAttributeCount); i++) {
            Attribute elementAttribute = Attribute.builder().name(name[i])
                    .value(value[i])
                    .build();
            elementAttributeList.add(elementAttribute);
        }
        List<Attribute> subElementAttributeList = new ArrayList<>();
        if (subElementTypePath != null) {
            for (int i = elementAttributeCount; i <= (subElementAttributeCount); i++) {
                Attribute subElementAttribute = Attribute.builder().name(name[i])
                        .value(value[i])
                        .build();
                subElementAttributeList.add(subElementAttribute);
            }
        }
        Attributes elementAttributes = Attributes.builder().attribute(elementAttributeList).build();
        Attributes subElementAttributes = Attributes.builder().attribute(subElementAttributeList).build();
        ChildrenElement childrenElement = ChildrenElement.builder().elementTypePath(subElementTypePath).attributes(subElementAttributes).build();
        ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();
        ConfigElement configElement;
        configElement = ConfigElement.builder().elementTypePath(elementTypePath).attributes(elementAttributes).childrenElements(childrenElements).build();
        return configElement;
    }*/

       /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to delete a specific Device Groups Information from OCSDM - Description from TestNG")
    public void ShouldBeAbleToDeleteSpecificDeviceGroupInfo(String loginSessionId, String deviceGroupID) {
        Response response = RestApi.delete(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICE_GROUPS+"/"+deviceGroupID, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to delete a specific Device Groups Information");
        //ShouldBeAbleToLockSpecificDevice(loginSessionId, deviceID);
    }

 /*   @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigurationSubElementNetworkInterfaceToRealmConfig"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to add Device Group - Description from TestNG")
    public void ShouldBeAbleToUpdateDevice() {
        DeviceActionRequest deviceActionRequest = deviceActionRequestBuilder("SaveActivateConfig");
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICES + "/" + deviceID + Path.ACTION, deviceActionRequest, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should is to add Device Group");
        //Assert is Failing: assertError(response.as(Error.class), StatusCode.CODE_400);
    }*/
 /*   @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigSubElementSurrogateToSDM(){

        List<Attribute> list = new ArrayList<>();
        Attribute field1 = Attribute.builder().name("prefix")
                .value("10.10.20.31/24")
                .build();
        list.add(field1);

        List<Attribute> list1 = new ArrayList<>();
        Attribute field2 = Attribute.builder().name("registerHost")
                .value("1.1.1.1")
                .build();
        Attribute field3 = Attribute.builder().name("registerUser")
                .value("register")
                .build();
        Attribute field4 = Attribute.builder().name("realmID")
                .value("qwqwqw")
                .build();
        Attribute field5 = Attribute.builder().name("contactUser")
                .value("user")
                .build();
        Attribute field6 = Attribute.builder().name("contactHost")
                .value("host")
                .build();
        Attribute field7 = Attribute.builder().name("customerRoute")
                .value("route")
                .build();

        list1.add(field2);
        list1.add(field3);
        list1.add(field4);
        list1.add(field5);
        list1.add(field6);
        list1.add(field7);


        Attributes attr = Attributes.builder().attribute(list).build();
        Attributes attri = Attributes.builder().attribute(list1).build();

        ParentElement parentElement = ParentElement.builder().elementTypePath("surrogateAgent").attributesList(attri).build();
        ChildrenElement childrenElement = ChildrenElement.builder().attributesList(attr).build();
        ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();

        ConfigElement configElement = ConfigElement.builder().elementTypePath("surrogateAgent/sourceIpPrefix")
                .parentElement(parentElement).childrenElements(childrenElements)
                .elementTypePath("surrogateAgent/sourceIpPrefix")
                .build();

        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS +Path.ADD, configElement, loginSessionId);

    }


    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigElementSurrogateToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to apply an action on the specified managed device to OCSDM")
    public void ShouldBeAbleToApplyAnActionToSDM(){

        DeviceAction deviceAction = DeviceAction.builder().type("SaveActivateConfig").build();
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICES + "/" + deviceID + Path.ACTION, deviceAction, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should be able to apply an action on the specified managed device");

    }*/

/*    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToAddConfigElementToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration")
    public void ShouldBeAbleToLoadDeviceConf(){
        Response response = RestApi.get(Path.CONFIGURATION + Path.DEVICES + "/"+deviceID + Path.LOAD_CONFIGURATION, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to load device configuration");
    }*/


/*   @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceConf"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration")
    public void ShouldBeAbleToDeleteConfElement(){

       List<Attribute> list = new ArrayList<>();
       Attribute field1 = Attribute.builder().name("prefix")
               .value("10.10.20.31/24")
               .build();
       list.add(field1);


       List<Attribute> list1 = new ArrayList<>();
       Attribute field2 = Attribute.builder().name("registerHost")
               .value("1.1.1.1")
               .build();
       Attribute field3 = Attribute.builder().name("registerUser")
               .value("register")
               .build();
       Attribute field4 = Attribute.builder().name("realmID")
               .value("qwqwqw")
               .build();
       Attribute field5 = Attribute.builder().name("contactUser")
               .value("user")
               .build();
       Attribute field6 = Attribute.builder().name("contactHost")
               .value("host")
               .build();
       Attribute field7 = Attribute.builder().name("customerRoute")
               .value("route")
               .build();

       list1.add(field2);
       list1.add(field3);
       list1.add(field4);
       list1.add(field5);
       list1.add(field6);
       list1.add(field7);

       Attributes attr = Attributes.builder().attribute(list).build();
       Attributes attri = Attributes.builder().attribute(list1).build();


       ParentElement parentElement = ParentElement.builder().elementTypePath("surrogateAgent").attributesList(attri).build();
       ChildrenElement childrenElement = ChildrenElement.builder().attributesList(attr).build();
       ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();

       ConfigElement configElement = ConfigElement.builder().elementTypePath("surrogateAgent/sourceIpPrefix")
               .parentElement(parentElement).childrenElements(childrenElements)
               .elementTypePath("surrogateAgent/sourceIpPrefix")
               .build();

        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS +Path.DELETE, configElement, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add ESBC Device to OCSDM");

       // NfConfigResponse nfConfigResponse = response.getBody().as(NfConfigResponse.class);


    }*/


    /*@FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to logout to OCSDM - Description from TestNG")
    public void ShouldBeAbleToLogoutToSDM() {
        Response response = RestApi.post(Path.ADMIN + Path.LOGOUT, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_204, "User should be able to logout");
        SessionResponse sessionResponseBody = response.getBody().as(SessionResponse.class);
        String logoutSessionId = sessionResponseBody.sessionId;
    }

     */
   /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to add ESBC Device to OCSDM - Description from TestNG")
    public void ShouldBeAbleToAddESBCDevice() {
        List<Parameter> list = new ArrayList<>();
        Parameter field1 = Parameter.builder().name("primary.ip")
                .value("10.184.17.195")
                .build();
        Parameter field2 = Parameter.builder().name("snmp.community.name")
                .value("raghav")
                .build();
        Parameter field3 = Parameter.builder().name("snmp.port")
                .value("161")
                .build();
        Parameter field4 = Parameter.builder().name("username")
                .value("admin")
                .build();
        Parameter field5 = Parameter.builder().name("password")
                .value("abcd1234!")
                .build();
        list.add(field1);
        list.add(field2);
        list.add(field3);
        list.add(field4);
        list.add(field5);
        NfCategory cat = NfCategory.builder().product("ESBC/ECB").name("Enterprise Edge & Core").vendor("Oracle").build();
        NfType nfType = NfType.builder().name("ESBC").build();
        NfConfig con = NfConfig.builder().name("sd195").parentGroupFullName("Home").nfCategory(cat).nfType(nfType).parameters(list).build();
        Response response = RestApi.post(Path.INVENTORY + Path.NETWORK_FUNCTION_MANAGEMENT + Path.NETWORK_FUNCTIONS, con, loginSessionId);
        System.out.println("deviceGroupID is " + deviceGroupID);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add ESBC Device to OCSDM");
        //Nf nf =
        NfConfigResponse nfConfigResponse = response.getBody().as(NfConfigResponse.class);
        System.out.println(nfConfigResponse);
//        System.out.println("deviceGroupID is " + deviceGroupID);

        //Assert is Failing: assertError(response.as(Error.class), StatusCode.CODE_400);
    }
    /*
    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to delete a configuration element")
    public void ShouldBeAbleToDeleteConfigElement(){

        List<Attribute> list = new ArrayList<>();
        Attribute field1 = Attribute.builder().name("id")
                .value("binay-realm111")
                .build();
        list.add(field1);

        List<Attribute> list1 = new ArrayList<>();
        Attribute field2 = Attribute.builder().name("name")
                .value("lo0")
                .build();
        Attribute field3 = Attribute.builder().name("subPortId")
                .value("0")
                .build();
        Attribute field4 = Attribute.builder().name("family")
                .value("4")
                .build();
        list1.add(field2);
        list1.add(field3);
        list1.add(field4);

        Attributes attributesList = Attributes.builder().attribute(list).build();
        Attributes attributes1 = Attributes.builder().attribute(list1).build();
        ChildrenElement childrenElement = ChildrenElement.builder().elementTypePath("realmConfig/networkInterfaceId").attributesList(attributes1).build();
        ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();
        ConfigElement configElement;
        configElement = ConfigElement.builder().elementTypePath("realmConfig").attributesList(attributesList).childrenElements(childrenElements).build();
        System.out.println("444444444444444444444444444444444444444");


        Response response = RestApi.post(Path.CONFIGURATION +Path.DEVICE_CONFIGURATIONS+ "/" + deviceID +Path.CONFIGURATION_ELEMENTS  + Path.DELETE, configElement, loginSessionId);

    }


    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to add configuration sub element to OCSDM - Description from TestNG")
    public void ShouldBeAbleToAddConfigSubElement(){

        List<Attribute> list = new ArrayList<>();
        Attribute field1 = Attribute.builder().name("id")
                .value("binay-realm111")
                .build();
        list.add(field1);

        List<Attribute> list1 = new ArrayList<>();
        Attribute field2 = Attribute.builder().name("registerHost")
                .value("SAHost10")
                .build();
        Attribute field3 = Attribute.builder().name("registerUser")
                .value("SARegUser10")
                .build();
        Attribute field4 = Attribute.builder().name("realmID")
                .value("binay-realm111")
                .build();
        Attribute field5 = Attribute.builder().name("contactUser")
                .value("SARegContactUser")
                .build();
        Attribute field6 = Attribute.builder().name("contactHost")
                .value("SARegContactHost")
                .build();
        Attribute field7 = Attribute.builder().name("customerRoute")
                .value("SA:KrishnaSA")
                .build();

        list1.add(field2);
        list1.add(field3);
        list1.add(field4);
        list1.add(field5);
        list1.add(field6);
        list1.add(field7);

        Attributes attr = Attributes.builder().attribute(list).build();
        Attributes attri = Attributes.builder().attribute(list1).build();
        Attribute attribute1 = Attribute.builder().name("prefix").value("10.10.20.31/24").build();
        Attributes attrib = Attributes.builder().attribute((List<Attribute>) attribute1).build();
        ChildrenElement childrenElement = ChildrenElement.builder().attributesList(attrib).elementTypePath("surrogateAgent/sourceIpPrefix").build();

        ParentElement parentElement = ParentElement.builder().elementTypePath("surrogateAgent").attributesList(attri).childrenElements(childrenElement).build();

        ConfigElement configElement;
        configElement = ConfigElement.builder().elementTypePath("surrogateAgent/sourceIpPrefix").attributesList(attrib).parentElement(parentElement).build();


        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS +Path.ADD, configElement, loginSessionId);
    }


*/


   /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to retreive configuration changes for the device loaded")
    public void ShouldBeAbleToRetreiveConfigurationChanges(){
        Response response = RestApi.get(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/"+deviceID + Path.CONFIGURATION_CHANGES, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to retreive configuration changes for the device loaded");
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToRetreiveConfigurationChanges"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to add Device Group - Description from TestNG")
    public void ShouldBeAbleToUpdateDevice() {
        DeviceActionRequest deviceActionRequest = deviceActionRequestBuilder("SaveActivateConfig");
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICES + "/"+ deviceID + Path.ACTION, deviceActionRequest, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should is to add Device Group");
        //Assert is Failing: assertError(response.as(Error.class), StatusCode.CODE_400);
    }




/*    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoadDeviceToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to load device configuration to OCSDM")
    public void ShouldBeAbleToAddConfigurationElements(){

        List<Attribute> list = new ArrayList<>();
        Attribute field1 = Attribute.builder().name("id")
                .value("binay-realm111")
                .build();
        list.add(field1);

        List<Attribute> list1 = new ArrayList<>();
        Attribute field2 = Attribute.builder().name("name")
                .value("lo0")
                .build();
        Attribute field3 = Attribute.builder().name("subPortId")
                .value("0")
                .build();
        Attribute field4 = Attribute.builder().name("family")
                .value("4")
                .build();
        list1.add(field2);
        list1.add(field3);
        list1.add(field4);
        Attributes attributesList = Attributes.builder().attribute(list).build();
        Attributes attributes1 = Attributes.builder().attribute(list1).build();
        ChildrenElement childrenElement = ChildrenElement.builder().elementTypePath("realmConfig/networkInterfaceId").attributesList(attributes1).build();
        ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();
        ConfigElement configElement;
        configElement = ConfigElement.builder().elementTypePath("realmConfig").attributesList(attributesList).childrenElements(childrenElements).build();
        System.out.println("444444444444444444444444444444444444444");
        Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" +deviceID+ Path.CONFIGURATION_ELEMENTS + Path.ADD , configElement, loginSessionId);
        System.out.println("DOES NOT WORK HERE, NEED TO INVESTIGATE");
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add ESBC Device to OCSDM");

    }*/



  /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to add ESBC Device to OCSDM - Description from TestNG")
    public void ShouldBeAbleToAddESBCDevice() {
        List<Parameter> list = new ArrayList<>();
        Parameter field1 = Parameter.builder().name("primary.ip")
                .value("10.184.17.195")
                .build();
        Parameter field2 = Parameter.builder().name("snmp.community.name")
                .value("vijay")
                .build();
        Parameter field3 = Parameter.builder().name("snmp.port")
                .value("161")
                .build();
        Parameter field4 = Parameter.builder().name("username")
                .value("admin")
                .build();
        Parameter field5 = Parameter.builder().name("password")
                .value("abcd1234!")
                .build();
        list.add(field1);
        list.add(field2);
        list.add(field3);
        list.add(field4);
        list.add(field5);
        NfCategory cat = NfCategory.builder().product("ESBC/ECB").name("Enterprise Edge & Core").vendor("Oracle").build();
        NfType nfType = NfType.builder().name("ESBC").build();
        NfConfig con = NfConfig.builder().name("sd195").parentGroupFullName("Home").nfCategory(cat).nfType(nfType).parameters(list).build();
        Response response = RestApi.post(Path.INVENTORY + Path.NETWORK_FUNCTION_MANAGEMENT + Path.NETWORK_FUNCTIONS, con, loginSessionId);
        System.out.println("deviceGroupID is " + deviceGroupID);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add ESBC Device to OCSDM");
        //Nf nf =
        System.out.println("@@@@@@@@@@@@@@@@" +response.getBody().as(Nf.class));
        System.out.println("deviceGroupID is " + deviceGroupID);

        //Assert is Failing: assertError(response.as(Error.class), StatusCode.CODE_400);
    }*/


/*    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"},groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to Display trap receivers - Description from TestNG")
    public void ShouldBeAbleToDisplayTrapReceivers() {

        Response response = RestApi.get(Path.FAULT + Path.TRAP_RECEIVERS, loginSessionId);
        System.out.println(RestApi.get(Path.FAULT + Path.TRAP_RECEIVERS, loginSessionId).then().extract().contentType());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to Display trap receivers");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+response.getHeaders());

    }*/

/*    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"},groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to Display trap receivers - Description from TestNG")
    public void ShouldBeAbleToRetrievealarms() {

        Response response = RestApi.get(Path.FAULT + Path.ALARMS, loginSessionId);
       System.out.println(RestApi.get(Path.FAULT + Path.ALARMS, loginSessionId).then().extract().contentType());
       assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to Display trap receivers");
    }*/





/*    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to add SBC Device to OCSDM - Description from TestNG")
    public void ShouldBeAbleToAddSBCDevice() {
        List<Parameter> list = new ArrayList<>();
        Parameter field1 = Parameter.builder().name("primary.ip")
                .value("10.184.17.195")
                .build();
        Parameter field2 = Parameter.builder().name("snmp.community.name")
                .value("vijay")
                .build();
        Parameter field3 = Parameter.builder().name("password")
                .value("abcd1234!")
                .build();
        Parameter field4 = Parameter.builder().name("username")
                .value("admin")
                .build();
        list.add(field1);
        list.add(field2);
        list.add(field3);
        list.add(field4);
        NfCategory nfCategory = NfCategory.builder().product("Session Delivery").name("SP Edge & Core").vendor("Oracle").build();
        NfType nfType = NfType.builder().name("Device").build();
        NfConfig nfConfig = NfConfig.builder().name("sd195").parentGroupFullName("Home").nfCategory(nfCategory).nfType(nfType).parameters(list).build();
        Response response = RestApi.post(Path.INVENTORY + Path.NETWORK_FUNCTION_MANAGEMENT + Path.NETWORK_FUNCTIONS, nfConfig, loginSessionId);
        //Nf nf =
        System.out.println("@@@@@@@@@@@@@@@@" +response.getBody().as(Nf.class));
        System.out.println("deviceGroupID is " + deviceGroupID);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to add ESBC Device to OCSDM");
        //Assert is Failing: assertError(response.as(Error.class), StatusCode.CODE_400);
    }*/


  /*  @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(dependsOnMethods = {"ShouldBeAbleToLoginToSDM"}, groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to get Server Information from OCSDM - Description from TestNG")
    public void ShouldBeAbleToGetServerInformation() {
    System.out.println("This is Cookie Parameter Transferred : JSESSIONID=" + loginSessionId);
        Response response = RestApi.get(Path.ADMIN + Path.SERVER_INFORMATION, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to get Server Information");
    }

  /*  @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to get Device Groups Information from OCSDM - Description from TestNG")
    public void ShouldBeAbleToGetDeviceGroups() {


        Response response = RestApi.get(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICE_GROUPS, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to get Device Groups Information");
        //ShouldBeAbleToGetSpecificDeviceGroupInfo(loginSessionId, deviceGroupID);
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to get Device Groups Information from OCSDM - Description from TestNG")
    public void ShouldBeAbleToGetSpecificDeviceGroupInfo() {

        Response response = RestApi.get(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICE_GROUPS + "/" + deviceGroupID, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should is to get Device Groups Information");
        //ShouldBeAbleToGetManagebleAndNonManagebleDevices(loginSessionId);


    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to get all manageble & non manageble devices from OCSDM - Description from TestNG")
    public void ShouldBeAbleToGetManagebleAndNonManagebleDevices() {


        Response response = RestApi.get(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICES, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should is to get all manageble & non manageble devices");
        //ShouldBeAbleToGetInformationOfSpecificDevice(loginSessionId, deviceID);
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to get information of a specific device from OCSDM - Description from TestNG")
    public void ShouldBeAbleToGetInformationOfSpecificDevice() {


        Response response = RestApi.get(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICES + "/" + deviceID, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to get information of a specific device");
        // ShouldBeAbleToAddDeviceGroup();
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to add Device Group - Description from TestNG")
    public void ShouldBeAbleToAddDeviceGroup() {



        DeviceGroupIdRequest deviceGroupRequest = deviceGroupBuilder("DeviceGroupAddedByAutomation19qwqw");
        Response response = RestApi.post(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICE_GROUPS, deviceGroupRequest, loginSessionId);

        DeviceGroupIdResponse DeviceGroupResponseBody = response.getBody().as(DeviceGroupIdResponse.class);
        deviceGroupId = DeviceGroupResponseBody.id;
        System.out.println("deviceGroupID is " + deviceGroupID);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User should is to add Device Group");
        //Assert is Failing: assertError(response.as(Error.class), StatusCode.CODE_400);
        //ShouldBeAbleToUpdateDeviceGroup();
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to update a specific Device Group - Description from TestNG")
    public void ShouldBeAbleToUpdateDeviceGroup() {

        System.out.println("deviceGroupID is " + deviceGroupID);
        DeviceGroupRequest deviceGroupupdatedRequest = deviceGroupidBuilder("DeviceGroupUpdatedByAutomation20");
        Response response = RestApi.put(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICE_GROUPS + "/" + deviceGroupId, deviceGroupupdatedRequest, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to update a specific Device Group");
        //  assertError(response.as(Error.class), StatusCode.CODE_400);
        // ShouldBeAbleToLockSpecificDevice(loginSessionId, deviceGroupID);
    }


   /* @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to delete a specific Device Groups Information from OCSDM - Description from TestNG")
    public void ShouldBeAbleToDeleteSpecificDeviceGroupInfo(String loginSessionId, String deviceGroupID) {
        Response response = RestApi.delete(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICE_GROUPS+"/"+deviceGroupID, loginSessionId);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200, "User is able to delete a specific Device Groups Information");
        //ShouldBeAbleToLockSpecificDevice(loginSessionId, deviceID);
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to lock a specific device from OCSDM - Description from TestNG")
    public void ShouldBeAbleToLockSpecificDevice(String loginSessionId, String deviceID) {
        DeviceActionRequest deviceActionRequest = deviceActionRequestBuilder("Lock");
        Response response = RestApi.post(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICES +"/"+ deviceID + Path.ACTION, deviceActionRequest, loginSessionId);
        //assertStatusCode(response.statusCode(), StatusCode.CODE_204, "User is able lock a specific device");
        ShouldBeAbleToUnockSpecificDevice(loginSessionId, deviceID);
    }

    @FrameworkAnnotation(author = {AuthorType.NAGARJUN},
            category = {CategoryType.SMOKE, CategoryType.SANITY, CategoryType.REGRESSION})
    @Test(groups = {"SMOKE", "SANITY", "REGRESSION"}, description = "should be able to get information of a specific device from OCSDM - Description from TestNG")
    public void ShouldBeAbleToUnockSpecificDevice(String loginSessionId, String deviceID) {
        DeviceActionRequest deviceActionRequest = deviceActionRequestBuilder("Unlock");
        Response response = RestApi.post(Path.INVENTORY + Path.DEVICE_MANAGEMENT + Path.DEVICES +"/"+ deviceID + Path.ACTION, deviceActionRequest, loginSessionId);
        //Assert is Failing: assertStatusCode(response.statusCode(), StatusCode.CODE_204, "User is able unlock a specific device");
        ShouldBeAbleToDeleteSpecificDeviceGroupInfo(loginSessionId, deviceID);
    }

    /*    @FrameworkAnnotation(author = { AuthorType.NAGARJUN },
            category = { CategoryType.SMOKE,CategoryType.SANITY, CategoryType.REGRESSION })
    @Test(groups = {"SMOKE","SANITY","REGRESSION"},description = "should be able to get Server Information - Description from TestNG")
    public void ShouldBeAbleToGETServerInformation(){
        ServerInformation requestServerInformation = serverInformationBuilder();
        Response response = RestApi.post(requestServerInformation);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200,"User is able to get Server Information");
        assertLoginEqual(response.as(SessionRequest.class), requestServerInformation);
    }*/


    @Step
    public SessionRequest loginBuilder(String userName, String password) {

        return SessionRequest.builder()
                .userName(userName)
                .password(password).build();
    }

    @Step
    public DeviceAssociation DeviceAssociationBuilder(String deviceName, String nfName) {

        return DeviceAssociation.builder()
                .deviceName(deviceName)
                .nfName(nfName).build();
    }

    @Step
    public Object categoryBuilder(String nfConfigName,
                                  String parentGroupFullName,
                                  String nfCategoryName,
                                  String product,
                                  String vendor,
                                  String nfTypeName,
                                  List<Parameter> parameters) {

        return NfCategory.builder();
    }

    public com.oracle.ocsdm.pojo.lombok.Session sessionBuilder() {
        return com.oracle.ocsdm.pojo.lombok.Session.builder()
                .loginSessionId(loginSessionId)
                .build();
    }

    @Step
    public DeviceGroupIdRequest deviceGroupBuilder(String name) {
        return DeviceGroupIdRequest.builder()
                .name(name)
                .build();//Completed
    }

    @Step
    public DeviceGroupRequest deviceGroupidBuilder(String name) {
        return DeviceGroupRequest.builder()
                .name(name)
                .build();//Completed
    }


    public DeviceActionRequest deviceActionRequestBuilder(String action) {
        return DeviceActionRequest.builder()
                .type(action)
                .build();//Completed
    }
/*    @Step
    public ServerInformation serverInformationBuilder(){

        return ServerInformation.builder().build();//Completed
    }*/

    @Step
    public void assertLoginEqual(SessionRequest responseSessionRequest, SessionRequest requestSessionRequest) {
        assertThat(responseSessionRequest.getUserName(), equalTo(requestSessionRequest.getUserName()));
        //VerificationManager.validateResponse(responseSessionRequest.getName(),requestSessionRequest.getName(),
        //FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD +" - <b> <u> "+message+" </u> </b>");
        VerificationManager.validateResponse(responseSessionRequest.getUserName(), requestSessionRequest.getUserName(),
                FrameworkConstants.ASSERTION_FOR_RESPONSE_CUSTOM_FIELD + " - <b> <u> NAME </u> </b>");
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode, String message) {
        assertThat(actualStatusCode, equalTo(statusCode.code));
        VerificationManager.validateResponse(actualStatusCode, statusCode.code,
                //FrameworkConstants.ASSERTION_FOR_RESPONSE_STATUS_CODE +" - <b> <u> Register the account </u> </b>");
                FrameworkConstants.ASSERTION_FOR_RESPONSE_STATUS_CODE + " - <b> <u> " + message + " </u> </b>");

    }
    /*
    public Object buildPOJO(Error responseErr, StatusCode statusCode)
    List<Attribute> list = new ArrayList<>();
    Attribute field1 = Attribute.builder().name("id")
            .value("binay-realm111")
            .build();
        list.add(field1);

    List<Attribute> list1 = new ArrayList<>();
    Attribute field2 = Attribute.builder().name("name")
            .value("lo0")
            .build();
    Attribute field3 = Attribute.builder().name("subPortId")
            .value("0")
            .build();
    Attribute field4 = Attribute.builder().name("family")
            .value("4")
            .build();

        list1.add(field2);
        list1.add(field3);
        list1.add(field4);

    Attributes attr = Attributes.builder().attribute(list).build();
    Attributes attri = Attributes.builder().attribute(list1).build();
    ChildrenElement childrenElement = ChildrenElement.builder().elementTypePath("realmConfig/networkInterfaceId").attributesList(attri).build();
    ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();
    ConfigElement configElement;
    configElement = ConfigElement.builder().elementTypePath("realmConfig").attributesList(attr).childrenElements(childrenElements).build();

    //configElement = ConfigElement.builder().elementTypePath("realmConfig").attributesList((Attributes) attr).childrenElements().build();

    Response response = RestApi.post(Path.CONFIGURATION + Path.DEVICE_CONFIGURATIONS + "/" + deviceID + Path.CONFIGURATION_ELEMENTS +Path.ADD, configElement, loginSessionId);
}*/
}
