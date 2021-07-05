package com.example.demo;


import com.example.demo.BLL.ClientService;
import com.example.demo.BLL.OrderDetailsService;
import com.example.demo.BLL.OrderService;
import com.example.demo.BLL.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class perfilController {

    int client_id = 0;
    @GetMapping(value = "/Perfil_cliente")
    public String showCliente(Model model, HttpSession session){
        ClientService cliServe = new ClientService();
        OrderService ordServe = new OrderService();

        List<OrderService> ordens = new ArrayList<>();


        if(session.getAttribute("Cliente_ID") == null){
            return "redirect:/login";
        }else{
            client_id = (int) session.getAttribute("Cliente_ID");
            cliServe.read(client_id);
            model.addAttribute("Client", cliServe);

            ordens = ordServe.readFatura(client_id);
            for(OrderService ord: ordens){
                System.out.println(ord.getO_id());
            }
            model.addAttribute("Orders", ordens);
            System.out.println(ordens.size());


        }
        return "Perfil_cliente";
    }

    @PostMapping(value ="/Perfil_cliente")
    public void updateCliente(Model model, HttpSession session, @ModelAttribute("userInfo") ClientService client) throws SQLException {
        ClientService cliServe = new ClientService();
        if(client.getMail() != "" || client.getPassword() != "" || client.getName() != "" || client.getBirthdate() != null || client.getPhone() != 0){
              cliServe.updateClientOnly(client.getName(), client.getBirthdate(), client.getMail(), client.getPhone(), client.getPassword(), client_id);
              showCliente(model, session);
        }
    }
}
