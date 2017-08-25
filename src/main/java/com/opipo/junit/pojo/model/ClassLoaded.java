package com.opipo.junit.pojo.model;

import com.opipo.junit.pojo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ClassLoaded {

    private static String packageSearch = "package ";
    private static String importSearch = "import ";
    private static String classSearch = "public class ";
    private String packageName;
    private String className;
    private List<String> imports = new ArrayList<>();
    private List<String> attributes = new ArrayList<>();

    public ClassLoaded(String clazz) {
        String[] lines = clazz.split(System.getProperty("line.separator"));
        boolean braceOpened = false;
        for (String line : lines) {
            if (line.startsWith(packageSearch)) {
                this.packageName = Utils.removeSemicolon(line.substring(packageSearch.length()));
            } else if (line.startsWith(importSearch)) {
                addImport(Utils.removeSemicolon(line.substring(importSearch.length())));
            } else if (line.startsWith(classSearch)) {
                String newLine = line.substring(classSearch.length());
                this.className = newLine.contains(" ") ? newLine.substring(0, newLine.indexOf(" ")) : newLine.contains("{") ? newLine.substring(0, newLine.indexOf("{")) : newLine;
            } else{
                line = line.trim().replaceAll("/t","").replaceAll("    ","");
                if (!line.trim().isEmpty() && !line.startsWith("@")) {
                    braceOpened = braceOpened || line.contains("{");
                    if (!braceOpened) {
                        if (!line.contains(" static ")) {
                            line = line.contains("=") ? line.substring(0, line.indexOf("=")) : line;
                            if (line.contains(" ") && !line.startsWith("//") && !line.startsWith("/*")) {
                                line = line.startsWith("private")?line.replaceFirst("private",""):line.startsWith("public")?line.replaceFirst("public",""):line;
                                attributes.add(Utils.removeSemicolon(line.trim()));
                            }
                        }
                    } else {
                        braceOpened = !line.contains("}");
                    }
                }
            }
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public void addImport(String imports) {
        this.imports.add(imports);
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String attribute) {
        this.attributes.add(attribute);
    }
}
