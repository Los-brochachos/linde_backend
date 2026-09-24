package com.linde.linde_backend.controllers.cisterna;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.linde.linde_backend.dto.cisterna.CisternaRequest;
import com.linde.linde_backend.dto.cisterna.CisternaResponse;
import com.linde.linde_backend.dto.cisterna.EditarCisternaRequest;
import com.linde.linde_backend.dto.cisterna.EliminarCisternaRequest;
import com.linde.linde_backend.services.cisterna.CisternaService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("api/v1/cisternas")
@RequiredArgsConstructor 
public class CisternaRestController {
    
    private final CisternaService service;

    @GetMapping 
    public ResponseEntity<List<CisternaResponse>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/placa/{placa}") 
    public ResponseEntity<CisternaResponse> findByPlaca(@PathVariable String placa){
        return service.findByPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CisternaResponse> findByNombre(@PathVariable String nombre){
        return service.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping 
    public ResponseEntity<CisternaResponse> crear(@Valid @RequestBody CisternaRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @PutMapping
    public ResponseEntity<CisternaResponse> editar(@Valid @RequestBody EditarCisternaRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.editar(request));
    }

    @DeleteMapping
    public ResponseEntity<CisternaResponse> eliminar(@Valid @RequestBody EliminarCisternaRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.eliminar(request));
    }
}
