# Retroalimentación — Laboratorio 1: Codificación del diseño OO

**Grupo:** Grupo8 · **Proyecto:** Consultorio Médico
**Fecha límite:** 2026-09-08 23:59 · **Versión revisada:** commit `1fb1377`

¡Muy buen trabajo! El diseño quedó completo y la prueba muestra todo lo que se pedía.

## Nota

| Criterio | Peso | Nota (0-5) |
|---|---|---|
| El código sigue el diagrama UML | 60% | 5.0 |
| Pruebas: creación de objetos en el programa | 20% | 5.0 |
| Buenas prácticas de programación | 20% | 4.0 |
| **Nota del laboratorio** | | **4.80** |

La nota se calcula así: 60% diseño UML + 20% pruebas + 20% buenas prácticas.

## 1. El código sigue el diagrama UML (5.0)
**Lo que hicieron bien:**
- La interfaz `RolClinico` tiene justo los dos métodos del diagrama: `datosResumen()` y `rolEnConsulta()`.
- `Persona` es abstracta, implementa `RolClinico` y protege sus atributos. Su constructor no deja crear una persona sin identificación, y `datosResumen()` junta los tres datos.
- `Paciente` y `Medico` heredan de `Persona` y cada uno responde `rolEnConsulta()` a su manera, usando sus propios datos.
- Un `Paciente` guarda su lista de `Consulta` (puede tener cero o varias), tal como pide el diagrama.
- `Cita` conoce a su `Paciente` y a su `Medico` sin ser dueña de ellos, y `Consulta` está bien protegida con constructor, getters y setters.

**Lo que pueden mejorar:**
- Los setters no revisan los datos como sí lo hace el constructor. Por ejemplo, `setIdentificacion` deja poner una identificación vacía o `null` después de crear la persona.

## 2. Pruebas: creación de objetos (5.0)
**Lo que hicieron bien:**
- `PruebaCreacionObjetos` está fuera del paquete de dominio y crea un `Paciente` y un `Medico`.
- Agregan una `Consulta` al paciente y llaman `rolEnConsulta()` usando variables de tipo `Persona`, sin `instanceof`. Cada uno muestra un resultado distinto. Cumple todo lo pedido.

## 3. Buenas prácticas (4.0)
**Lo que hicieron bien:**
- Hicieron varios commits antes de la fecha límite, con mensajes claros y casi siempre con prefijos como `fix:`, `test:` o `chore:`.
- Los nombres de clases, métodos y variables siguen las costumbres de Java.

**Lo que pueden mejorar:**
- Todo el código se hizo directo en `main`. Lo ideal es que cada uno trabaje en su propia rama y luego la una a `main`.
- Casi todo el código lo subió una sola persona; la otra cuenta solo agregó el `README.md` y el `.gitignore`. El trabajo debe verse repartido entre todos.
- Las clases están en `com/itm/consultoriomedico/models/domain` y no en el paquete `model.domain` que acordamos en clase. Deben seguir la estructura acordada.

## ¿El programa funciona?
Sí. El proyecto compila sin errores y `PruebaCreacionObjetos` muestra bien los datos del paciente, del médico (cada uno con su propio rol), de la consulta y de la cita.

## Para el próximo laboratorio
- Mover las clases del dominio al paquete `model.domain`.
- Revisar los datos también en los setters, no solo en el constructor.
- Trabajar en ramas y unirlas a `main`, en vez de hacer todo directo en `main`.
- Repartir el código entre todos los integrantes, de modo que se vea en los commits.
