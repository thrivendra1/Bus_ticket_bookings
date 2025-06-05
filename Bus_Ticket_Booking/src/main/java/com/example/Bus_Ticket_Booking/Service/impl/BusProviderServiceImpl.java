package com.example.Bus_Ticket_Booking.Service.impl;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Entity.BusProvider;
import com.example.Bus_Ticket_Booking.Repository.BusProviderRepository;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BusProviderServiceImpl implements BusProviderService {

    private BusProviderRepository busProviderRepository;
    private ModelMapper modelMapper;

    @Override
    public BusProviderDto saveBusProvider(BusProviderDto busProviderDto) {
        BusProvider busProvider=modelMapper.map(busProviderDto,BusProvider.class);

        BusProvider savedBusprovider=busProviderRepository.save(busProvider);

        return modelMapper.map(savedBusprovider,BusProviderDto.class);
    }

    @Override
    public BusProviderDto findBusProviderByEmailId(String emailId) {

        BusProvider busProvider=busProviderRepository.findByEmailId(emailId);

        if(busProvider==null)
        {
            return null;
        }
        return modelMapper.map(busProvider,BusProviderDto.class);
    }
}
