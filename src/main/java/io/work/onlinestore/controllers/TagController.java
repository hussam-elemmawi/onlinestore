package io.work.onlinestore.controllers;

import io.swagger.annotations.ApiOperation;
import io.work.onlinestore.data.model.Tag;
import io.work.onlinestore.services.interfaces.TagService;
import io.work.onlinestore.util.exception.ServiceException;
import io.work.onlinestore.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/tags")
@Validated
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(path = "/")
    @ApiOperation(value = "Get all tags", notes = "Get all tags information")
    public ResponseEntity<ApiResponse<List<Tag>>> getAllTags() {
        try {
            List<Tag> tagList = tagService.getAllTags();
            return new ResponseEntity<>(new ApiResponse<>("Get all tags success", tagList), HttpStatus.OK);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Get all tags failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/")
    @ApiOperation(value = "Create tag", notes = "Create a new tag")
    public ResponseEntity<ApiResponse<String>> createTag(@RequestBody @Valid Tag tag) {
        try {
            tagService.create(tag);
            return new ResponseEntity<>(new ApiResponse<>("Created tag success", tag.toString()), HttpStatus.CREATED);
        } catch (ServiceException se) {
            return new ResponseEntity<>(new ApiResponse<>("Create new product failed " + se.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
