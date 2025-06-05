package com.example.Bus_Ticket_Booking.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusesDto
{
    private Long id;

    private String busName;

    private String busNumber;
    private String Starting_Point_Time;
    private String Destination_Point_Time;

    private double price;

    private  Long busProvider_id;
}
