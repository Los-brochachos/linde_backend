package com.linde.linde_backend.controllers.Cliente;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.entities.Cliente.cliente;
import com.linde.linde_backend.services.Cliente.clienteServices;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("api/cliente")
@RequiredArgsConstructor //para la inyeccion de dependencias

public class clienteController {
    private final clienteServices clienteS ; 
    //metodo para mostrar al cliente y crear al cliente 
    @GetMapping //mostar
    public List<cliente>listar (){
        return clienteS.listarClientes();
    }
    @GetMapping ("/{id}")
    public cliente buscarCliente (@PathVariable Integer id ){
        return clienteS.buscarCliente(id);
    }
    @PostMapping 
    public cliente crearCliente(@RequestBody cliente nuevocCliente){
        return clienteS.guarCliente(nuevocCliente);

    }
    @DeleteMapping ("/{id}")
    public void  eliminaCliente (@PathVariable Integer id){
         clienteS.eliminarCliente(id);
    }

}
