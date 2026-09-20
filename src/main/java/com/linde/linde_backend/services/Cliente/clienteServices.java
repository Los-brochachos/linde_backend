package com.linde.linde_backend.services.Cliente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.entities.Cliente.cliente;
import com.linde.linde_backend.repositories.Cliente.clienteRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
 
public class clienteServices {
    //referencia 
    private final clienteRepository clienteRepository;
    //listar
    public List <cliente>listarClientes(){
        return clienteRepository.findAll();
    }
    //buscar
    public cliente buscarCliente (Integer id){
        return clienteRepository.findById(id).orElse(null);
    
    }

    //guardar
    public cliente guarCliente(cliente cliente){
        return clienteRepository.save(cliente);

    }
    //eliminar
    public void eliminarCliente (Integer id){
        clienteRepository.deleteById(id);
    }

}
