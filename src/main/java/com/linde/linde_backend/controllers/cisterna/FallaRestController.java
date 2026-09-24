package com.linde.linde_backend.controllers.cisterna;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.cisterna.EliminarFallaRequest;
import com.linde.linde_backend.dto.cisterna.FallaRequest;
import com.linde.linde_backend.dto.cisterna.FallaResponse;
import com.linde.linde_backend.services.cisterna.FallaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("api/v1/falla")
@RequiredArgsConstructor 
public class FallaRestController {
    private final FallaService service; 
    
    @GetMapping 
    public ResponseEntity<List<FallaResponse>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/idCisterna/{id}")
    public ResponseEntity<List <FallaResponse>>findByIdCisterna(@PathVariable Integer id){
        return ResponseEntity.ok(service.findByCisterna_IdCisterna(id));
    }

    @PostMapping
    public ResponseEntity<FallaResponse> crear(@Valid @RequestBody FallaRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/solucionar")
    public ResponseEntity<FallaResponse> cambiarEstado(@Valid @RequestBody EliminarFallaRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.cambiarEstado(request));
    }

    


}
