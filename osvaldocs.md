# 📘 OSVALDOCS: Guía Definitiva de Arquitectura Hexagonal (Nivel Inicial)

Esta guía está diseñada para que puedas construir un proyecto Spring Boot con Arquitectura Hexagonal desde cero, paso a paso, sin asumir conocimientos previos avanzados.

---

## 🏗️ FASE 0: PREPARACIÓN DEL ENTORNO

Antes de escribir código de negocio, necesitamos los cimientos.

### 1. Crear el Proyecto (Spring Initializr)
Ve a [start.spring.io](https://start.spring.io/) y selecciona:
*   **Project:** Maven
*   **Language:** Java
*   **Spring Boot:** 3.x (la última estable)
*   **Group:** `com.tuempresa`
*   **Artifact:** `nombre-del-proyecto` (ej: `library-service`)
*   **Dependencies (¡Clave!):**
    *   `Spring Web` (para hacer APIs REST)
    *   `Spring Data JPA` (para base de datos)
    *   `PostgreSQL Driver` (o H2 si es memoria)
    *   `Lombok` (para no escribir getters/setters)
    *   `Validation` (para validar datos)

Genera el ZIP, descárgalo y ábrelo en tu IDE (IntelliJ/VS Code).

### 2. Configurar Base de Datos (`application.properties`)
Abre `src/main/resources/application.properties` y pega esto (ajusta nombre de DB):

```properties
spring.application.name=nombre-del-proyecto
# Conexión a Base de Datos (Postgres local)
spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA (Para que cree las tablas solo)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Crear Estructura de Carpetas (Arquitectura)
Dentro de `src/main/java/com/tuempresa/proyecto`, crea estas carpetas vacías. **El orden es vital**:

```
├── application
│   ├── port
│   │   ├── in   (Lo que entra: Casos de Uso)
│   │   └── out  (Lo que sale: Repositorios)
│   └── service  (Donde vive la lógica)
├── domain
│   ├── model    (Tus objetos puros)
│   └── exception (Tus errores personalizados)
└── infrastructure
    ├── adapter
    │   ├── jpa
    │   │   ├── entity
    │   │   ├── mapper
    │   │   └── repository
    │   └── controller
    └── config
```

---

## 🧠 FASE 1: EL DOMINIO (Tu Negocio Puro)

Aquí definimos **QUÉ** es tu sistema. No uses anotaciones de Spring (`@Service`, `@Entity`) aquí. Solo Java.

### Paso 1.1: Crear el Modelo
Ejemplo: Vamos a hacer un sistema de **Libros**.
Archivo: `domain/model/Book.java`

```java
package com.tuempresa.proyecto.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String author;
    private boolean available;

    // Lógica de negocio: El libro sabe si se puede prestar
    public void lend() {
        if (!this.available) {
            throw new RuntimeException("El libro ya está prestado");
        }
        this.available = false;
    }
}
```

---

## 🔌 FASE 2: LOS PUERTOS (Contratos)

Aquí definimos las reglas del juego. Son **Interfaces**.

### Paso 2.1: Puerto de Salida (Para guardar datos)
Archivo: `application/port/out/BookRepositoryPort.java`

```java
package com.tuempresa.proyecto.application.port.out;

import com.tuempresa.proyecto.domain.model.Book;
import java.util.Optional;

public interface BookRepositoryPort {
    Book save(Book book);
    Optional<Book> findById(Long id);
}
```

### Paso 2.2: Puerto de Entrada (Para usar el sistema)
Archivo: `application/port/in/CreateBookUseCase.java`

```java
package com.tuempresa.proyecto.application.port.in;

import com.tuempresa.proyecto.domain.model.Book;

public interface CreateBookUseCase {
    Book createBook(Book book);
}
```

---

## ⚙️ FASE 3: LA APLICACIÓN (Servicios)

Aquí implementamos la lógica. Es el puente entre el "Qué quiero hacer" y "Cómo guardarlo".

### Paso 3.1: Implementar el Servicio
Archivo: `application/service/BookService.java`

```java
package com.tuempresa.proyecto.application.service;

import com.tuempresa.proyecto.application.port.in.CreateBookUseCase;
import com.tuempresa.proyecto.application.port.out.BookRepositoryPort;
import com.tuempresa.proyecto.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service // ¡Ahora sí usamos Spring!
@RequiredArgsConstructor
public class BookService implements CreateBookUseCase {

