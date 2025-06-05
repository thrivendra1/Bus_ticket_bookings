package com.example.Bus_Ticket_Booking.Controller;

import com.example.Bus_Ticket_Booking.Dto.BusProviderDto;
import com.example.Bus_Ticket_Booking.Service.BusProviderService;
import com.example.Bus_Ticket_Booking.Service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/busProvider")
@AllArgsConstructor
public class BusProviderController
{
    private BusProviderService busProviderService;
    private PassengerService passengerService;
    private PasswordEncoder passwordEncoder;


    @GetMapping("/registration")
    public String Registration(Model model)
    {
        BusProviderDto busProviderDto=new BusProviderDto();
        model.addAttribute("busProvider",busProviderDto);
        return "busOperatorRegistration";
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
            return "busOperatorRegistration";
        }

        busProviderDto.setPassword(passwordEncoder.encode(busProviderDto.getPassword()));
        busProviderService.saveBusProvider(busProviderDto);

        return "redirect:/login?success";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model)
    {
        return "busproviderDashboard";
    }

    @GetMapping("/addbus")
    public String addBus(Model model)
    {

        return "addBus";
    }
}
