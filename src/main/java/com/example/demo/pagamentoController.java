package com.example.demo;

import com.example.demo.BLL.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class pagamentoController {
    public List<PaymentService> methods = new ArrayList<>();
    public PaymentService payment = new PaymentService();
    public List<ProductService> prodCarrinho = new ArrayList<>();
    public lojaController carrinho = new lojaController();

    @GetMapping(value = "/pagamento")
    public String showProdutosCarrinho(Model model, HttpSession session, @ModelAttribute("metodo") String metodo) {
        if(session.getAttribute("Cliente_ID") != null) {
            methods = payment.readAllPayments();
            float total = 0;
            prodCarrinho = (List<ProductService>) session.getAttribute("carrinho");
            prodCarrinho.size();
            for (ProductService prod : prodCarrinho) {
                total += (prod.getPrice_un() * prod.getQuantityRequested());
            }
            session.setAttribute("totalCarrinho", total);
            model.addAttribute("methods", methods);
            model.addAttribute("totalCarrinho", total);
            model.addAttribute("carrinho", prodCarrinho);
            model.addAttribute("metodo", metodo);

            System.out.println(this.prodCarrinho.size());
        }else{
            return "redirect:/login";
        }

        return "pagamento";
    }

    @PostMapping(value="/pagamento")
    public String escolherPagamento(@ModelAttribute("metodoEscolha") PaymentService metodo,  Model model, HttpSession session){
        System.out.println("Cheguei escolherPagamento" + metodo.getMethod());
        session.setAttribute("metodo", metodo.getMethod());
        model.addAttribute("metodo", metodo.getMethod());
        showProdutosCarrinho(model, session, metodo.getMethod());
        return "/pagamento";
    }


    @PostMapping(value="/pagamento/carrinho")
    public String encomendarCarrinho(HttpSession session) throws SQLException {
        int id_Cliente = (int)session.getAttribute("Cliente_ID");
        int id_Order;
        float total = 0;
        String metodo = (String) session.getAttribute("metodo");
        total = (float) session.getAttribute("totalCarrinho");

        OrderService order = new OrderService();
        OrderDetailsService orderDetail = new OrderDetailsService();
        ReceiptService receipt = new ReceiptService();
        PaymentService pay = new PaymentService();
        ProductService prodserve = new ProductService();
        System.out.println(metodo);
        pay.readPayment(metodo);
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        id_Order = order.create(date, id_Cliente, total);
        for(ProductService prod: prodCarrinho){
            orderDetail.create(prod.getQuantityRequested(), prod.getPrice_un(), prod.getProduct_id(), id_Order);
            prodserve.update((prod.getPr_quantity() - prod.getQuantityRequested()), prod.getProduct_id());
        }
        receipt.create(id_Order, pay.getPayment_id());

        return "redirect:/loja";
    }


}
