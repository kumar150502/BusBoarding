package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import java.util.*;

import com.example.entities.Passenger;
import com.example.entities.Ticket;
import com.example.exceptions.DuplicateSeatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class BoardingService {

    public String calculateBoardingSequence(List<Ticket> tickets) throws DuplicateSeatException {
        Map<String, List<String>> pnrMap = new HashMap<>();
        for (Ticket ticket : tickets) {
            pnrMap.computeIfAbsent(ticket.getPnr(), k -> new ArrayList<>()).add(ticket.getSeat());
        }

        List<Passenger> passengers = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : pnrMap.entrySet()) {
            passengers.add(new Passenger(entry.getKey(), entry.getValue()));
        }

        return generateOutput(calculateSequence(passengers));
    }

    public List<String> calculateSequence(List<Passenger> passengers) throws DuplicateSeatException {
        passengers.sort(Comparator.comparing(p -> p.getSeats().get(0)));
        Map<String, Boolean> occupiedSeats = new HashMap<>();
        List<String> boardingSequence = new ArrayList<>();
        int totalTime = 0;

        for (Passenger passenger : passengers) {
            for (String seat : passenger.getSeats()) {
                // Check if the seat is already occupied
                if (occupiedSeats.getOrDefault(seat, false)) {
                    throw new DuplicateSeatException("Seat " + seat + " is already occupied.");
                }

                int timeToBoard = 30;
                if (occupiedSeats.containsKey(seat)) {
                    timeToBoard += 30;
                }
                timeToBoard += 30;

                occupiedSeats.put(seat, true);
                totalTime += timeToBoard;
                boardingSequence.add(String.format("PNR: %s, Seat: %s, Time: %d seconds", passenger.getPnr(), seat, totalTime));
            }
        }
        return boardingSequence;
    }

    public String generateOutput(List<String> boardingSequence) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputJson = mapper.createObjectNode();
        ArrayNode boardingArray = mapper.createArrayNode();

        for (String entry : boardingSequence) {
            boardingArray.add(entry);
        }

        outputJson.set("boardingSequence", boardingArray);
        return outputJson.toString();
    }
}
