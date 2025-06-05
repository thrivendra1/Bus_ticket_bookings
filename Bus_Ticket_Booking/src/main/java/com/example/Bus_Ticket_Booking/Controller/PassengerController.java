package com.example.Bus_Ticket_Booking.Controller;

import com.example.Bus_Ticket_Booking.Dto.BusesDto;
import com.example.Bus_Ticket_Booking.Dto.PassengerDto;
import com.example.Bus_Ticket_Booking.Entity.Passenger;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import com.example.Bus_Ticket_Booking.Service.BusesService;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/passenger")
public class PassengerController
{
    private PassengerService passengerService;
    private BusProviderService busProviderService;
    private BusesService busesService;

    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model)
    {
        PassengerDto passenger=new PassengerDto();
        model.addAttribute("passenger",passenger);
        return "Passenger/passengerRegistration";
    }

    @PostMapping("/savePassenger")
    public String savePassenger(@ModelAttribute("passenger") PassengerDto passengerDto, BindingResult bindingResult, Model model)
    {
        if(passengerService.findByEmailid(passengerDto.getEmailId())!=null)
        {
            bindingResult.rejectValue("emailId","email.exist","This email is already registered. Please use another email.");
        }
       else if(busProviderService.findBusProviderByEmailId(passengerDto.getEmailId())!=null)
        {
            bindingResult.rejectValue("emailId","email.exist","This email is already registered in Bus PProvider ares. Please use another email.");
        }

        if (bindingResult.hasErrors())
        {
            return "Passenger/passengerRegistration";
        }

        passengerDto.setPassword(passwordEncoder.encode(passengerDto.getPassword()));
        passengerService.savepassnger(passengerDto);
        return "redirect:/login?success";

    }

    @GetMapping("/dashboard")
    public String Dashboard(Authentication authentication,Model model)
    {
        String email=authentication.getName();

        PassengerDto passengerDto=passengerService.findByEmailid(email);
        model.addAttribute("passenger",passengerDto);
        return "Passenger/passengerdashboard";
    }

    @GetMapping("/search")
    public String serachbus(@RequestParam("form") String form,
                            @RequestParam("to") String to,
                            Authentication authentication,
                            Model model
                            )
    {
        String email=authentication.getName();

        PassengerDto passengerDto=passengerService.findByEmailid(email);
        model.addAttribute("passenger",passengerDto);

        List<BusesDto> busesDtos=busesService.findByRoute(form,to);

        model.addAttribute("buses",busesDtos);
        return "Passenger/afterBusSearch";
    }
}
