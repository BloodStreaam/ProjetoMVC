package com.example.demo;
import static javax.swing.JOptionPane.showMessageDialog;
import com.example.demo.BLL.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class lojaController {

    List<ProductService> products;
    public ProductService carrinhoServe = new ProductService();
    public List<ProductService> prodCarrinho = new ArrayList<>();


    @GetMapping(value = "/loja")
    public void showProdutos(Model model) {
        ProductService prodAdded = new ProductService();
        ProductService prod = new ProductService();

        products = prod.readAll();
        model.addAttribute("carrinho", this.prodCarrinho);
        model.addAttribute("products", products);
        model.addAttribute("product", prodAdded);

    }




    @PostMapping(value= "/loja")
    public void submitQuantidadeProduto(@ModelAttribute("userFormData") ProductService produto, Model model, HttpSession session) {
        boolean checkProduto = false;
        
        System.out.println(this.prodCarrinho.size());
        System.out.println("Nome " + produto.getName() + "stock " + produto.getPr_quantity() + "quantidade "+ produto.getQuantityRequested());
        if(this.prodCarrinho.size() > 0){
            for(ProductService prod: this.prodCarrinho){
                 if(prod.getProduct_id() == produto.getProduct_id()){
                     checkProduto = true;
                    System.out.println("This product already exist in the cart");
                    break;
                 }
            }

            if(checkProduto == false){
                this.prodCarrinho.add(produto);
                System.out.println("Product added sucefully");

            }
        }else{
            this.prodCarrinho.add(produto);

            System.out.println("Product added sucefully");

        }

        model.addAttribute("carrinho", this.prodCarrinho);
        session.setAttribute("carrinho", this.prodCarrinho);

        showProdutos(model);


    }

    @RequestMapping("/loja/remove")
    @ResponseBody
    public void removeFromCart(Model model, @ModelAttribute("remove_update") ProductService produto, HttpSession session, HttpServletResponse httpResponse) throws IOException {

        showProdutos(model);
        System.out.println("Elimiando" + produto.getProduct_id());

        this.prodCarrinho = (List<ProductService>) session.getAttribute("carrinho");
        if(!this.prodCarrinho.isEmpty()) {
            for (ProductService prod : this.prodCarrinho) {
                if (produto.getProduct_id() == prod.getProduct_id()) {
                    System.out.println("ProdCarrinho: " + prod.getProduct_id());
                    this.prodCarrinho.remove(prod);
                    System.out.println("Product Removed Sucessfully");
                    session.setAttribute("carrinho", this.prodCarrinho);
                    model.addAttribute("carrinho", this.prodCarrinho);

                    showProdutos(model);
                    httpResponse.sendRedirect("/loja");
                }
            }

        }else{
            httpResponse.sendRedirect("/loja");
        }
    }






}
