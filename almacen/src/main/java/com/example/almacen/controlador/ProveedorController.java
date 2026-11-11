package com.example.almacen.controlador;

import com.example.almacen.Repository.ProveedorRepository;
import com.example.almacen.formulario.ProveedorForm;
import com.example.almacen.modelo.Proveedor;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorRepository proveedorRepository;

    public ProveedorController(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorRepository.findAllByOrderByRazonSocialAsc());
        return "proveedores/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("form", new ProveedorForm());
        return "proveedores/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();
        ProveedorForm form = new ProveedorForm();
        form.setId(proveedor.getId());
        form.setRazonSocial(proveedor.getRazonSocial());
        form.setRuc(proveedor.getRuc());
        form.setTelefono(proveedor.getTelefono());
        form.setCorreo(proveedor.getCorreo());
        form.setDireccion(proveedor.getDireccion());
        model.addAttribute("form", form);
        return "proveedores/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("form") ProveedorForm form,
                          BindingResult errores, Model model, RedirectAttributes mensajes) {
        String ruc = limpiar(form.getRuc());
        if (ruc != null) {
            proveedorRepository.findByRuc(ruc)
                    .filter(existente -> !existente.getId().equals(form.getId()))
                    .ifPresent(existente -> errores.rejectValue("ruc", "duplicado",
                            "Ya existe un proveedor con ese RUC."));
        }

        if (errores.hasErrors()) {
            return "proveedores/formulario";
        }

        Proveedor proveedor = form.getId() == null
                ? new Proveedor(form.getRazonSocial().trim(), ruc, limpiar(form.getTelefono()),
                    limpiar(form.getCorreo()), limpiar(form.getDireccion()))
                : proveedorRepository.findById(form.getId()).orElseThrow();
        proveedor.setRazonSocial(form.getRazonSocial().trim());
        proveedor.setRuc(ruc);
        proveedor.setTelefono(limpiar(form.getTelefono()));
        proveedor.setCorreo(limpiar(form.getCorreo()));
        proveedor.setDireccion(limpiar(form.getDireccion()));
        proveedorRepository.save(proveedor);

        mensajes.addFlashAttribute("exito", "Proveedor guardado correctamente.");
        return "redirect:/proveedores";
    }

    private String limpiar(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }
}
