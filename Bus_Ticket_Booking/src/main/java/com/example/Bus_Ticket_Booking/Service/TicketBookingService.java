package com.example.Bus_Ticket_Booking.Service;

import com.example.Bus_Ticket_Booking.Dto.TicketBookingDto;
import com.example.Bus_Ticket_Booking.Entity.TicketBooking;

import java.util.List;

public interface TicketBookingService
{
    TicketBookingDto saveBooking(TicketBookingDto ticketBookingDto);
    List<TicketBooking> getTicketsByPassengerId(Long passengerId);
}
