package com.opipo.junit.pojo.service;

import com.opipo.junit.pojo.model.ClassLoaded;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComparableWritterTest {

    @Test
    void givenClazzThenBuildHashCodeCompareToEquals(){
        ComparableWritter comparableWritter = new ComparableWritter();

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
        ClassLoaded classLoaded = new ClassLoaded(clazz);

        String expected = "@Override\n" +
                "public int hashCode() {\n" +
                "final HashCodeBuilder hcb = new HashCodeBuilder();\n" +
                "hcb.append(getPosition());\n" +
                "hcb.append(getName());\n" +
                "return hcb.toHashCode();\n" +
                "}\n" +
                "\n" +
                "@Override\n" +
                "public boolean equals(Object object) {\n" +
                "if (!(object instanceof Section)) {\n" +
                "return false;\n" +
                "}\n" +
                "final Section other = ( Section ) object;\n" +
                "final EqualsBuilder eqb = new EqualsBuilder();\n" +
                "eqb.append(this.getPosition(), other.getPosition());\n" +
                "eqb.append(this.getName(), other.getName());\n" +
                "return eqb.isEquals();\n" +
                "}\n" +
                "\n" +
                "@Override\n" +
                "public int compareTo(Section other) {\n" +
                "final CompareToBuilder ctb = new CompareToBuilder();\n" +
                "ctb.append(this.getPosition(), other.getPosition());\n" +
                "ctb.append(this.getName(), other.getName());\n" +
                "return ctb.toComparison();\n" +
                "}";

        Assertions.assertEquals(expected, comparableWritter.writeMethods(classLoaded).trim());
    }
}
