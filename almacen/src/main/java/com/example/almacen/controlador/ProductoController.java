package com.example.almacen.controlador;

import com.example.almacen.Repository.CategoriaRepository;
import com.example.almacen.Repository.ProductoRepository;
import com.example.almacen.formulario.ProductoForm;
import com.example.almacen.modelo.Categoria;
import com.example.almacen.modelo.Producto;
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
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoRepository.findAllByOrderByNombreAsc());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("form", new ProductoForm());
        cargarCategorias(model);
        return "productos/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Producto producto = productoRepository.findById(id).orElseThrow();
        ProductoForm form = new ProductoForm();
        form.setId(producto.getId());
        form.setCodigo(producto.getCodigo());
        form.setNombre(producto.getNombre());
        form.setDescripcion(producto.getDescripcion());
        form.setPrecioCompra(producto.getPrecioCompra());
        form.setPrecioVenta(producto.getPrecioVenta());
        form.setStockMinimo(producto.getStockMinimo());
        form.setCategoriaId(producto.getCategoria().getId());
        form.setEstado(producto.isEstado());
        model.addAttribute("form", form);
        cargarCategorias(model);
        return "productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("form") ProductoForm form,
                          BindingResult errores, Model model, RedirectAttributes mensajes) {
        productoRepository.findByCodigoIgnoreCase(form.getCodigo())
                .filter(existente -> !existente.getId().equals(form.getId()))
                .ifPresent(existente -> errores.rejectValue("codigo", "duplicado",
                        "Ya existe un producto con ese código."));

        Categoria categoria = form.getCategoriaId() == null
                ? null : categoriaRepository.findById(form.getCategoriaId()).orElse(null);
        if (categoria == null || !categoria.isEstado()) {
            errores.rejectValue("categoriaId", "invalida", "Selecciona una categoría activa.");
        }

        if (errores.hasErrors()) {
            cargarCategorias(model);
            return "productos/formulario";
        }

        Producto producto = form.getId() == null
                ? new Producto(form.getCodigo().trim(), form.getNombre().trim(), limpiar(form.getDescripcion()),
                    form.getPrecioCompra(), form.getPrecioVenta(), form.getStockMinimo(), categoria, form.isEstado())
                : productoRepository.findById(form.getId()).orElseThrow();
        producto.setCodigo(form.getCodigo().trim());
        producto.setNombre(form.getNombre().trim());
        producto.setDescripcion(limpiar(form.getDescripcion()));
        producto.setPrecioCompra(form.getPrecioCompra());
        producto.setPrecioVenta(form.getPrecioVenta());
        producto.setStockMinimo(form.getStockMinimo());
        producto.setCategoria(categoria);
        producto.setEstado(form.isEstado());
        productoRepository.save(producto);

        mensajes.addFlashAttribute("exito", "Producto guardado. El stock se administra mediante movimientos.");
        return "redirect:/productos";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Integer id, RedirectAttributes mensajes) {
        Producto producto = productoRepository.findById(id).orElseThrow();
        producto.setEstado(!producto.isEstado());
        productoRepository.save(producto);
        mensajes.addFlashAttribute("exito", "Estado del producto actualizado.");
        return "redirect:/productos";
    }

    private void cargarCategorias(Model model) {
        model.addAttribute("categorias", categoriaRepository.findByEstadoTrueOrderByNombreAsc());
    }

    private String limpiar(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }
}
