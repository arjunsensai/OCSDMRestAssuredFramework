package com.oracle.ocsdm.utils;

import com.oracle.ocsdm.pojo.lombok.configuration.Attributes;
import com.oracle.ocsdm.pojo.lombok.configuration.ChildrenElement;
import com.oracle.ocsdm.pojo.lombok.configuration.ChildrenElements;
import com.oracle.ocsdm.pojo.lombok.configuration.ConfigElement;

import java.util.ArrayList;
import java.util.List;

public class BuildRequestPOJO {
    public static ChildrenElement childrenElement;
    public static ChildrenElements childrenElements;

    public static ConfigElement buildMainElementRequestPOJO902(String[] key, String[] value) {

        List<Attributes> mainElementKeyValuePair = new ArrayList<>();
        List<ChildrenElements> childrenElementsList = new ArrayList<>();

        String mainElementTypePath = null;
        for (int i = 0; i < key.length; i++) {
            if (key[i].equalsIgnoreCase("MainElement")) {
                mainElementTypePath = value[i];
            } else if (key[i].equalsIgnoreCase("SubElement")) {
                List<Attributes> subElementKeyValuePair = new ArrayList<>();
                String subElementTypePath = value[i];

                //i = i + 1;
                //int j = i + 1;
                for (int j = i + 1; j < key.length; j++) {
                    if (!key[j].equalsIgnoreCase("MainElement") && !key[j].equalsIgnoreCase("SubElement")) {
                        Attributes subElementAttributesBuilder = Attributes.builder().name(key[j])
                                .value(value[j])
                                .build();
                        subElementKeyValuePair.add(subElementAttributesBuilder);
                        i++;

                        //ChildrenElements childrenElementsListBuilder = ChildrenElements.builder().attributes(subElementKeyValuePair).elementTypePath(subElementTypePath).build();
                        //childrenElementsList.add(childrenElementsListBuilder);
                    } else {
                        break;
                    }
                }
                ChildrenElements childrenElementsListBuilder = ChildrenElements.builder().attributes(subElementKeyValuePair).elementTypePath(subElementTypePath).build();
                childrenElementsList.add(childrenElementsListBuilder);
            } else {
                Attributes elementAttributesBuilder = Attributes.builder().name(key[i])
                        .value(value[i])
                        .build();
                mainElementKeyValuePair.add(elementAttributesBuilder);

            }
        }
        ConfigElement configElement;
        if (childrenElementsList.size()==0) {
            configElement = ConfigElement.builder().elementTypePath(mainElementTypePath).attributes(mainElementKeyValuePair).build();
        } else {
            configElement = ConfigElement.builder().elementTypePath(mainElementTypePath).attributes(mainElementKeyValuePair).childrenElements(childrenElementsList).build();
        }
        return configElement;

    }
}

     /*  public static ConfigElement buildMainElementRequestPOJO(String elementTypePath, String subElementTypePath, String[] name, String[] value, int elementAttributeCount, int subElementAttributeCount) {

        List<Attribute> elementAttributeList = new ArrayList<>();
        for (int i = 0; i < (elementAttributeCount); i++) {
            Attribute elementAttribute = Attribute.builder().name(name[i])
                    .value(value[i])
                    .build();
            elementAttributeList.add(elementAttribute);
        }
        Attributes elementAttributes = Attributes.builder().attribute(elementAttributeList).build();
        List<Attribute> subElementAttributeList = new ArrayList<>();
        if (subElementTypePath != null) {
            for (int i = elementAttributeCount; i <= (subElementAttributeCount); i++) {
                Attribute subElementAttribute = Attribute.builder().name(name[i])
                        .value(value[i])
                        .build();
                subElementAttributeList.add(subElementAttribute);
            }
            Attributes subElementAttributes = Attributes.builder().attribute(subElementAttributeList).build();

            childrenElement = ChildrenElement.builder().elementTypePath(subElementTypePath).attributes(subElementAttributes).build();
        }
        ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();

        ConfigElement configElement;
        configElement = ConfigElement.builder().elementTypePath(elementTypePath).attributes(elementAttributes).childrenElements(childrenElements).build();

        return configElement;

    }*/


   /* public static ConfigElement buildMainElementRequestPOJO902(String elementTypePath, String subElementTypePath, String[] name, String[] value, int elementAttributeCount, int subElementAttributeCount) {

        List<Attributes> elementAttributeList = new ArrayList<>();
        for (int i = 0; i < (elementAttributeCount); i++) {
            Attributes elementAttribute = Attributes.builder().name(name[i])
                    .value(value[i])
                    .build();
            elementAttributeList.add(elementAttribute);
            System.out.println();

        }
        List<ChildrenElements> childrenElementsList = new ArrayList<>();
        {
            if (subElementCount != 0) {
                for (int i = elementAttributeCount; i <= (subElementAttributeCount); i++) {
                    Attributes subElementAttribute = Attributes.builder().name(name[i])
                            .value(value[i])
                            .build();
                    subElementAttributeList.add(subElementAttribute);
                    List<Attributes> subElementAttributeList = new ArrayList<>();
                    if (subElementTypePath != null) {
                        for (int i = elementAttributeCount; i <= (subElementAttributeCount); i++) {
                            Attributes subElementAttribute = Attributes.builder().name(name[i])
                                    .value(value[i])
                                    .build();
                            subElementAttributeList.add(subElementAttribute);
                        }
                    }
                    //Attributes subElementAttributes = Attributes.builder().attributes(subElementAttributeList).build();
                    childrenElements = ChildrenElements.builder().elementTypePath(subElementTypePath).attributes(subElementAttributeList).build();
                }
                ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();
                ConfigElement configElement;
                configElement = ConfigElement.builder().elementTypePath(elementTypePath).attributes(elementAttributeList).childrenElements(childrenElements).build();
                return configElement;
            }*/

