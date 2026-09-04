# Flujo Spring Boot

Aplicación web para administrar información de un sistema de transporte y parqueadero. El proyecto permite registrar, consultar, editar y eliminar carros, choferes, motores y pasajeros desde una interfaz web.

## Tecnologías

- Java 17
- Spring Boot 4.1.1
- Spring MVC
- Spring Data JPA / Hibernate
- Thymeleaf
- Bootstrap 5.3
- MySQL
- Maven Wrapper
- Lombok

## Funcionalidades

La aplicación cuenta con un panel principal y cuatro módulos de gestión:

| Módulo | Datos principales | Ruta |
| --- | --- | --- |
| Carros | Marca, modelo y placa única | `/carros` |
| Choferes | Nombre, cédula única y licencia | `/choferes` |
| Motores | Número de serie único, tipo y caballos de fuerza | `/motores` |
| Pasajeros | Nombre y cédula única | `/pasajeros` |

Cada módulo incluye operaciones para:

- Listar registros.
- Crear registros.
- Editar registros existentes.
- Eliminar registros.

## Requisitos previos

- JDK 17 o superior.
- MySQL Server, XAMPP o una instalación compatible.
- Git, si deseas clonar el repositorio.

## Configuración de la base de datos

1. Crea una base de datos MySQL llamada `tiendaparking`:

   ```sql
   CREATE DATABASE tiendaparking;
   ```

2. Revisa `src/main/resources/application.properties` y ajusta la conexión según tu entorno:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/tiendaparking
   spring.datasource.username=root
   spring.datasource.password=
   ```

   No publiques contraseñas reales en el repositorio. Para un entorno compartido, utiliza una configuración local o variables de entorno.

3. La propiedad `spring.jpa.hibernate.ddl-auto=update` permite que Hibernate actualice las tablas a partir de las entidades al iniciar la aplicación. Para producción, configura explícitamente una estrategia de migraciones y evita depender de esta opción.

## Instalación y ejecución

Clona el repositorio y entra en su carpeta:

```bash
git clone https://github.com/NeygerSerrano/Flujo-Springboot.git
cd Flujo-Springboot
```

En Windows ejecuta:

```powershell
   .\mvnw.cmd spring-boot:run
```

En macOS o Linux ejecuta:

```bash
./mvnw spring-boot:run
```

Cuando la aplicación esté iniciada, abre [http://localhost:8080](http://localhost:8080). También puedes ejecutar el proyecto desde tu IDE como una aplicación Spring Boot.

## Pruebas

Para ejecutar las pruebas automatizadas:

```powershell
   .\mvnw.cmd test
```

En macOS o Linux:

```bash
./mvnw test
```

## Estructura principal

```text
src/
├── main/
│   ├── java/com/springboot/app/flujo_springboot/
│   │   ├── controllers/       # Controladores MVC y rutas web
│   │   ├── models/            # Entidades JPA
│   │   └── repositories/      # Repositorios Spring Data
│   └── resources/
│       ├── templates/         # Vistas Thymeleaf
│       └── application.properties
└── test/                      # Pruebas de la aplicación
```

## Rutas principales

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/` | Panel principal |
| `GET` | `/carros` | Lista y formulario de carros |
| `POST` | `/carros/guardar` | Guarda o actualiza un carro |
| `GET` | `/carros/editar/{id}` | Carga un carro para editar |
| `GET` | `/carros/eliminar/{id}` | Elimina un carro |
| `GET` | `/choferes` | Lista y formulario de choferes |
| `POST` | `/choferes/guardar` | Guarda o actualiza un chofer |
| `GET` | `/motores` | Lista y formulario de motores |
| `POST` | `/motores/guardar` | Guarda o actualiza un motor |
| `GET` | `/pasajeros` | Lista y formulario de pasajeros |
| `POST` | `/pasajeros/guardar` | Guarda o actualiza un pasajero |

Las rutas de edición y eliminación de choferes, motores y pasajeros siguen el mismo patrón: `/{modulo}/editar/{id}` y `/{modulo}/eliminar/{id}`.

## Licencia

Este proyecto es de uso académico y personal. No se ha definido una licencia de código abierto en el repositorio.