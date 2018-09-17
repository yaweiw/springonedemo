package com.example.springonedemo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

	@GetMapping("/{name}")
    @Cacheable("testcache")
    public String getValue(@PathVariable String name) {
	    return "hello " + name;
    }
}