/*
            public static ConfigElement buildMainElementRequestPOJO9012(String elementTypePath, String[] subElementTypePath, String[] name, String[] value, int elementAttributeCount, int subElementAttributeCount, int subElementTypePathCount)
            {

                List<Attributes> elementAttributeList = new ArrayList<>();
                for (int i = 0; i < (elementAttributeCount); i++) {
                    Attributes elementAttribute = Attributes.builder().name(name[i])
                            .value(value[i])
                            .build();
                    elementAttributeList.add(elementAttribute);
                }

                List<Attributes> subElementAttributeList = new ArrayList<>();
                    while(subElementTypePathCount>0) {
                        for(int i=0; i<=subElementTypePath[subElementTypePathCount]; i++){

                        }
                        for (int i = elementAttributeCount; i <= (subElementAttributeCount); i++) {
                            Attributes subElementAttribute = Attributes.builder().name(name[i])
                                    .value(value[i])
                                    .build();
                            subElementAttributeList.add(subElementAttribute);

                            subElementTypePathCount--;
                        }
                            if (subElementTypePath != null) {
                                for (int i = elementAttributeCount; i <= (subElementAttributeCount); i++) {
                                    Attributes subElementAttribute = Attributes.builder().name(name[i])
                                            .value(value[i])
                                            .build();
                                    subElementAttributeList.add(subElementAttribute);
                                }
                            }
                            //Attributes subElementAttributes = Attributes.builder().name().build();
                            childrenElements = ChildrenElements.builder().elementTypePath(Arrays.toString(subElementTypePath)).attributes(subElementAttributeList).build();
                        }
                        ChildrenElements childrenElements = ChildrenElements.builder().childrenElement(childrenElement).build();
                        ConfigElement configElement;

                        configElement = ConfigElement.builder().elementTypePath(elementTypePath).attributes(elementAttributeList).childrenElements(childrenElements).build();
                        return configElement;
                    }*/
