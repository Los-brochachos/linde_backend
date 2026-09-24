package com.linde.linde_backend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.entities.cisterna.Falla;
import com.linde.linde_backend.services.FallaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/fallas")
@RequiredArgsConstructor
public class FallaRestController {

    private final FallaService service;

    @GetMapping
    public List<Falla> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Falla obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @GetMapping("/cisterna/{idCisterna}")
    public List<Falla> listarPorCisterna(@PathVariable Integer idCisterna) {
        return service.listarPorCisterna(idCisterna);
    }

    @PostMapping
    public Falla reportar(@RequestParam Integer idCisterna,
                           @RequestParam Integer idConductor,
                           @RequestParam String descripcion) {
        return service.reportar(idCisterna, idConductor, descripcion);
    }

    @PatchMapping("/{id}/estado")
    public Falla actualizarEstado(@PathVariable Integer id, @RequestParam String estado) {
        return service.actualizarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}