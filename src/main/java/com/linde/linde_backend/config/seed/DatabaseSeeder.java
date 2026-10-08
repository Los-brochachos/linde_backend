package com.linde.linde_backend.config.seed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.linde.linde_backend.entities.atencion.*;
import com.linde.linde_backend.entities.cisterna.*;
import com.linde.linde_backend.entities.cliente.Cliente;
import com.linde.linde_backend.entities.documento.*;
import com.linde.linde_backend.entities.pedido.*;
import com.linde.linde_backend.entities.trabajador.*;
import com.linde.linde_backend.entities.usuario.Usuario;
import com.linde.linde_backend.utils.*;
import com.linde.linde_backend.utils.atencion.*;
import com.linde.linde_backend.utils.cisterna.EstadoFalla;
import com.linde.linde_backend.utils.pedido.*;
import com.linde.linde_backend.utils.trabajador.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Datos ficticios para una base de desarrollo vacia. */
@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private static final List<Class<?>> ENTIDADES = List.of(
        Usuario.class, Cliente.class, Trabajador.class, Conductor.class, Programador.class,
        Tecnico.class, Cisterna.class, Producto.class, Pedido.class, DetallePedido.class,
        SeguimientoPedido.class, Atencion.class, Alerta.class, Falla.class,
        HistorialMantenimiento.class, Factura.class, GuiaRemision.class);

    @Override
    @Transactional
    public void run(String... args) {
        for (Class<?> entidad : ENTIDADES) {
            Long cantidad = entityManager.createQuery(
                "select count(e) from " + entidad.getSimpleName() + " e", Long.class).getSingleResult();
            if (cantidad > 0) {
                log.info("Seeder omitido: {} ya contiene datos. La carga requiere tablas vacias.", entidad.getSimpleName());
                return;
            }
        }
        LocalDateTime ahora = LocalDateTime.now(ZoneId.of("America/Lima"));
        LocalDate hoy = ahora.toLocalDate();
        String password = passwordEncoder.encode("Linde1234*");
        trabajador("admin", RolesEnum.ADMIN, "Andrea", "Torres", 1, password, hoy);
        trabajador("analista", RolesEnum.ANALISTA, "Luis", "Ramos", 2, password, hoy);
        List<Conductor> conductores = new ArrayList<>();
        List<Programador> programadores = new ArrayList<>();
        List<Tecnico> tecnicos = new ArrayList<>();
        String[] nombres = {"Carlos", "Maria", "Jorge", "Lucia", "Pedro"};
        for (int i = 0; i < 5; i++) {
            conductores.add(guardar(Conductor.builder()
                .trabajador(trabajador("conductor" + (i + 1), RolesEnum.CONDUCTOR, nombres[i], "Quispe", 10 + i, password, hoy))
                .licenciaConducir("Q" + (70000000 + i)).categoriaLicencia(CategoriaLicencia.A_IIIC)
                .fechaVencimientoLicencia(hoy.plusYears(3)).build()));
            programadores.add(guardar(Programador.builder()
                .trabajador(trabajador("programador" + (i + 1), RolesEnum.PROGRAMADOR, nombres[i], "Flores", 20 + i, password, hoy))
                .nivelIngles(NivelIngles.B2).turno(Turno.values()[i % Turno.values().length]).build()));
            tecnicos.add(guardar(Tecnico.builder()
                .trabajador(trabajador("tecnico" + (i + 1), RolesEnum.TECNICO, nombres[i], "Medina", 30 + i, password, hoy))
                .especialidad(EspecialidadTecnico.values()[i]).nivelTecnico(NivelTecnico.values()[i % NivelTecnico.values().length]).build()));
        }
        List<Cliente> clientes = new ArrayList<>();
        String[] empresas = {"Clinica Demo Lima SAC", "Metalurgica Demo Sur SAC", "Alimentos Demo Peru SAC",
            "Laboratorio Demo Andino SAC", "Hospital Demo Norte SAC"};
        List<Cisterna> cisternas = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        String[] gases = {"Oxigeno medicinal", "Oxigeno industrial", "Nitrogeno", "Argon", "Dioxido de carbono"};
        String[] precios = {"8.50", "6.20", "4.80", "15.00", "5.50"};
        for (int i = 0; i < 5; i++) {
            clientes.add(guardar(Cliente.builder().ruc("2090000000" + (i + 1)).razonSocial(empresas[i])
                .direccion("Av. Industrial " + (100 + i) + ", Lima").telefono("91000000" + (i + 1))
                .usuario(usuario("cliente" + (i + 1), RolesEnum.CLIENTE, password)).build()));
            cisternas.add(guardar(Cisterna.builder().nombre("Cisterna Demo " + (i + 1)).placa("DEM-10" + i)
                .capacidad(new BigDecimal("20000.00")).estado(i == 4 ? Estado.INACTIVO : Estado.ACTIVO).build()));
            productos.add(guardar(Producto.builder().nombre(gases[i]).tipoGas(gases[i]).unidadMedida("m3")
                .precioUnitario(new BigDecimal(precios[i])).estado(Estado.ACTIVO).build()));
        }
        // Tres pedidos por estado, con historial cronologico y ultimo estado consistente.
        for (int i = 0; i < 15; i++) {
            EstadoPedido estado = EstadoPedido.values()[i % 5];
            LocalDateTime registro = hoy.minusDays(20 - i).atTime(8, 0);
            LocalDateTime traslado = estado == EstadoPedido.EN_PREPARACION ? hoy.plusDays(1 + i / 5).atTime(9, 0)
                : estado == EstadoPedido.DESPACHADO ? ahora.minusHours(1) : registro.plusDays(2).withHour(9);
            Pedido pedido = guardar(Pedido.builder().cliente(clientes.get(i % 5)).fechaRegistro(registro.toLocalDate())
                .fechaEntregaEstimada(traslado.toLocalDate()).prioridad(PrioridadPedido.values()[i % 3]).estado(estado).build());
            Producto producto = productos.get(i % 5);
            DetallePedido detalle = guardar(DetallePedido.builder().pedido(pedido).producto(producto)
                .cantidad(new BigDecimal("1500.00")).precioUnitario(producto.getPrecioUnitario())
                .estado(estado == EstadoPedido.CANCELADO ? Estado.INACTIVO : Estado.ACTIVO).build());
            pedido.setDetalles(List.of(detalle));
            seguimiento(pedido, EstadoPedido.RECIBIDO, registro);
            if (estado == EstadoPedido.CANCELADO) {
                seguimiento(pedido, estado, registro.plusHours(2));
            } else {
                List<EstadoPedido> flujo = List.of(EstadoPedido.RECIBIDO, EstadoPedido.EN_PREPARACION,
                    EstadoPedido.DESPACHADO, EstadoPedido.ENTREGADO);
                for (EstadoPedido paso : flujo.subList(1, flujo.size())) {
                    if (flujo.indexOf(paso) <= flujo.indexOf(estado)) {
                        seguimiento(pedido, paso, paso == EstadoPedido.EN_PREPARACION ? registro.plusHours(2)
                            : paso == EstadoPedido.DESPACHADO ? traslado : traslado.plusHours(3));
                    }
                }
            }
            if (estado == EstadoPedido.EN_PREPARACION || estado == EstadoPedido.DESPACHADO || estado == EstadoPedido.ENTREGADO) {
                EstadoAtencion ea = estado == EstadoPedido.ENTREGADO ? EstadoAtencion.FINALIZADA
                    : estado == EstadoPedido.DESPACHADO ? EstadoAtencion.EN_CURSO : EstadoAtencion.PROGRAMADA;
                Atencion atencion = guardar(Atencion.builder().pedido(pedido).conductor(conductores.get(i % conductores.size()))
                    .programador(programadores.get(i % programadores.size())).cisterna(cisternas.get(i % 4))
                    .fechaInicioProgramada(traslado).fechaFinProgramada(traslado.plusHours(3))
                    .fechaInicio(ea == EstadoAtencion.PROGRAMADA ? null : traslado)
                    .fechaFin(ea == EstadoAtencion.FINALIZADA ? traslado.plusHours(3) : null)
                    .estado(ea).observaciones("Entrega de demostracion").build());
                guardar(Alerta.builder().atencion(atencion).tipo(TipoAlerta.RETRASO)
                    .mensaje("Demora reportada en ruta de demostracion")
                    .fechaHora(ea == EstadoAtencion.PROGRAMADA ? ahora : traslado.plusMinutes(15))
                    .estado(ea == EstadoAtencion.FINALIZADA ? EstadoAlerta.ATENDIDA : EstadoAlerta.PENDIENTE).build());
            }
            if (estado == EstadoPedido.DESPACHADO || estado == EstadoPedido.ENTREGADO) {
                guardar(Factura.builder().pedido(pedido).numero("FDEM-" + String.format("%08d", i + 1))
                    .fechaEmision(traslado.toLocalDate()).tipoComprobante("FACTURA").moneda("PEN").build());
                guardar(GuiaRemision.builder().pedido(pedido).numero("TDEM-" + String.format("%08d", i + 1))
                    .fechaEmision(traslado.toLocalDate()).fechaInicioTraslado(traslado.toLocalDate())
                    .motivoTraslado("Venta").puntoPartida("Planta Demo, Callao")
                    .puntoLlegada(pedido.getCliente().getDireccion()).build());
            }
        }
        // Ocho fallas: tres pendientes y cinco con historial de mantenimiento.
        for (int i = 0; i < 8; i++) {
            Falla falla = guardar(Falla.builder().cisterna(cisternas.get(4)).tecnico(tecnicos.get(i % tecnicos.size()))
                .descripcion("Revision de componente de prueba " + (i + 1)).fechaHora(ahora.minusDays(10 - i))
                .estado(EstadoFalla.values()[i % EstadoFalla.values().length]).build());
            if (falla.getEstado() != EstadoFalla.PENDIENTE) {
                guardar(HistorialMantenimiento.builder().falla(falla).fecha(falla.getFechaHora().plusHours(2))
                    .descripcion("Inspeccion y ajuste del componente")
                    .resultado(falla.getEstado() == EstadoFalla.RESUELTA ? "Reparacion completada" : "Reparacion en curso")
                    .observaciones("Registro de demostracion").costo(new BigDecimal("350.00")).build());
            }
        }
        entityManager.flush();
        log.info("Seeder de desarrollo: datos insertados en las 17 tablas de negocio.");
        // Los tokens se generan mediante el login real.
    }

    private Usuario usuario(String cuenta, RolesEnum rol, String password) {
        return guardar(Usuario.builder().correo(cuenta + "@linde.example").contraseña(password)
            .estado(Estado.ACTIVO).rol(rol).build());
    }

    private Trabajador trabajador(String cuenta, RolesEnum rol, String nombres, String apellidos,
            int numero, String password, LocalDate hoy) {
        return guardar(Trabajador.builder().usuario(usuario(cuenta, rol, password)).nombres(nombres).apellidos(apellidos)
            .dni(String.format("%08d", 90000000 + numero)).telefono("920000001").direccion("Av. Demo 123, Lima")
            .fechaIngreso(hoy.minusYears(2)).estado(Estado.ACTIVO).build());
    }

    private void seguimiento(Pedido pedido, EstadoPedido estado, LocalDateTime fecha) {
        guardar(SeguimientoPedido.builder().pedido(pedido).estado(estado).fechaHora(fecha)
            .observacion("Estado de demostracion: " + estado.name()).build());
    }

    private <T> T guardar(T entidad) {
        entityManager.persist(entidad);
        return entidad;
    }
}
