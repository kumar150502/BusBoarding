package com.example.demo;

import com.example.entities.Ticket;
import com.example.service.BoardingService;
import com.example.exceptions.DuplicateSeatException; // Import your custom exception
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class BoardingServiceTest {

    @Autowired
    private BoardingService boardingService;

    @Test
    public void testSinglePassenger() throws DuplicateSeatException {  // Add 'throws'
        List<Ticket> tickets = Arrays.asList(new Ticket("PNR1", "1A"));
        String expectedOutput = "{\"boardingSequence\":[\"PNR: PNR1. Seat: 1A, Time: 60 seconds\"]}";
        String actualOutput = boardingService.calculateBoardingSequence(tickets);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testMultiplePassengersDifferentPNR() throws DuplicateSeatException {  // Add 'throws'
        List<Ticket> tickets = Arrays.asList(
            new Ticket("PNR1", "1A"),
            new Ticket("PNR2", "1B")
        );
        String expectedOutput = "{\"boardingSequence\":[\"PNR: PNR1. Seat: 1A, Time: 60 seconds\",\"PNR: PNR2. Seat: 1B, Time: 120 seconds\"]}";
        String actualOutput = boardingService.calculateBoardingSequence(tickets);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testMultiplePassengersSamePNR() throws DuplicateSeatException {  // Add 'throws'
        List<Ticket> tickets = Arrays.asList(
            new Ticket("PNR1", "1A"),
            new Ticket("PNR1", "1B")
        );
        String expectedOutput = "{\"boardingSequence\":[\"PNR: PNR1. Seat: 1A, Time: 60 seconds\",\"PNR: PNR1. Seat: 1B, Time: 120 seconds\"]}";
        String actualOutput = boardingService.calculateBoardingSequence(tickets);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testMultiplePassengersDifferentSeats() throws DuplicateSeatException {  // Add 'throws'
        List<Ticket> tickets = Arrays.asList(
            new Ticket("PNR1", "1A"),
            new Ticket("PNR1", "1C"),
            new Ticket("PNR2", "1B")
        );
        String expectedOutput = "{\"boardingSequence\":[\"PNR: PNR1. Seat: 1A, Time: 60 seconds\",\"PNR: PNR2. Seat: 1B, Time: 120 seconds\",\"PNR: PNR1. Seat: 1C, Time: 180 seconds\"]}";
        String actualOutput = boardingService.calculateBoardingSequence(tickets);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testAllSeatsOccupied() {
        List<Ticket> tickets = Arrays.asList(
            new Ticket("PNR1", "1A"),
            new Ticket("PNR2", "1A") // Same seat for different PNR
        );

        try {
            boardingService.calculateBoardingSequence(tickets);
            fail("Expected DuplicateSeatException to be thrown");
        } catch (DuplicateSeatException e) {
            assertEquals("Seat 1A is already occupied.", e.getMessage());
        }
    }

    @Test
    public void testDuplicateSeatWithinSamePNR() {
        List<Ticket> tickets = Arrays.asList(
            new Ticket("PNR1", "1A"),
            new Ticket("PNR1", "1A") // Duplicate seat within the same PNR
        );

        try {
            boardingService.calculateBoardingSequence(tickets);
            fail("Expected DuplicateSeatException to be thrown");
        } catch (DuplicateSeatException e) {
            assertEquals("Seat 1A is already occupied.", e.getMessage());
        }
    }
}
