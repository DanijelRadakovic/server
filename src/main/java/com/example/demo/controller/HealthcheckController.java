package com.example.demo.controller;

import com.example.demo.conf.EndpointConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(EndpointConfiguration.HEALTHCHECK_URL)
public class HealthcheckController {

    public HealthcheckController() {
    }

    /**
     * GET /health
     *
     * @return all available servers
     */
    @GetMapping
    public ResponseEntity<String> health() throws IOException {
        return new ResponseEntity<>("healthy", HttpStatus.OK);
    }
}
