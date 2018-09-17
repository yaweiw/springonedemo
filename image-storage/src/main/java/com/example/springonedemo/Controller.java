package com.example.springonedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class Controller {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String sayHello() {
		return "Say Hello World!";
	}

	@Autowired
	public ResourceStorage resourceStorageService;

	@Autowired
	public NotificationSender notificationSender;

	@GetMapping("/images/{imageName}")
	public ResponseEntity<Resource> serveImage(@PathVariable String imageName) throws IOException {
		Resource image = resourceStorageService.load("blob://images/" + imageName);
		String contentType = "application/octet-stream";
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.body(new InputStreamResource(image.getInputStream()));
	}

	@PostMapping("/upload")
	public String updateImage(@RequestParam("image") MultipartFile image) throws IOException {
		String imageLocation = "blob://images/" + image.getOriginalFilename();
		//1. store image
		resourceStorageService.store(imageLocation, image.getInputStream());
		//2. send notification
		notificationSender.doSend(imageLocation);
		return "redirect:/";
	}
}

