# Sistema de gestión de inventario

Aplicación web construida con Spring Boot, Java y Thymeleaf para trabajar con la base MariaDB existente `bd_inventario`.

## Antes de iniciar

1. Inicia MariaDB/MySQL desde XAMPP y confirma que la base `bd_inventario` está disponible en el puerto 3306.
2. Abre este proyecto en IntelliJ IDEA y espera a que Maven termine de cargar las dependencias.
3. En la configuración de ejecución de `AlmacenApplication`, agrega estas variables de entorno si tu instalación local usa XAMPP con el usuario `root` sin contraseña:

   ```text
   DB_USERNAME=root
   DB_PASSWORD=
   ```

   Si tu servidor usa otra cuenta o contraseña, coloca esos valores locales en lugar de los de ejemplo. No guardes credenciales reales en el código.

4. Ejecuta `AlmacenApplication` desde IntelliJ o inicia el proyecto con el wrapper Maven `mvnw.cmd spring-boot:run`.
5. Abre `http://localhost:8080`.
6. En el inicio de sesión, selecciona **Configurar el primer administrador**. Completa el formulario desde este mismo equipo. La contraseña debe tener al menos 12 caracteres.
7. Inicia sesión con el correo y contraseña que acabas de registrar.

La configuración inicial agrega un nuevo administrador cuando se envía el formulario. No cambia las cuentas existentes. El enlace de configuración desaparece cuando se guarda un administrador activo con contraseña BCrypt.

## Protección de la base de datos

Hibernate está configurado con `spring.jpa.hibernate.ddl-auto=validate`: verifica que las entidades correspondan a las tablas existentes, sin crear, modificar ni borrar tablas. La aplicación no ejecuta `db.diego.sql`; ese archivo contiene instrucciones destructivas y no debe importarse sobre la base existente.

Las entradas y salidas de inventario actualizan el stock mediante los triggers que ya existen en la base. Por eso el formulario de productos no permite editar directamente el stock.

## Funciones incluidas

- Inicio de sesión y permisos de administrador/empleado.
- Administración de usuarios, disponible para administradores.
- Consulta y mantenimiento de categorías, proveedores y productos.
- Registro de entradas y salidas con detalles por producto.
- Interfaz en español con Bootstrap.

## Versiones

- Java 21 como versión objetivo de compilación.
- Spring Boot 4.1.2.
- MariaDB Connector/J.

