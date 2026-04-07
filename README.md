# 🎮 Proyecto DAM Java — Minijuegos de Consola

Aplicación de consola desarrollada en **Java** que integra tres juegos clásicos bajo una arquitectura modular. El proyecto fue desarrollado íntegramente **sin el uso de programación orientada a objetos**, empleando únicamente programación estructural mediante métodos estáticos, arrays y matrices bidimensionales.

---

## 📋 Descripción

El programa presenta un menú principal desde el cual el usuario puede acceder a tres minijuegos independientes, consultar las puntuaciones acumuladas de la sesión y salir de la aplicación. Toda la interacción se realiza a través de la terminal.

```
1. Cuatro en raya
2. Busca minas
3. Hundir la flota
4. Ver puntuaciones
5. Salir
```

---

## 🕹️ Juegos incluidos

### 1. Cuatro en Raya (`Cuatroenraya.java`)
Juego para **dos jugadores** en un tablero de 6 filas × 7 columnas.

- El jugador que empieza se elige **aleatoriamente** al inicio de cada partida.
- Cada turno, el jugador activo elige una columna y la ficha cae por gravedad hasta la posición más baja disponible.
- El sistema detecta automáticamente victorias en **horizontal**, **vertical** y **diagonal** (ascendente y descendente).
- Si se completan las 42 casillas sin ganador, se declara **empate**.
- El tablero se representa con `A` para el jugador 1, `B` para el jugador 2 y `O` para las casillas vacías.

### 2. Buscaminas (`Buscaminas.java`)
Versión clásica del buscaminas con **tres niveles de dificultad**:

| Dificultad | Tablero | Bombas |
|------------|---------|--------|
| Fácil      | 5×5     | 5      |
| Medio      | 10×10   | 20     |
| Difícil    | 20×20   | 100    |

- Las bombas se colocan de forma **aleatoria** al inicio.
- El jugador puede **desvelar casillas** o **poner banderas** (`?`) para marcar posibles bombas.
- Al desvelar una casilla, se muestra el número de bombas existentes en las 8 posiciones adyacentes (radar de bombas).
- Si se destapa una bomba, la partida termina y se **revela el tablero completo**.
- La victoria se alcanza cuando se han descubierto todas las casillas que no contienen bomba.
- Se lleva un **contador de partidas ganadas** accesible desde el menú principal.

### 3. Hundir la Flota (`Hundirlaflota.java`)
Juego contra la **CPU** en un tablero de 10×10.

**Flota disponible por jugador:**
- 1 × Portaaviones (4 casillas)
- 2 × Submarinos (3 casillas cada uno)
- 3 × Lanchas (1 casilla cada una)

**Mecánica de juego:**
- El jugador coloca manualmente su flota indicando posición inicial y final para los barcos de más de una casilla (horizontal o vertical). La CPU coloca su flota de forma aleatoria.
- Los disparos se alternan: primero el jugador, luego la CPU (disparo aleatorio).
- El tablero muestra simultáneamente el **tablero propio** y el **tablero enemigo** con la siguiente simbología:

| Símbolo | Significado |
|---------|-------------|
| `.`     | Mar sin disparar |
| `#`     | Posición de barco propio |
| `X`     | Barco tocado |
| `H`     | Barco hundido |
| `O`     | Disparo fallido (agua) |

- El juego termina cuando toda la flota de un bando queda hundida (6 barcos en total).
- Se lleva un **contador de partidas ganadas** accesible desde el menú principal.

---

## 🏗️ Estructura del proyecto

```
src/
└── NavarroLuis_04/
    ├── NavarroLuis_04.java    # Clase principal — menú y control de flujo
    ├── Cuatroenraya.java      # Motor del juego Cuatro en Raya
    ├── Buscaminas.java        # Motor del juego Buscaminas
    └── Hundirlaflota.java     # Motor del juego Hundir la Flota
```

---

## ⚙️ Decisiones técnicas destacadas

- **Sin POO**: todo el proyecto está implementado con métodos estáticos y variables estáticas de clase. No se instancia ningún objeto ni se hace uso de herencia, polimorfismo o encapsulación.
- **Matrices bidimensionales** (`int[][]`): son la estructura central del proyecto, usadas para representar tableros, posiciones de barcos y estado de las casillas.
- **Entrada robusta**: todos los valores introducidos por el usuario se validan en bucles `while` antes de ser procesados, evitando valores fuera de rango o posiciones inválidas.
- **Aleatoridad**: se utiliza `Math.random()` para la colocación de bombas, la IA en Hundir la Flota y la elección del jugador inicial en Cuatro en Raya.
- **Puntuaciones de sesión**: las partidas ganadas en Buscaminas y Hundir la Flota se acumulan en variables estáticas públicas y se pueden consultar en cualquier momento desde el menú principal.
- **Separación de responsabilidades**: cada juego está encapsulado en su propia clase con un único punto de entrada público (`juego()`), manteniendo el `main` completamente limpio.

---

## ▶️ Ejecución

**Requisitos:** Java 17 o superior (se utiliza la sintaxis de switch con `->` de Java 14+).

```bash
# Compilar
javac src/NavarroLuis_04/*.java

# Ejecutar
java -cp src NavarroLuis_04.NavarroLuis_04
```

También puede abrirse y ejecutarse directamente desde **NetBeans IDE** o cualquier otro IDE compatible con Java.

---

## 👤 Autor

**Luis Navarro Nocito** — Proyecto inicial de 1º del modulo DAM (Desarrollo de Aplicaciones Multiplataforma)
