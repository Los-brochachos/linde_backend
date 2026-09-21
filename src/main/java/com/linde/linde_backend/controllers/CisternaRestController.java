package com.linde.linde_backend.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import com.linde.linde_backend.services.CisternaService;
import com.linde.linde_backend.entities.cisterna.Cisterna;


@RestController 
@RequestMapping ("api/v1/cisterna")
@RequiredArgsConstructor 
public class CisternaRestController {
    private final CisternaService service;
    @GetMapping 
    public List<Cisterna> listar(){
        return service.listar();
    }

    @GetMapping("/placa/{placa}") 
    public Optional <Cisterna> findByPlaca(@PathVariable String placa){
        return service.findByPlaca(placa);
    }

    @GetMapping ("/nombre/{nombre}")
    public Optional <Cisterna> findByNombre(@PathVariable String nombre){
        return service.findByNombre(nombre);
    }

    @PostMapping 
    public Cisterna crear(@RequestParam String placa,@RequestParam String nombre, @RequestParam BigDecimal capacidad){
        return service.crear(placa, nombre, capacidad);
    }

    @PutMapping ("/{nombre}")
    public Cisterna editar(@RequestParam String placa,@PathVariable String nombre, @RequestParam BigDecimal capacidad){
        return service.editar(placa, nombre, capacidad);
    }

    @DeleteMapping ("/{nombre}")
    public Cisterna eliminar(@PathVariable String nombre){
        return service.eliminar(nombre);
    }

}
