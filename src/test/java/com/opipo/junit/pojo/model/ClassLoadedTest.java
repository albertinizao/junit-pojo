package com.opipo.junit.pojo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class ClassLoadedTest {

    @Test
    void givenClassThenBuildClassLoader() {
        String clazz = "package com.opipo.rev.lorewiki.model;\n" +
                "\n" +
                "import io.swagger.annotations.ApiModel;\n" +
                "import io.swagger.annotations.ApiModelProperty;\n" +
                "import org.hibernate.validator.constraints.NotEmpty;\n" +
                "import org.springframework.data.annotation.Id;\n" +
                "import org.springframework.data.mongodb.core.mapping.Document;\n" +
                "\n" +
                "@ApiModel(value = \"Section\", description = \"Section of information in a page\")\n" +
                "public class Section {\n" +
                "\n" +
                "    @Id\n" +
                "    @ApiModelProperty(value = \"The url to access\", required = true, example = \"naciones/humanas\")\n" +
                "    @NotEmpty\n" +
                "    private Integer position;\n" +
                "\n" +
                "    @ApiModelProperty(value = \"The title of the page\", required = true, example = \"naciones humanas\")\n" +
                "    @NotEmpty\n" +
                "    private String name;\n" +
                "}\n";
        List<String> imports = Arrays.asList("io.swagger.annotations.ApiModel", "io.swagger.annotations.ApiModelProperty",
                "org.hibernate.validator.constraints.NotEmpty", "org.springframework.data.annotation.Id", "org.springframework.data.mongodb.core.mapping.Document");
        List<String> attributes = Arrays.asList("Integer position", "String name");
        ClassLoaded classLoaded = new ClassLoaded(clazz);
        Assertions.assertEquals("com.opipo.rev.lorewiki.model", classLoaded.getPackageName());
        Assertions.assertEquals("Section", classLoaded.getClassName());
        Assertions.assertEquals(imports.size(),classLoaded.getImports().size());
        Assertions.assertTrue(imports.containsAll(classLoaded.getImports()));
        Assertions.assertEquals(attributes.size(),classLoaded.getAttributes().size());
        Assertions.assertTrue(attributes.containsAll(classLoaded.getAttributes()));
    }
}
