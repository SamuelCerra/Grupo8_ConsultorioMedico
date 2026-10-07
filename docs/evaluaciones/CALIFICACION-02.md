# Retroalimentación — Lista Simple (Momento 2)

**Grupo:** Grupo8 · **Proyecto:** Consultorio Médico

## Nota

| Criterio | Peso | Nota (0-5) |
|---|---|---|
| Identificación de las relaciones uno-a-muchos | 20 % | 5.0 |
| `ListaSimple<T>` integrada al `Service` | 30 % | 3.5 |
| Menú en consola funcional | 15 % | 4.0 |
| Reemplazo del arreglo previo, sin código muerto | 20 % | 4.0 |
| Buenas prácticas (commits y nombres) | 15 % | 3.5 |
| **Nota del laboratorio** | | **3.98** |

La nota se calcula así: cada criterio (0 a 5) se multiplica por su peso, se suman y el resultado queda en escala de 0 a 5 (79.5 sobre 100).

## 1. Relaciones uno-a-muchos (5.0)
**Lo que hicieron bien:**
- Eligieron `Paciente` → `Cita` y `Paciente` → `Consulta` (historial clínico). Ambas son relaciones reales del consultorio: un paciente tiene varias citas y varias consultas.
- Además, `Medico` guarda sus citas en una lista, lo cual tiene sentido.

## 2. `ListaSimple<T>` integrada al `Service` (3.5)
**Lo que hicieron bien:**
- `Paciente` guarda `ListaSimple<Cita>` y `ListaSimple<Consulta>` y las crean en el constructor.
- Los servicios usan `insertarFinal`, `buscarPorIndice` y `eliminarPorValor` para agregar, consultar y eliminar.
- La vista solo habla con los servicios, nunca con la lista.

**Lo que pueden mejorar:**
- Al eliminar una cita, `CitaService` solo la saca de su lista general. La cita sigue en la lista del paciente y en la del médico, así que los datos quedan desalineados.
- Las citas viven en dos sitios (la lista del servicio y la del paciente). Escojan un solo lugar para evitar inconsistencias.
- Al registrar una consulta, el dato `tratamiento` se recibe pero nunca se guarda.

## 3. Menú en consola (4.0)
**Lo que hicieron bien:**
- Hay menús separados para pacientes, citas y consultas, con crear, listar y eliminar.
- Las consultas se pueden registrar, ver en el historial y eliminar por paciente y fecha.
- Validan fechas mal escritas sin que el programa se caiga.

**Lo que pueden mejorar:**
- En citas solo se puede crear, listar todas y eliminar; falta buscar las citas de un paciente.
- Cada vez que se crea una cita se pide crear un médico nuevo con todos sus datos, aunque ya exista.

## 4. Reemplazo del arreglo previo (4.0)
**Lo que hicieron bien:**
- No quedó ningún arreglo ni `ArrayList` en el código; todo usa `ListaSimple<T>`.

**Lo que pueden mejorar:**
- `ListaSimple` tiene métodos repetidos que hacen lo mismo (`eliminarInicio` y `eliminarAlInicio`, `eliminarFinal` y `eliminarAlFinal`). Dejen uno solo.
- Hay piezas sin usar, como el atributo `consulta` de `Cita`.
- No siguieron la estructura de carpetas acordada en clase: `structures` quedó fuera de `model/`.

## 5. Buenas prácticas (3.5)
**Lo que hicieron bien:**
- Hicieron varios commits con mensajes claros (por ejemplo "agrega servicios de citas y pacientes").
- Los nombres de clases, métodos y variables siguen las convenciones de Java.

**Lo que pueden mejorar:**
- Trabajaron directamente sobre `main`; usen una rama de trabajo y únanla a `main` al terminar.
- La entrega final de las relaciones llegó en un solo commit grande ("Implementar relaciones 1:N con ListaSimple"). Es mejor un commit por cambio.
- El `README.md` sigue siendo el texto genérico de VS Code.

## ¿El programa funciona?
Sí. Compila sin errores y el menú arranca y responde bien al crear y listar pacientes. La falla principal es la cita eliminada que queda en las listas del paciente y del médico.

## Para el próximo laboratorio
- Al eliminar una cita, quítenla también de la lista del paciente y del médico.
- Guarden las citas en un solo lugar.
- Dejen una sola versión de cada método de `ListaSimple` y borren lo que no se usa.
- Organicen las carpetas como se acordó: `model/structures`.
- Trabajen en ramas y hagan commits pequeños y frecuentes.
