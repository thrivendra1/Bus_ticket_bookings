package com.example.Bus_Ticket_Booking.Service.impl;

import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Entity.Buses;
import com.example.Bus_Ticket_Booking.Repository.BusProviderRepository;
import com.example.Bus_Ticket_Booking.Repository.BusesRepository;
import com.example.Bus_Ticket_Booking.Service.BusesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusesServiceImpl implements BusesService {

    @Autowired
    private BusesRepository busesRepository;

    @Autowired
    private BusProviderRepository busProviderRepository;
    @Autowired
    private ModelMapper modelMapper;

//    public BusesDto saveBueses(BusesDto busesDto) {
//        Buses bus = new Buses();
//
//        bus.setBusName(busesDto.getBusName());
//        bus.setBusNumber(busesDto.getBusNumber());
//        bus.setStartingPoint(busesDto.getStartingPoint());
//        bus.setStartTime(busesDto.getStartTime());
//        bus.setDestinationPoint(busesDto.getDestinationPoint());
//        bus.setDestinationTime(busesDto.getDestinationTime());
//        bus.setPrice(busesDto.getPrice());
//
//        // Fetch BusProvider entity from database using ID
//        BusProvider provider = busProviderRepository.findById(busesDto.getBusProvider_id())
//                                                    .orElseThrow(() -> new RuntimeException("BusProvider not found"));
//
//        // Set relation
//        bus.setBusProvider(provider);
//
//        // Save entity
//        busesRepository.save(bus);
//
//        return modelMapper.map(bus,BusesDto.class);
//    }

    @Override
    public BusesDto saveBueses(BusesDto dto) {
        Buses bus = modelMapper.map(dto, Buses.class);

        // Fetch and set BusProvider
        BusProvider provider = busProviderRepository.findById(dto.getBusProvider_id())
                .orElseThrow(() -> new RuntimeException("BusProvider not found"));
        bus.setBusProvider(provider);

        Buses savedBus = busesRepository.save(bus);
        return modelMapper.map(savedBus, BusesDto.class);
    }



    @Override
    public List<BusesDto> findBusesByBusProviderEmailId(String emailId) {
        // Fetch the BusProvider entity using email I
        BusProvider busProvider=busProviderRepository.findByEmailId(emailId);
        if(busProvider==null)
        {
            return List.of();
        }

        // Fetch all buses associated with the busProvider ID
        List<Buses> buses=busesRepository.findByBusProviderId(busProvider.getId());

//        Convert each Buses entity into BusesDto
        return buses.stream()
                .map(bus->modelMapper.map(bus,BusesDto.class))
                .toList();
    }

    @Override
    public void deleteBusById(Long id) {
        busesRepository.deleteById(id);
    }

//    @Override
//    public BusesDto findyById(Long id) {
//
//        Buses buses=busesRepository.findById(id)
//                .orElseThrow(()->new RuntimeException("No buses"));
//
//        return modelMapper.map(buses,BusesDto.class);
//    }
@Override
public BusesDto findyById(Long id) {
    Buses buses = busesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No buses"));

    BusesDto busesDto = new BusesDto();
    busesDto.setId(buses.getId());
    busesDto.setBusName(buses.getBusName());
    busesDto.setBusNumber(buses.getBusNumber());
    busesDto.setStartingPoint(buses.getStartingPoint());
    busesDto.setStartTime(buses.getStartTime());
    busesDto.setDestinationPoint(buses.getDestinationPoint());
    busesDto.setDestinationTime(buses.getDestinationTime());
    busesDto.setPrice(buses.getPrice());
    // Set busProvider_id manually from the entity’s relation
    if (buses.getBusProvider() != null) {
        busesDto.setBusProvider_id(buses.getBusProvider().getId());
    }
    return busesDto;
}


    @Override
    public BusesDto updateTheBus(BusesDto busesDto) {

        Buses existingbus=busesRepository.findById(busesDto.getId())
                .orElseThrow(()->new RuntimeException("no buses"));

        existingbus.setBusName(busesDto.getBusName());
        existingbus.setBusNumber(busesDto.getBusNumber());
        existingbus.setStartingPoint(busesDto.getStartingPoint());
        existingbus.setStartTime(busesDto.getStartTime());
        existingbus.setDestinationPoint(busesDto.getDestinationPoint());
        existingbus.setDestinationTime(busesDto.getDestinationTime());
        existingbus.setPrice(busesDto.getPrice());


        Buses updatedBus=busesRepository.save(existingbus);
        return modelMapper.map(updatedBus,BusesDto.class);
    }

    @Override
    public List<BusesDto> findByRoute(String form, String to) {
        List<Buses> buses=busesRepository.findByStartingPointIgnoreCaseAndDestinationPointIgnoreCase(form,to);

        return buses.stream()
                .map(a->modelMapper.map(a,BusesDto.class))
                .toList();
    }

    @Override
    public Buses findById(Long id) {
       return busesRepository.findById(id).orElseThrow(()->new RuntimeException("No bus"));
    }

}

