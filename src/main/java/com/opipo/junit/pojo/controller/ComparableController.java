package com.opipo.junit.pojo.controller;

import com.opipo.junit.pojo.model.ClassLoaded;
import com.opipo.junit.pojo.service.ComparableWritter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/comparable")
@Api(value = "REST API to get Comparable code")
public class ComparableController {

    @Autowired
    private ComparableWritter comparableWritter;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "Comparable from text", notes = "Get the code of comparator given a java class in text")
    public @ResponseBody
    ResponseEntity<String> fromText(@ApiParam(value = "Java class", required = true) @RequestBody String text) {
        return new ResponseEntity<String>(comparableWritter.writeMethods(new ClassLoaded(text)), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ApiOperation(value = "Comparable from file", notes = "Get the code of comparator given a java file")
    public @ResponseBody
    ResponseEntity<String> fromClass(@ApiParam(value = "Java class", required = true) @RequestParam("file") MultipartFile file) throws Exception{
        return new ResponseEntity<String>(comparableWritter.writeMethods(new ClassLoaded(new String(file.getBytes()))), HttpStatus.OK);
    }
}
