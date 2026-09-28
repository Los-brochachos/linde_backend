package com.linde.linde_backend.controllers.trabajador;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linde.linde_backend.dto.trabajador.TrabajadorResponse;
import com.linde.linde_backend.services.trabajador.TrabajadorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/trabajadores")
@RequiredArgsConstructor
public class TrabajadorRestController {

    private final TrabajadorService trabajadorService;

    @GetMapping
    public ResponseEntity<List<TrabajadorResponse>> listarTodos() {

        return ResponseEntity.ok(
                trabajadorService.listarTodos()
        );
    }

    @GetMapping("/activos")
    public ResponseEntity<List<TrabajadorResponse>> listarActivos() {

        return ResponseEntity.ok(
                trabajadorService.listarActivos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorResponse> buscarPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                trabajadorService.buscarPorId(id)
        );
    }

    @GetMapping("/{id}/activo")
    public ResponseEntity<TrabajadorResponse> buscarActivoPorId(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                trabajadorService.buscarActivoPorId(id)
        );
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<TrabajadorResponse> buscarPorDni(
            @PathVariable String dni) {

        return ResponseEntity.ok(
                trabajadorService.buscarPorDni(dni)
        );
    }

    @GetMapping("/dni/{dni}/activo")
    public ResponseEntity<TrabajadorResponse> buscarActivoPorDni(
            @PathVariable String dni) {

        return ResponseEntity.ok(
                trabajadorService.buscarActivoPorDni(dni)
        );
    }
}