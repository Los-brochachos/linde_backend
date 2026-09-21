package com.linde.linde_backend.services.cliente;

import java.util.List;

import org.springframework.stereotype.Service;

import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.repositories.cliente.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
 
public class ClienteService {
    //referencia 
    private final ClienteRepository clienteRepository;
    //listar
    public List <Cliente>listarClientes(){
        return clienteRepository.findAll();
    }
    //buscar
    public Cliente buscarCliente (Integer id){
        return clienteRepository.findById(id).orElse(null);
    
    }

    //guardar
    public Cliente guarCliente(Cliente cliente){
        return clienteRepository.save(cliente);

    }
    //eliminar
    public void eliminarCliente (Integer id){
        clienteRepository.deleteById(id);
    }

}
