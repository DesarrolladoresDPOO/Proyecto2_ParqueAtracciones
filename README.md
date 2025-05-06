# Proyecto 2 - Sistema de Administración Parque de Atracciones

## Integrantes  
1. **Jerónimo López** - 202320969  
2. **Juan Esteban Piñeros** - 202412232  
3. **Daniel Diab** - 202212289  

Diseño y Programación Orientada a Objetos (DPOO)  

---

## Descripción General

Este proyecto implementa un sistema de gestión para un parque de atracciones, desarrollado en Java con un enfoque de programación orientada a objetos. Permite la administración de atracciones, empleados, turnos y la venta de tiquetes a través de interfaces de consola diferenciadas por rol: administrador, empleado y cliente.

El sistema asegura la persistencia de datos mediante archivos CSV, cuenta con autenticación de usuarios, validación de entradas y pruebas automatizadas con JUnit.

# Estructura del Proyecto
```bash
Proyecto2_ParqueAtracciones/
├── src/
│   ├── Interfaz/             # Interfaces de consola por rol
│   ├── persona/              # Clases relacionadas con empleados y usuarios
│   ├── atracciones/          # Modelado de atracciones y espectáculos
│   ├── tiquetes/             # Gestión de diferentes tipos de tiquetes
│   ├── persistencia/         # Lectura y escritura de archivos CSV
│   └── pruebas/              # Pruebas unitarias e integración con JUnit
│
├── datos/
│   ├── empleados.csv
│   ├── clientes.csv
│   ├── atracciones.csv
│   ├── asistencias_clientes.csv
│   └── auth_admin.csv
│
├── README.md
└── Documento_de_Analisis_proyecto_2.pdf
```

## Funcionalidades Clave

- **Gestión de Atracciones y Espectáculos**  
  - Validación de acceso a atracciones mecánicas y culturales  
  - Evalúa si un cliente cumple con los requisitos de altura, peso o edad, y si tiene condiciones médicas que lo impidan.  
  - Disponibilidad según temporada o clima  
  - Consulta si una atracción o espectáculo está disponible en la fecha actual.  

- **Administración de Empleados**  
  - Se gestionan turnos diurnos y nocturnos para empleados con fecha, hora de inicio y fin.  
  - Verificación de turnos asignados  
  - Consultas personalizadas para saber si un empleado tiene turno en una fecha dada.  
  - Empleados como operadores mecánicos, cajeros o cocineros deben cumplir condiciones según su rol.
  - Registro de nuevos empleados especificando el tipo de estos.
  - Consulta de empleados actuales registrados en la aplicación. 

- **Venta y Validación de Tiquetes**  
  - Soporte para múltiples tipos de tiquete:  
    - Básico  
    - Familiar  
    - Oro  
    - Diamante  
  - Consulta de tiquetes por cliente  
  - Muestra los tiquetes asociados a cada cliente y si ya han sido usados.  
  - Compra simulada de tiquetes  
  - Los tiquetes pueden incluir acceso a diferentes atracciones o beneficios como FastPass.
    
- **Persistencia de datos**  
  - Permite la lectura y la escritura de datos provenientes de un archivo de tipo CSV donde esta contenida la información de los empleados.
  - Permite la lectura y la escritura de datos provenientes de un archivo de tipo CSV donde esta contenida la información de los clientes.
  - Se usa BufferedReader y se lee cada linea del documento para después filtrar los datos de esta y añadirlos al programa.
  - Se usa BufferedWriter para escribir en el archivo CSV los nuevos clientes y los nuevos empleados añadidos durante la ejecucio

## Pruebas

El proyecto incluye pruebas unitarias e integradas usando **JUnit 5**. Las pruebas cubren:

- Autenticación por rol
- Validación de restricciones en atracciones
- Compra y uso de tiquetes
- Consulta y asignación de turnos
- Registro de historial de asistencias

La cobertura supera el **80%** en clases funcionales principales (excluyendo getters/setters triviales).

---

## Persistencia

Los datos se almacenan en archivos CSV dentro de la carpeta `datos/`. Todos los cambios realizados en el sistema (registro, compras, turnos, etc.) se reflejan de forma persistente en estos archivos.
