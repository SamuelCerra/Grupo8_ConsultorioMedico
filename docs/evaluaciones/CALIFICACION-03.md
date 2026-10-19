# Retroalimentación — Laboratorio de Pilas y Colas

**Grupo:** Grupo8 · **Proyecto:** Consultorio Médico

Muy buen trabajo: las dos estructuras funcionan y el proyecto compila y corre.

## Nota

| Pilas | Puntos | Colas | Puntos |
|---|---|---|---|
| `Pila<T>` correcta | 28/30 | `Cola<T>` correcta | 30/30 |
| Casos límite | 6/10 | Casos límite | 10/10 |
| Integración en entidad y `Service` | 25/30 | UML y entidad dueña | 12/15 |
| Menú en `view/` | 14/15 | Integración en el `Service` | 20/25 |
| Buenas prácticas | 10/15 | Menú en `view/` | 6/10 |
| | | Buenas prácticas | 3/10 |
| **Total Pilas** | **83/100** | **Total Colas** | **81/100** |

**Nota del laboratorio: 4.10**

La nota es el promedio de los dos totales (Pilas y Colas) dividido entre 20.

## Pilas (83/100)
**Lo que hicieron bien:**
- `Pila<T>` con nodos enlazados, todas las operaciones directas (sin ciclos) y error claro al hacer `pop` o `peek` con la pila vacía.
- La pila vive como atributo privado en `Paciente` y el `Service` tiene los cinco métodos más `listarOperaciones`, sin mostrar la pila a la vista.
- Deshacer retira la consulta de la pila y también del historial del paciente, y listar deja la pila igual que antes.
- El menú usa solo `ConsoleUtils`, sin `try/catch`, y avisa cuando no hay nada que deshacer.

**Lo que pueden mejorar:**
- `push(null)` se acepta y mete un elemento vacío en la pila; debía lanzar `IllegalArgumentException`.
- Agregaron `getTope()` a `Pila`, que deja ver los nodos por dentro; la pila solo debe dar acceso con sus cinco operaciones.
- Cuando el paciente no existe, el `Service` imprime mensajes o devuelve `null` en vez de lanzar el error.
- La rama de la pila se llamó `pila`, no `feature/pila-en-el-proyecto`, aunque sí la fusionaron a `main` y los commits fueron frecuentes.

## Colas (81/100)
**Lo que hicieron bien:**
- `Cola<T>` completa: maneja bien la cola vacía, deja `frente` y `fin` en orden al vaciarla y funciona al encolar después de vaciar.
- Todos los errores (cola vacía, `enqueue(null)`) están bien controlados.
- `listarEnEspera` rota la fila el número justo de veces y la deja intacta.
- Atender saca al paciente de la fila sin borrarlo de la lista de pacientes.

**Lo que pueden mejorar:**
- La opción 6 del menú de colas llama a `listarOperaciones` (la de la pila, que pide la identificación de un paciente) en vez de `listarEnEspera`. Por eso la opción "Listar la fila" no muestra la fila.
- En `SalaDeEspera` el atributo `nombre` es público (debe ser privado) y solo se valida que no sea nulo, no que esté vacío.
- `registrarLLegada` (con dos L mayúsculas, el nombre no sigue la convención de Java) no avisa si el paciente no existe; tienen `buscarPacienteOFallar` escrito pero sin usar.
- Trabajaron la cola directamente en `main`, sin la rama `feature/cola-en-el-proyecto`, y con pocos commits grandes.
