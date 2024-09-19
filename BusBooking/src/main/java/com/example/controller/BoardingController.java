package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Ticket;
import com.example.exceptions.DuplicateSeatException;
import com.example.service.BoardingService;

@RestController
public class BoardingController {

    @Autowired
    private BoardingService boardingService;

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateBoarding(@RequestBody List<Ticket> tickets) {
        try {
            String result = boardingService.calculateBoardingSequence(tickets);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DuplicateSeatException e) {
            // If a duplicate seat is found, return a 400 Bad Request with the error message
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Optional: You can use this handler to catch the exception globally if needed.
    @ExceptionHandler(DuplicateSeatException.class)
    public ResponseEntity<String> handleDuplicateSeatException(DuplicateSeatException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
