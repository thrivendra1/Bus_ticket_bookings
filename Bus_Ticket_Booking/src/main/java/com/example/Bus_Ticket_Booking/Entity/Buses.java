package com.example.Bus_Ticket_Booking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Buses
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String busName;

    @Column(nullable = false)
    private String busNumber;

    @Column(nullable = false)
    private String Starting_Point_Time;

    @Column(nullable = false)
    private String Destination_Point_Time;


    @Column(nullable = false)
    private double price;


    @ManyToOne
    @JoinColumn(name = "bus_provider_id")
    private BusProvider busProvider;



}
