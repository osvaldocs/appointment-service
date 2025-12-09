# ⚡ GUÍA RÁPIDA (RESUMEN) - EXAMEN HEXAGONAL

## 1. Orden de Batalla (Secuencia Estricta)
1.  **Configuración:** `pom.xml` (dependencias) + `application.properties` (DB).
2.  **Dominio (Core):** `Model` (Clases puras) -> `Port IN` (Interfaces UseCase) -> `Port OUT` (Interfaces Repository).
3.  **Aplicación (Lógica):** `Service` (Implementa UseCase, usa Port OUT).
4.  **Infraestructura (Adaptadores):**
    *   `Entity` (JPA) -> `Repository` (Spring Data) -> `Mapper` -> `Adapter` (Implementa Port OUT).
    *   `Controller` (REST) -> Llama a UseCase.
5.  **Seguridad:** `UserEntity` -> `JwtService` -> `Filter` -> `Config`.

## 2. Estructura de Carpetas (¡Crea esto primero!)
```
com.example.project
├── application
│   ├── port
│   │   ├── in   (Casos de Uso)
│   │   └── out  (Repositorios)
│   └── service  (Lógica @Service)
├── domain
│   ├── model    (POJOs puros)
│   └── exception
└── infrastructure
    ├── adapter
    │   ├── jpa
    │   │   ├── entity
    │   │   ├── mapper
    │   │   └── repository
    │   └── security
    ├── config
    └── controller
```

---

# 📘 GUÍA COMPLETA "COPIAR Y PEGAR" (TEMPLATE UNIVERSAL)

*Instrucciones: Reemplaza `Objeto` por tu entidad (ej: `Libro`, `Pedido`) y `Campo` por sus atributos.*

## PASO 1: DOMINIO (Puro Java)

### 1.1 Modelo (`domain/model/Objeto.java`)
```java
package com.example.project.domain.model;

public class Objeto {
    private Long id;
    private String campo;
    // Constructor vacío, Constructor lleno, Getters y Setters
    
    // Lógica de negocio (opcional pero recomendada)
    public void validar() {
        if (campo == null) throw new RuntimeException("Error");
    }
}
```

### 1.2 Puerto de Salida (`application/port/out/ObjetoRepositoryPort.java`)
```java
package com.example.project.application.port.out;
import com.example.project.domain.model.Objeto;
import java.util.Optional;

public interface ObjetoRepositoryPort {
    Objeto save(Objeto objeto);
    Optional<Objeto> findById(Long id);
}
```

### 1.3 Puerto de Entrada (`application/port/in/CrearObjetoUseCase.java`)
```java
package com.example.project.application.port.in;
import com.example.project.domain.model.Objeto;

public interface CrearObjetoUseCase {
    Objeto crear(Objeto objeto);
}
```

## PASO 2: APLICACIÓN (Servicios)

### 2.1 Servicio (`application/service/ObjetoService.java`)
```java
package com.example.project.application.service;

import com.example.project.application.port.in.CrearObjetoUseCase;
import com.example.project.application.port.out.ObjetoRepositoryPort;
import com.example.project.domain.model.Objeto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ObjetoService implements CrearObjetoUseCase {

    private final ObjetoRepositoryPort repositoryPort;

    @Override
    @Transactional
    public Objeto crear(Objeto objeto) {
        // Validaciones o lógica extra aquí
        return repositoryPort.save(objeto);
    }
}
```

## PASO 3: INFRAESTRUCTURA (JPA)

### 3.1 Entidad JPA (`infrastructure/adapter/jpa/entity/ObjetoEntity.java`)
```java
package com.example.project.infrastructure.adapter.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "objetos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ObjetoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String campo;
}
```

### 3.2 Repositorio Spring (`infrastructure/adapter/jpa/repository/ObjetoJpaRepository.java`)
```java
package com.example.project.infrastructure.adapter.jpa.repository;
import com.example.project.infrastructure.adapter.jpa.entity.ObjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjetoJpaRepository extends JpaRepository<ObjetoEntity, Long> {
}
```

### 3.3 Mapper (`infrastructure/adapter/jpa/mapper/ObjetoMapper.java`)
```java
package com.example.project.infrastructure.adapter.jpa.mapper;
import com.example.project.domain.model.Objeto;
import com.example.project.infrastructure.adapter.jpa.entity.ObjetoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjetoMapper {
    Objeto toDomain(ObjetoEntity entity);
    ObjetoEntity toEntity(Objeto domain);
}
```

### 3.4 Adaptador (`infrastructure/adapter/jpa/ObjetoJpaAdapter.java`)
```java
package com.example.project.infrastructure.adapter.jpa;

import com.example.project.application.port.out.ObjetoRepositoryPort;
import com.example.project.domain.model.Objeto;
import com.example.project.infrastructure.adapter.jpa.entity.ObjetoEntity;
import com.example.project.infrastructure.adapter.jpa.mapper.ObjetoMapper;
import com.example.project.infrastructure.adapter.jpa.repository.ObjetoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ObjetoJpaAdapter implements ObjetoRepositoryPort {

    private final ObjetoJpaRepository jpaRepository;
    private final ObjetoMapper mapper;

    @Override
    public Objeto save(Objeto objeto) {
        ObjetoEntity entity = mapper.toEntity(objeto);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Objeto> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
```

## PASO 4: INFRAESTRUCTURA (REST)

### 4.1 Controlador (`infrastructure/controller/ObjetoController.java`)
```java
package com.example.project.infrastructure.controller;

import com.example.project.application.port.in.CrearObjetoUseCase;
import com.example.project.domain.model.Objeto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/objetos")
@RequiredArgsConstructor
public class ObjetoController {

    private final CrearObjetoUseCase crearUseCase;

    @PostMapping
    public ResponseEntity<Objeto> crear(@RequestBody Objeto objeto) {
        return ResponseEntity.ok(crearUseCase.crear(objeto));
    }
}
```

## PASO 5: DEPENDENCIAS CLAVE (`pom.xml`)
*Asegúrate de tener estas dependencias para que el código de arriba funcione:*
1.  `spring-boot-starter-data-jpa`
2.  `spring-boot-starter-web`
3.  `postgresql` (runtime)
4.  `lombok` (optional)
5.  `mapstruct` (y su procesador en el plugin maven-compiler).
