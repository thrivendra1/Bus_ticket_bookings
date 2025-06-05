package com.example.Bus_Ticket_Booking.Service.impl;

import com.example.Bus_Ticket_Booking.Dto.PassengerDto;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import com.example.Bus_Ticket_Booking.Repository.PassengerRepository;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PassengerServiceImp implements PassengerService {

    private PassengerRepository passengerRepository;
    private ModelMapper modelMapper;

    @Override
    public PassengerDto savepassnger(PassengerDto passengerDto) {

        Passenger passenger=modelMapper.map(passengerDto,Passenger.class);

       Passenger savedPassenger= passengerRepository.save(passenger);

       return modelMapper.map(savedPassenger,PassengerDto.class);
    }

    @Override
    public PassengerDto findByEmailid(String email) {

        Passenger passenger=passengerRepository.findByEmailId(email);
        if(passenger==null)
        {
            return null;
        }

        return modelMapper.map(passenger,PassengerDto.class);
    }
}
