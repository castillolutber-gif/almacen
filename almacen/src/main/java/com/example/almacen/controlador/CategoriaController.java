package com.example.almacen.controlador;

import com.example.almacen.Repository.CategoriaRepository;
import com.example.almacen.formulario.CategoriaForm;
import com.example.almacen.modelo.Categoria;
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
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAllByOrderByNombreAsc());
        return "categorias/lista";
    }

    @GetMapping("/nuevo")
    public String nueva(Model model) {
        model.addAttribute("form", new CategoriaForm());
        return "categorias/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow();
        CategoriaForm form = new CategoriaForm();
        form.setId(categoria.getId());
        form.setNombre(categoria.getNombre());
        form.setDescripcion(categoria.getDescripcion());
        form.setEstado(categoria.isEstado());
        model.addAttribute("form", form);
        return "categorias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("form") CategoriaForm form,
                          BindingResult errores, Model model, RedirectAttributes mensajes) {
        categoriaRepository.findByNombreIgnoreCase(form.getNombre())
                .filter(existente -> !existente.getId().equals(form.getId()))
                .ifPresent(existente -> errores.rejectValue("nombre", "duplicado",
                        "Ya existe una categoría con ese nombre."));

        if (errores.hasErrors()) {
            return "categorias/formulario";
        }

        Categoria categoria = form.getId() == null
                ? new Categoria(form.getNombre().trim(), limpiar(form.getDescripcion()), form.isEstado())
                : categoriaRepository.findById(form.getId()).orElseThrow();
        categoria.setNombre(form.getNombre().trim());
        categoria.setDescripcion(limpiar(form.getDescripcion()));
        categoria.setEstado(form.isEstado());
        categoriaRepository.save(categoria);

        mensajes.addFlashAttribute("exito", "Categoría guardada correctamente.");
        return "redirect:/categorias";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes mensajes) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow();
        categoria.setEstado(!categoria.isEstado());
        categoriaRepository.save(categoria);
        mensajes.addFlashAttribute("exito", "Estado de la categoría actualizado.");
        return "redirect:/categorias";
    }

    private String limpiar(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }
}
