package com.opipo.junit.pojo.service;

import com.opipo.junit.pojo.model.ClassLoaded;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class POJOJunitWritter {

    public String writeMethods(ClassLoaded clazz, boolean junit5){
        String nombreClase = clazz.getClassName().substring(0, 1).toLowerCase() + clazz.getClassName().substring(1);
        String lineSeparator = System.getProperty("line.separator");
        String nombreClaseCammelCase = clazz.getClassName();

        Set<String> importsToAdd = new TreeSet<>();
        Long contadorLong = 1L;
        Integer contadorInteger = 1;
        Double contadorDouble = 1D;

        StringBuilder test = new StringBuilder();
        for (String atribute : clazz.getAttributes()) {
            String[] atributeSplit = atribute.split(" ");
            String nombreAtributo = atributeSplit[1].substring(0, 1).toUpperCase() + atributeSplit[1].substring(1);
            StringBuilder declaracionAtributo = new StringBuilder();
            declaracionAtributo.append(atribute).append(" = ");
            String clase = atributeSplit[0];
            if (clase.equalsIgnoreCase("Long") || clase.equalsIgnoreCase("long")) {
                declaracionAtributo.append("Long.valueOf(").append(contadorLong++).append("L);");
            } else if (clase.equalsIgnoreCase("Short") || clase.equalsIgnoreCase("short")) {
                declaracionAtributo.append(contadorInteger++).append(";");
            } else if (clase.equalsIgnoreCase("Integer") || clase.equalsIgnoreCase("int")) {
                declaracionAtributo.append("Integer.valueOf(").append(contadorInteger++).append(");");
            } else if (clase.equalsIgnoreCase("Double") || clase.equalsIgnoreCase("double")) {
                declaracionAtributo.append("Double.valueOf(").append(contadorInteger++).append("D);");
            } else if (clase.equalsIgnoreCase("BigDecimal")) {
                declaracionAtributo.append("BigDecimal.valueOf(").append(contadorDouble++).append("D);");
            } else if (clase.equalsIgnoreCase("String")) {
                declaracionAtributo.append("Integer.toString(").append(contadorInteger++).append(");");
            } else if (clase.equalsIgnoreCase("Date")) {
                declaracionAtributo.append("new Date();");
            } else if (clase.equalsIgnoreCase("Boolean") || clase.equalsIgnoreCase("boolean")) {
                declaracionAtributo.append("Boolean.valueOf(true);");
            } else if (clase.startsWith("List")) {
                declaracionAtributo.append("new Array").append(clase).append("();");
            } else if (clase.startsWith("Set")) {
                declaracionAtributo.append("new Hash").append(clase).append("();");
            } else if (clase.equalsIgnoreCase("Byte[]") || clase.equalsIgnoreCase("byte[]") || clase.equalsIgnoreCase("Byte []") || clase.equalsIgnoreCase("byte []")) {
                declaracionAtributo.append("Integer.toString(").append(contadorInteger++).append(").getBytes();");
            }else{
                declaracionAtributo.append("this.build").append(clase).append("();");
            }

            importsToAdd.add(clazz.getImports().stream().filter(f->f.endsWith(clase)).findFirst().orElse(""));

            StringBuilder setter = new StringBuilder();
            setter.append(nombreClase).append(".set").append(nombreAtributo).append("(").append(atributeSplit[1])
                    .append(");");

            StringBuilder getter = new StringBuilder();
            getter.append(nombreClase).append(".get").append(nombreAtributo).append("()");

            StringBuilder assertion = new StringBuilder();
            assertion.append("assertEquals(").append(junit5?"":"\"The attribute is not the expected\",").append(atributeSplit[1]).append(",")
                    .append(getter.toString()).append(");");
            test.append(lineSeparator);
            test.append("@Test").append(lineSeparator);
            if (junit5){
                test.append("@DisplayName(\"The getter and the setter of ").append(atributeSplit[1]).append(" work well\")").append(lineSeparator);
            }
            test.append("public void ").append(atributeSplit[1]).append("AttributeTest(){").append(lineSeparator);
            test.append(declaracionAtributo.toString()).append(lineSeparator);
            test.append(setter.toString()).append(lineSeparator);
            test.append(assertion.toString()).append(lineSeparator);
            test.append("}");
            test.append(lineSeparator);

            if (clase.equalsIgnoreCase("Date")) {
                test.append(lineSeparator);
                test.append("@Test").append(lineSeparator);
                if (junit5){
                    test.append("@DisplayName(\"In a date, when you set null the value is setted\")").append(lineSeparator);
                }
                test.append("public void ").append(atributeSplit[1]).append("NullAttributeTest(){").append(lineSeparator);
                test.append(atribute).append(" = null;").append(lineSeparator);
                test.append(setter.toString()).append(lineSeparator);
                test.append("assertNull(").append(junit5?"":"\"The attribute must be null\",").append(getter.toString()).append(");")
                        .append(lineSeparator);
                test.append("}");
                test.append(lineSeparator);
                test.append(lineSeparator);
                if (junit5){
                    test.append("@DisplayName(\"In a date, when you set value and then change it, the value settet still unchanged\")").append(lineSeparator);
                }
                test.append("@Test").append(lineSeparator);
                test.append("public void ").append(atributeSplit[1]).append("WithChangeAttributeTest(){").append(lineSeparator);
                test.append(declaracionAtributo.toString()).append(lineSeparator);
                test.append(setter.toString()).append(lineSeparator);
                test.append(atributeSplit[1]).append(".setTime(1L);").append(lineSeparator);
                test.append("assertNotEquals(").append(junit5?"":"\"The attribute mustn't be equal\",").append(atributeSplit[1]).append(",")
                        .append(getter.toString()).append(");");
                test.append("}");
                test.append(lineSeparator);
            }
            test.append(lineSeparator);
        }
        StringBuilder constructorEqualsHashCode = new StringBuilder();
        constructorEqualsHashCode.append(clazz.getClassName()).append(" o1 = new ").append(clazz.getClassName())
                .append("();").append(lineSeparator);
        constructorEqualsHashCode.append(clazz.getClassName()).append(" o2 = new ").append(clazz.getClassName())
                .append("();").append(lineSeparator);
        StringBuilder equalsHashCode = new StringBuilder();
        equalsHashCode.append(lineSeparator);
        equalsHashCode.append("@Test").append(lineSeparator);
        equalsHashCode.append("public void givenSameObjReturnThatTheyAreEquals(){").append(lineSeparator);
        equalsHashCode.append(constructorEqualsHashCode.toString());
        equalsHashCode.append("assertEquals(").append(junit5?"":"\"The object must be equals\",").append("o1, o2);").append(lineSeparator);
        equalsHashCode.append("}").append(lineSeparator);
        equalsHashCode.append(lineSeparator);
        equalsHashCode.append("@Test").append(lineSeparator);
        equalsHashCode.append("public void givenSameObjReturnZero(){").append(lineSeparator);
        equalsHashCode.append(constructorEqualsHashCode.toString());
        equalsHashCode.append("assertEquals(").append(junit5?"":"\"The object must be equals\",").append("0, o1.compareTo(o2));").append(lineSeparator);
        equalsHashCode.append("}").append(lineSeparator);
        equalsHashCode.append(lineSeparator);
        equalsHashCode.append("@Test").append(lineSeparator);
        equalsHashCode.append("public void givenObjectFromOtherClassReturnThatTheyArentEquals(){").append(lineSeparator);
        equalsHashCode.append(clazz.getClassName()).append(" o1 = new ").append(clazz.getClassName()).append("();")
                .append(lineSeparator);
        equalsHashCode.append("assertNotEquals(").append(junit5?"":"\"The object are from distinct classes\",").append("o1, new String());")
                .append(lineSeparator);
        equalsHashCode.append("}").append(lineSeparator);
        equalsHashCode.append(lineSeparator);
        equalsHashCode.append("@Test").append(lineSeparator);
        equalsHashCode.append("public void givenSameObjReturnSameHashCode(){").append(lineSeparator);
        equalsHashCode.append(constructorEqualsHashCode.toString());
        equalsHashCode.append("assertEquals(").append(junit5?"":"\"The object must be equals\",").append("o1.hashCode(), o2.hashCode());").append(lineSeparator);
        equalsHashCode.append("}").append(lineSeparator);



        StringBuilder stringClase = new StringBuilder();
        stringClase.append("package ").append(clazz.getPackageName()).append(";").append(lineSeparator);
        stringClase.append(lineSeparator);
        String assertPackage = junit5?"org.junit.jupiter.api.Assertions":"org.junit.Assert";
        stringClase.append("import static ").append(assertPackage).append(".assertEquals;").append(lineSeparator);
        stringClase.append("import static ").append(assertPackage).append(".assertNull;").append(lineSeparator);
        stringClase.append("import static ").append(assertPackage).append(".assertNotEquals;").append(lineSeparator);
        stringClase.append("import ").append(junit5?"org.junit.jupiter.api.BeforeEach;":"org.junit.Before;").append(lineSeparator);
        stringClase.append("import ").append(junit5?"org.junit.jupiter.api.Test;":"org.junit.Test;").append(lineSeparator);
        if (junit5){
            stringClase.append("import org.junit.jupiter.api.DisplayName;").append(lineSeparator);
        }
        for (String importToAdd:importsToAdd){
            if (!importToAdd.trim().isEmpty()) {
                stringClase.append("import ").append(importToAdd).append(";").append(lineSeparator);
            }
        }
        stringClase.append(lineSeparator);
        if (junit5){
            stringClase.append("@DisplayName(\"").append(clazz.getClassName()).append(" autogenerated\")").append(lineSeparator);
        }
        stringClase.append("public class ").append(nombreClaseCammelCase).append("Test {").append(lineSeparator).append(lineSeparator);
        stringClase.append("private  ").append(nombreClaseCammelCase).append(" ").append(nombreClase).append(";\n")
                .append(lineSeparator);
        stringClase.append(junit5?"@BeforeEach":"@Before").append(lineSeparator);
        stringClase.append("public void init(){").append(lineSeparator);
        stringClase.append(nombreClase).append(" = new ").append(nombreClaseCammelCase).append("();").append(lineSeparator);
        stringClase.append("}").append(lineSeparator).append(lineSeparator);

        StringBuilder result = new StringBuilder();
        result.append(stringClase).append(lineSeparator);
        result.append(test).append(lineSeparator);
        result.append(equalsHashCode).append(lineSeparator);;
        result.append("}").append(lineSeparator);;
        result.append(lineSeparator);

        return result.toString();
    }
}
