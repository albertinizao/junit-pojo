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

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "Junit4 tests from text", notes = "Get the code of Junit4 tests given a java class in text")
    public @ResponseBody
    ResponseEntity<String> fromText(@ApiParam(value="Junit version", required = true, example = "4") @RequestParam String version,
                                    @ApiParam(value = "Java class", required = true) @RequestBody String text) {
        if (!Arrays.asList("4","5").contains(version)){
            throw new IllegalArgumentException("Version is incorrect. Only supported 4 or 5");
        }
        return new ResponseEntity<String>(pojoJunitWritter.writeMethods(new ClassLoaded(text),version.equalsIgnoreCase("5")), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "Junit4 tests from file", notes = "Get the code of Junit4 tests given a java file")
    public @ResponseBody
    ResponseEntity<String> fromClass(@ApiParam(value="Junit version", required = true, example = "4") @RequestParam String version,
                                     @ApiParam(value = "Java class", required = true) @RequestParam("file") MultipartFile file) throws Exception{
        if (!Arrays.asList("4","5").contains(version)){
            throw new IllegalArgumentException("Version is incorrect. Only supported 4 or 5");
        }
        return new ResponseEntity<String>(pojoJunitWritter.writeMethods(new ClassLoaded(new String(file.getBytes())),false), HttpStatus.OK);
    }

    @RequestMapping(value = "/junit4", method = RequestMethod.POST)
    @ApiOperation(value = "Junit4 tests from text", notes = "Get the code of Junit4 tests given a java class in text")
    public @ResponseBody
    ResponseEntity<String> fromText(@ApiParam(value = "Java class", required = true) @RequestBody String text) {
        return new ResponseEntity<String>(pojoJunitWritter.writeMethods(new ClassLoaded(text),false), HttpStatus.OK);
    }

    @RequestMapping(value = "/junit4", method = RequestMethod.PUT)
    @ApiOperation(value = "Junit4 tests from file", notes = "Get the code of Junit4 tests given a java file")
    public @ResponseBody
    ResponseEntity<String> fromClass(@ApiParam(value = "Java class", required = true) @RequestParam("file") MultipartFile file) throws Exception{
        return new ResponseEntity<String>(pojoJunitWritter.writeMethods(new ClassLoaded(new String(file.getBytes())),false), HttpStatus.OK);
    }

    @RequestMapping(value = {"/junit5"}, method = RequestMethod.POST)
    @ApiOperation(value = "Junit5 tests from text", notes = "Get the code of Junit5 tests given a java class in text")
    public @ResponseBody
    ResponseEntity<String> fromText5(@ApiParam(value = "Java class", required = true) @RequestBody String text) {
        return new ResponseEntity<String>(pojoJunitWritter.writeMethods(new ClassLoaded(text),true), HttpStatus.OK);
    }

    @RequestMapping(value = {"/junit5"}, method = RequestMethod.PUT)
    @ApiOperation(value = "Junit5 tests from file", notes = "Get the code of Junit5 tests given a java file")
    public @ResponseBody
    ResponseEntity<String> fromClass5(@ApiParam(value = "Java class", required = true) @RequestParam("file") MultipartFile file) throws Exception{
        return new ResponseEntity<String>(pojoJunitWritter.writeMethods(new ClassLoaded(new String(file.getBytes())),true), HttpStatus.OK);
    }
}
