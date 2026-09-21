package com.linde.linde_backend.controllers.cliente;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.services.cliente.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("api/cliente")
@RequiredArgsConstructor //para la inyeccion de dependencias

public class ClienteRestController {
    private final ClienteService clienteS ; 
    //metodo para mostrar al cliente y crear al cliente 
    @GetMapping //mostar
    public List<Cliente>listar (){
        return clienteS.listarClientes();
    }
    @GetMapping ("/{id}")
    public Cliente buscarCliente (@PathVariable Integer id ){
        return clienteS.buscarCliente(id);
    }
    @PostMapping 
    public Cliente crearCliente(@RequestBody Cliente nuevocCliente){
        return clienteS.guarCliente(nuevocCliente);

    }
    @DeleteMapping ("/{id}")
    public void  eliminaCliente (@PathVariable Integer id){
         clienteS.eliminarCliente(id);
    }

}