    private final BookRepositoryPort bookRepositoryPort; // Inyectamos el puerto

    @Override
    public Book createBook(Book book) {
        // Aquí podrías validar cosas antes de guardar
        book.setAvailable(true); // Por defecto disponible
        return bookRepositoryPort.save(book);
    }
}
```

---

## 🏗️ FASE 4: INFRAESTRUCTURA (JPA - Base de Datos)

Esta es la parte más "difícil" porque hay que conectar el mundo puro (Dominio) con el mundo sucio (Base de Datos).

### Paso 4.1: La Entidad de Base de Datos
Archivo: `infrastructure/adapter/jpa/entity/BookEntity.java`

```java
package com.tuempresa.proyecto.infrastructure.adapter.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private boolean available;
}
```

### Paso 4.2: El Repositorio de Spring
Archivo: `infrastructure/adapter/jpa/repository/BookJpaRepository.java`

```java
package com.tuempresa.proyecto.infrastructure.adapter.jpa.repository;

import com.tuempresa.proyecto.infrastructure.adapter.jpa.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
}
```

### Paso 4.3: El Mapper (Traductor)
Necesitamos convertir de `Book` (Dominio) a `BookEntity` (BD) y viceversa.
Archivo: `infrastructure/adapter/jpa/mapper/BookMapper.java`

*Nota: Asegúrate de tener la dependencia `mapstruct` en tu pom.xml.*

```java
package com.tuempresa.proyecto.infrastructure.adapter.jpa.mapper;

import com.tuempresa.proyecto.domain.model.Book;
import com.tuempresa.proyecto.infrastructure.adapter.jpa.entity.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toDomain(BookEntity entity);
    BookEntity toEntity(Book domain);
}
```

### Paso 4.4: El Adaptador JPA (El Enchufe)
Aquí conectamos todo. Implementamos el `BookRepositoryPort` usando las herramientas de Spring.
Archivo: `infrastructure/adapter/jpa/BookJpaAdapter.java`

```java
package com.tuempresa.proyecto.infrastructure.adapter.jpa;

import com.tuempresa.proyecto.application.port.out.BookRepositoryPort;
import com.tuempresa.proyecto.domain.model.Book;
import com.tuempresa.proyecto.infrastructure.adapter.jpa.entity.BookEntity;
import com.tuempresa.proyecto.infrastructure.adapter.jpa.mapper.BookMapper;
import com.tuempresa.proyecto.infrastructure.adapter.jpa.repository.BookJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookJpaAdapter implements BookRepositoryPort {

    private final BookJpaRepository jpaRepository;
    private final BookMapper mapper;

    @Override
    public Book save(Book book) {
        BookEntity entity = mapper.toEntity(book);
        BookEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
```

---

## 🌐 FASE 5: INFRAESTRUCTURA (REST - Controlador)

Finalmente, exponemos nuestra lógica al mundo (Postman/Frontend).

### Paso 5.1: El Controlador
Archivo: `infrastructure/controller/BookController.java`

```java
package com.tuempresa.proyecto.infrastructure.controller;

import com.tuempresa.proyecto.application.port.in.CreateBookUseCase;
import com.tuempresa.proyecto.domain.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final CreateBookUseCase createBookUseCase;

    @PostMapping
    public Book create(@RequestBody Book book) {
        return createBookUseCase.createBook(book);
    }
}
```

---

## 🚀 RESUMEN FINAL: ¿CÓMO FUNCIONA EL FLUJO?

Cuando alguien llama a `POST /api/books`:

1.  **Controller:** Recibe el JSON y llama al Caso de Uso (`CreateBookUseCase`).
2.  **Service:** Ejecuta la lógica (ej: validar) y llama al Puerto de Salida (`BookRepositoryPort`).
3.  **Adapter JPA:**
    *   Recibe el objeto de Dominio.
    *   Usa el **Mapper** para convertirlo a Entidad.
    *   Usa el **JpaRepository** para guardarlo en Postgres.
    *   Convierte la respuesta de vuelta a Dominio.
4.  **Service:** Devuelve el objeto guardado.
5.  **Controller:** Devuelve el JSON al usuario.

¡Y listo! Tienes una arquitectura limpia, desacoplada y profesional.
