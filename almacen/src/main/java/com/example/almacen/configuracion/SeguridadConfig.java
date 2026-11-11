package com.example.almacen.configuracion;

import com.example.almacen.Repository.UsuarioRepository;
import com.example.almacen.modelo.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return correo -> {
            Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correo)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado."));

            boolean passwordValido = usuario.getPasswordHash() != null
                    && usuario.getPasswordHash().startsWith("$2");

            return User.withUsername(usuario.getCorreo())
                    .password(usuario.getPasswordHash())
                    .roles(usuario.getRol().name())
                    .disabled(!usuario.isEstado() || !passwordValido)
                    .build();
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(autorizacion -> autorizacion
                        .requestMatchers("/login", "/configurar-admin", "/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/categorias/nuevo", "/categorias/*/editar",
                                "/proveedores/nuevo", "/proveedores/*/editar",
                                "/productos/nuevo", "/productos/*/editar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/categorias/**", "/proveedores/**", "/productos/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/categorias/**", "/proveedores/**", "/productos/**")
                        .authenticated()
                        .anyRequest().authenticated())
                .formLogin(formulario -> formulario
                        .loginPage("/login")
                        .defaultSuccessUrl("/productos", true)
                        .permitAll())
                .logout(cierre -> cierre
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
}
