package com.example.demo;

import com.example.demo.BLL.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.sql.SQLException;



@Controller
public class registerController {

    @GetMapping("/register")
    public String showForm(Model model) {
        ClientService client = new ClientService();
        String confirmPassword = "";
        model.addAttribute("client", client);


        return "register";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("client") ClientService cliente) throws SQLException {
        ClientService checkEqualMail = new ClientService();
        ClientService client = new ClientService();
        client.readMail(cliente.getMail());

        if(client.getMail() != null){
            System.out.println("This email already exists, try another one!");

        }else{
            if(cliente.getMail().equals(client.getMail())){
                System.out.println("This email already exists, try another one!");
            }else{
                checkEqualMail.createClientOnly(cliente.getName(), cliente.getBirthdate(), cliente.getMail(), cliente.getPhone(), cliente.getPassword());
                System.out.println("User created sucessfully");
                return "login";
            }
        }
        return "register";
    }

}
