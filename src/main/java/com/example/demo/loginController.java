package com.example.demo;

import com.example.demo.BLL.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class loginController {


    @GetMapping("/login")
    public void showForm(Model model) {
        ClientService client = new ClientService();
        model.addAttribute("client", client);


    }

    @PostMapping("/login")
    public String submitForm(@ModelAttribute("client") ClientService cliente, HttpSession session) {
        ClientService checkLogin = new ClientService();
        checkLogin.readMail(cliente.getMail());

        if(checkLogin.getMail() != null){
           if(checkLogin.getMail().equals(cliente.getMail())){
               if(checkLogin.getPassword().equals(cliente.getPassword())){
                   System.out.println("Login Sucessfull");
                   session.setAttribute("Cliente_ID", checkLogin.getC_id());
                   System.out.println(checkLogin.getC_id());
                   return "redirect:/loja";
               }else{
                   System.out.println("Wrong password, type the correct one");
                   return "login";
               }
           }else{
               System.out.println("Wrong Email, type the correct one");
               return "login";
           }
        }else{
            System.out.println("Wrong Email, type the correct one");
            return "login";
        }


    }



}