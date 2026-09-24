package com.linde.linde_backend.controllers.cliente;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.cliente.ClienteRequest;
import com.linde.linde_backend.dto.cliente.ClienteResponse;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.services.cliente.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Validated
public class ClienteRestController {

    private final ClienteService clienteS;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(clienteS.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCliente(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                clienteS.buscarCliente(id)
        );
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(
            @Valid @RequestBody ClienteRequest request) {

        ClienteResponse cliente = clienteS.crearCliente(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaCliente(
            @PathVariable Integer id) {

        clienteS.eliminarCliente(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> actualizarCliente(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequest request) {

        ClienteResponse cliente = clienteS.actualizarCliente(id, request);

        return ResponseEntity.ok(cliente);
    }

}
