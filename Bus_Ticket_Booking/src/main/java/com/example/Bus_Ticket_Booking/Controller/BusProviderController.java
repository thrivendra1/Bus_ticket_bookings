package com.example.Bus_Ticket_Booking.Controller;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import com.example.Bus_Ticket_Booking.Service.BusesService;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/busProvider")
@AllArgsConstructor
public class BusProviderController
{
    private BusProviderService busProviderService;
    private PassengerService passengerService;
    private PasswordEncoder passwordEncoder;
    private BusesService busesService;


    @GetMapping("/registration")
    public String Registration(Model model)
    {
        BusProviderDto busProviderDto=new BusProviderDto();
        model.addAttribute("busProvider",busProviderDto);
        return "Bus/busOperatorRegistration";
    }

    @PostMapping("/saveRegisration")
    public String saveRegistration(@ModelAttribute("busProvider") BusProviderDto busProviderDto, BindingResult bindingResult,Model model)
    {
        if(busProviderService.findBusProviderByEmailId(busProviderDto.getEmailId())!=null)
        {
            bindingResult.rejectValue("emailId","email.exist","This email is already registered. Please use another email.");
        }
       else if(passengerService.findByEmailid(busProviderDto.getEmailId())!=null)
        {
            bindingResult.rejectValue("emailId","email.exist","This email is already registered in customer. Please use another email.");
        }
        if(bindingResult.hasErrors())
        {
            return "Bus/busOperatorRegistration";
        }

        busProviderDto.setPassword(passwordEncoder.encode(busProviderDto.getPassword()));
        busProviderService.saveBusProvider(busProviderDto);

        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication,Model model)
    {
        String email=authentication.getName();
        BusProviderDto busProviderDto=busProviderService.findBusProviderByEmailId(email);
        model.addAttribute("busprovder",busProviderDto);

        List<BusesDto> busesDtos=busesService.findBusesByBusProviderEmailId(email);
        model.addAttribute("buses",busesDtos);

        return "Bus/busproviderDashboard";
    }

    @GetMapping("/addbus")
    public String addBus(Model model)
    {
        BusesDto busesDto=new BusesDto();
        model.addAttribute("busdto",busesDto);

        return "Bus/addBus";
    }

    @PostMapping("/Savethebuses")
    public String SaveTHeBuses(@ModelAttribute("busdto") BusesDto busesDto, Authentication authentication)
    {
        String email=authentication.getName();
        BusProviderDto busProviderDto=busProviderService.findBusProviderByEmailId(email);

        // Set the busProvider ID
        busesDto.setBusProvider_id(busProviderDto.getId());

        // Save the bus
        busesService.saveBueses(busesDto);

        return "redirect:/busProvider/dashboard";

    }


    @GetMapping("/deleteTheBus/{id}")
    public String deleteTheBus(@PathVariable("id") Long id)
    {
        busesService.deleteBusById(id);
        return "redirect:/busProvider/dashboard";
    }

    @GetMapping("/upadteTheBusData/{id}")
    public String upadteTheBusData(@PathVariable("id") Long id,Model model)
    {
        if (id == null) {
            // Handle error, redirect, or show message
            return "redirect:/busProvider/dashboard?error=invalid_id";
        }
        BusesDto busesDto=busesService.findyById(id);

        model.addAttribute("oldbus",busesDto);

        return "Bus/update_bus";
    }

    @PostMapping("/savetheupdatebus")
    public String savetheupdatebus(@ModelAttribute("oldbus") BusesDto busesDto)
    {
        busesService.updateTheBus(busesDto);
        return "redirect:/busProvider/dashboard";
    }
}
