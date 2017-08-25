package com.opipo.junit.pojo.controller;

import com.opipo.junit.pojo.model.ClassLoaded;
import com.opipo.junit.pojo.service.ComparableWritter;
import com.opipo.junit.pojo.service.POJOJunitWritter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@RequestMapping("/test")
@Api(value = "REST API to get Test code")
public class TestController {

    @Autowired
    private POJOJunitWritter pojoJunitWritter;

    @RequestMapping(value = {"","/junit{versionNumber}"}, method = RequestMethod.POST)
    @ApiOperation(value = "Junit tests from text", notes = "Get the code of Junit tests given a java class in text")
    public @ResponseBody
    ResponseEntity<String> fromText(@ApiParam(value="Junit version", required = false, example = "4") @PathVariable("versionNumber") String versionNumber,
                                    @ApiParam(value="Junit version", required = false, example = "4") @RequestParam String version,
                                    @ApiParam(value = "Java class", required = true, example = "package com.opipo.rev.lorewiki.model;\n" +
                                            "\n" +
                                            "import io.swagger.annotations.ApiModel;\n" +
                                            "import io.swagger.annotations.ApiModelProperty;\n" +
                                            "import org.apache.commons.lang3.builder.CompareToBuilder;\n" +
                                            "import org.apache.commons.lang3.builder.EqualsBuilder;\n" +
                                            "import org.apache.commons.lang3.builder.HashCodeBuilder;\n" +
                                            "import org.hibernate.validator.constraints.NotEmpty;\n" +
                                            "import org.springframework.data.annotation.Id;\n" +
                                            "\n" +
                                            "@ApiModel(value = \"Section\", description = \"Section of information in a page\")\n" +
                                            "public class Section implements Comparable<Section>{\n" +
                                            "\n" +
                                            "    @Id\n" +
                                            "    @ApiModelProperty(value = \"The url to access\", required = true, example = \"naciones/humanas\")\n" +
                                            "    @NotEmpty\n" +
                                            "    private Integer position;\n" +
                                            "\n" +
                                            "    @ApiModelProperty(value = \"The title of the page\", required = true, example = \"naciones humanas\")\n" +
                                            "    @NotEmpty\n" +
                                            "    private String name;\n" +
                                            "\n" +
                                            "    public Integer getPosition() {\n" +
                                            "        return position;\n" +
                                            "    }\n" +
                                            "\n" +
                                            "    public void setPosition(Integer position) {\n" +
                                            "        this.position = position;\n" +
                                            "    }\n" +
                                            "\n" +
                                            "    public String getName() {\n" +
                                            "        return name;\n" +
                                            "    }\n" +
                                            "\n" +
                                            "    public void setName(String name) {\n" +
                                            "        this.name = name;\n" +
                                            "    }\n" +
                                            "\n" +
                                            "    @Override\n" +
                                            "    public int hashCode() {\n" +
                                            "        final HashCodeBuilder hcb = new HashCodeBuilder();\n" +
                                            "        hcb.append(getPosition());\n" +
                                            "        hcb.append(getName());\n" +
                                            "        return hcb.toHashCode();\n" +
                                            "    }\n" +
                                            "\n" +
                                            "    @Override\n" +
                                            "    public boolean equals(Object object) {\n" +
                                            "        if (!(object instanceof Section)) {\n" +
                                            "            return false;\n" +
                                            "        }\n" +
                                            "        final Section other = ( Section ) object;\n" +
                                            "        final EqualsBuilder eqb = new EqualsBuilder();\n" +
                                            "        eqb.append(this.getPosition(), other.getPosition());\n" +
                                            "        eqb.append(this.getName(), other.getName());\n" +
                                            "        return eqb.isEquals();\n" +
                                            "    }\n" +
                                            "\n" +
                                            "    @Override\n" +
                                            "    public int compareTo(Section other) {\n" +
                                            "        final CompareToBuilder ctb = new CompareToBuilder();\n" +
                                            "        ctb.append(this.getPosition(), other.getPosition());\n" +
                                            "        ctb.append(this.getName(), other.getName());\n" +
                                            "        return ctb.toComparison();\n" +
                                            "    }\n" +
                                            "}\n") @RequestBody String text) {
        if ((versionNumber==null && version==null) || (versionNumber!=null && version!=null && !version.equals(versionNumber))){
            throw new IllegalArgumentException("Version is incorrect. You need to put one correctly");
        }
        String versionToCheck=version==null?versionNumber:version;
        if (!Arrays.asList("4","5").contains(versionToCheck)){
            throw new IllegalArgumentException("Version is incorrect. Only supported 4 or 5");
        }
        return new ResponseEntity<String>(pojoJunitWritter.writeMethods(new ClassLoaded(text),versionToCheck.equalsIgnoreCase("5")), HttpStatus.OK);
    }

    @RequestMapping(value = {"","/junit{versionNumber}"}, method = RequestMethod.PUT)
    @ApiOperation(value = "Junit tests from file", notes = "Get the code of Junit tests given a java file")
    public @ResponseBody
    ResponseEntity<String> fromClass(@ApiParam(value="Junit version", required = false, example = "4") @PathVariable("versionNumber") String versionNumber,
                                     @ApiParam(value="Junit version", required = true, example = "4") @RequestParam String version,
                                     @ApiParam(value = "Java class", required = true) @RequestParam("file") MultipartFile file) throws Exception{
        if ((versionNumber==null && version==null) || (versionNumber!=null && version!=null && !version.equals(versionNumber))){
            throw new IllegalArgumentException("Version is incorrect. You need to put one correctly");
        }
        String versionToCheck=version==null?versionNumber:version;
        if (!Arrays.asList("4","5").contains(versionToCheck)){
            throw new IllegalArgumentException("Version is incorrect. Only supported 4 or 5");
        }
        return new ResponseEntity<String>(pojoJunitWritter.writeMethods(new ClassLoaded(new String(file.getBytes())),versionToCheck.equalsIgnoreCase("5")), HttpStatus.OK);
    }
}
