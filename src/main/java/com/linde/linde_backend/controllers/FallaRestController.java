package com.linde.linde_backend.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.linde.linde_backend.services.FallaService;
import com.linde.linde_backend.entities.cisterna.Falla;


import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("api/v1/falla")
@RequiredArgsConstructor 
public class FallaRestController {
    private final FallaService service; 
    
    @GetMapping 
    public List<Falla> listar(){
        return service.listar();
    }

    @GetMapping("/idCisterna/{id}")
    public List <Falla> findByIdCisterna(@PathVariable Integer id){
        return service.findByCisterna_IdCisterna(id);
    }

    @PostMapping
    public Falla crear(
            @RequestParam String descripcion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHora,
            @RequestParam Integer idCisterna,
            @RequestParam Integer idConductor) {
        return service.create(descripcion, fechaHora, idCisterna, idConductor);
    }

    @PutMapping("/solucionar/{idFalla}")
    public Falla cambiarEstado(@PathVariable Integer idFalla) {
        return service.cambiarEstado(idFalla);
    }


}
