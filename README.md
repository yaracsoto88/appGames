# Aplicación de Juegos
## Introducción
Este documento representa el proyecto final de la asignatura Interficies Móviles del Grado Superior DAM, en la que se ha desarrollado una aplicación utilizando Android Studio y Java. 
Se incluyen dos juegos: 2048 y Senku, y además, se almacenan las puntuaciones obtenidas en una base de datos SQLite y se tiene acceso a un apartado específico de configuraciones.
Las imágenes de la aplicaión se han obtenido desde la página web flaticon.com y los sonidos extras desde pizabay.com de manera libre y gratuita.
La dinámica del 2048 consiste en desplazar bloques numéricos en un tablero de dimensiones 4x4, combinándolos para formar bloques de mayor valor y tratando de llegar a la casilla 2048.
Asimismo, en el Senku el objetivo es eliminar piezas del tablero saltando sobre ellas con otra pieza adyacente. Se han de eliminar todas las piezas, dejando solo una en el centro del tablero para poder ganar.

La aplicación cuenta con un sistema de puntuación para cada juego. En el Senku, se guarda el tiempo que se ha necesitado para completarlo. Y en el 2048 se guarda la puntuación máxima que se ha obtenido, pudiendo ganar solo si se obtiene la casilla con el número 2048, tal como su nombre indica.

## Funcionamiento general de la aplicación
### Se adjuntan las fotos correspondientes para poder sumergirse en la app paso por paso.



![splashactivity](https://github.com/yaracsoto88/appGames/assets/114931679/7be3e66e-fd5e-47cc-8d51-d029af1eaa73)

![user](https://github.com/yaracsoto88/appGames/assets/114931679/79bb8b1b-c920-4498-a003-89381a128a79)

![signin](https://github.com/yaracsoto88/appGames/assets/114931679/28ecdcc5-68f9-4dd0-995c-210bf0abdd3f)

![login](https://github.com/yaracsoto88/appGames/assets/114931679/dc0aac30-66ec-4b6b-b94a-0b1902906c8e)

![menu](https://github.com/yaracsoto88/appGames/assets/114931679/a8dd0984-8746-41d8-902e-2a587241c024)

![2048_v1](https://github.com/yaracsoto88/appGames/assets/114931679/478cc6c1-dc83-4de3-9a05-aea933f2964e)

![2048_v2](https://github.com/yaracsoto88/appGames/assets/114931679/0759ff9d-edfe-433d-a107-f40684bc74d0)


![senku_v1](https://github.com/yaracsoto88/appGames/assets/114931679/aed7a275-f460-4654-8f1e-d2a74b9e2801)

![senku_v2](https://github.com/yaracsoto88/appGames/assets/114931679/410cff7b-f62c-4546-ba33-6ebeeb068eb4)


![score](https://github.com/yaracsoto88/appGames/assets/114931679/6e55ee03-6ba4-41ee-b4a4-b9875d628e7b)

![scoresordenado](https://github.com/yaracsoto88/appGames/assets/114931679/51ee02dd-ff26-4a73-867f-59c30ebc18aa)


![config](https://github.com/yaracsoto88/appGames/assets/114931679/ad453a55-4b9f-422d-9d3f-11ecdff28a9a)

![passw_update](https://github.com/yaracsoto88/appGames/assets/114931679/487e54d3-9f59-465a-85c5-5d38a2939ebb)


![profile_update](https://github.com/yaracsoto88/appGames/assets/114931679/8e966375-8bf6-4ff6-a1e8-dfad20fd07ab)

![mnu_v2](https://github.com/yaracsoto88/appGames/assets/114931679/9371fed4-32c6-4c7a-bc9f-c3f7d54366f3)


![closesession](https://github.com/yaracsoto88/appGames/assets/114931679/10fbaa90-9af2-4e67-96f7-356290c0093d)







## Clases generales y su propósito
### Menu
Esta actividad representa el menú principal. Utiliza un RecyclerView para mostrar elementos del menú, accede a preferencias compartidas para obtener el nombre de usuario, y realiza animaciones al iniciar la actividad. Además, carga la imagen del usuario desde una base de datos local y la muestra en un ImageView.

### MenuAdapter
Esta clase un adaptador del RecyclerView diseñado para la clase Menu. Su propósito principal es gestionar la visualización de elementos del menú mediante la carga de datos desde un ArrayList de objetos MenuItem. Además, al hacer clic en un elemento, se inicia una nueva actividad correspondiente al elemento seleccionado.

### MenItem
Esta clase define objetos que representan elementos del menú. Cada objeto tiene un título, un recurso de imagen y la clase de actividad asociada. La función getMenu() devuelve un ArrayList de objetos MenuItem predefinidos para los elementos del menú, como "2048", "Senku", "Score" y "Settings", con sus respectivos recursos de imagen y clases de actividad asociadas.

### SplashActivity
SplashActivity es la actividad de bienvenida que muestra una animación al inicio de la aplicación. Durante la animación, se reproduce un sonido, y al finalizar, se inicia la actividad User y se finaliza la actividad actual. Además, se asegura de liberar los recursos del reproductor de medios en el método "onDestroy" para evitar posibles fugas de memoria.

### User
Esta actividad representa el inicio de sesión y registro de los usuarios. Permite ingresar unos determinados credenciales o bien registrarse. Al hacer clic en los botones de inicio de sesión o registro, verifica los datos en la base de datos local, DBHelper, y muestra mensajes de alerta correspondientes. Si las credenciales son correctas durante el inicio de sesión, se almacena el usuario activo en las preferencias compartidas y se inicia la actividad Menu. La función message se utiliza para mostrar mensajes de alerta.

### DBHelper
Esta clase gestiona la creación, actualización y operaciones de inserción/consulta para las tablas relacionadas con puntuaciones de juegos y datos de usuario. 
Las tablas que se crean son "ScoreData2048", "ScoreDataSenku" y "UserData". Gracias a esta clase se puede insertar de datos de puntuación, datos de usuario, actualizar fotos de usuario, verificar credenciales de usuario, consultar datos de las puntuaciones, eliminar puntuaciones y actualizar contraseñas. Además, incluye métodos específicos para obtener la mejor puntuación en cada juego.

### ScoreActivity
Esta actividad muestra las puntuaciones. Permite ordenarlas y visualizarlas, borrarlas y volver al menú principal. Utiliza RecyclerViews y adaptadores ScoresAdapter para mostrar las puntuaciones. Además, incluye funcionalidades como el deslizamiento para eliminar puntuaciones y la actualización dinámica de las listas al ordenarlas. La clase utiliza un DBHelper para gestionar las operaciones de base de datos y obtener los datos de puntuación.

### ScoreAdapter
Esta clase es un adaptador de RecyclerView diseñado para mostrar las puntuaciones en ScoreActivity. Utiliza un diseño personalizado ("list_layout.xml") para cada elemento de la lista, mostrando el nombre del jugador y la puntuación. La clase gestiona la creación de vistas y la vinculación de datos para cada elemento, permitiendo así la visualización de las puntuaciones en un RecyclerView.

### Score
Esta clase proporciona una estructura de datos para representar las puntuaciones de los juegos, y se utiliza en ScoreActivity y en ScoresAdapter para mostrar y gestionar las puntuaciones en la interfaz de usuario, así como en la clase DBHelper para interactuar con la base de datos. Incluye un método formatScore que convierte la puntuación de segundos a un formato más legible en minutos y segundos (MM:SS). 

### Setting
Esta clase se encarga proporcionar funcionalidades relacionadas con la configuración del perfil. Utiliza la clase DBHelper para interactuar con la base de datos local gestionando la carga y almacenamiento de la foto de perfil del usuario, así como la actualización de la contraseña en la base de datos. Además, puede redirigir al usuario a la actividad de inicio de sesión al cerrar la sesión y de esta manera, registrarse con una nueva cuenta.

## Las clases de los Juegos
### Senku
Utiliza un GridLayout para representar visualmente el tablero y ofrece funcionalidades como temporizador, controles táctiles, botones para deshacer movimientos y reiniciar el juego. La clase interactúa con la persistencia de datos a través de DBHelper para almacenar y recuperar las mejores puntuaciones de los usuarios. Además, incorpora efectos de sonido y mensajes visuales para notificar eventos clave del juego, como el fin del juego o la victoria.

### SenkuTable
Representa la lógica y estado del tablero. Lo inicializa con fichas y casillas vacías, y permite validar y realizar movimientos según las reglas. Además, ofrece funciones para deshacer movimientos, verificar la existencia de movimientos posibles y determinar el estado del juego (victoria, derrota o en progreso). La clase interactúa con la clase Senku a través de llamadas de métodos para coordinar la lógica y mantener la coherencia entre el modelo y la vista.

### game2048
Utiliza un objeto de la clase Tablero para gestionar el estado del mismo y la interacción con la interfaz de usuario. La clase controla la detección de gestos táctiles para realizar movimientos y muestra información como el tiempo transcurrido, puntuación actual y mejor puntuación obtenida. 
Además, gestiona eventos como iniciar un nuevo juego, deshacer movimientos, salir del juego y actualiza el temporizador. 
Incluye la reproducción de efectos de sonido al ganar o perder el juego. La persistencia de datos se realiza a través de la clase DBHelper, almacenando y recuperando la mejor puntuación del usuario.

### Tablero
Esta clase gestiona la matriz de casillas, realiza movimientos y fusiones de casillas, y actualiza la interfaz de usuario. Además, controla el estado del juego, como la puntuación, si el juego se ha ganado o perdido, y proporciona métodos para deshacer movimientos.
La inicialización del tablero se realiza mediante el método initBoar", que crea una matriz de casillas y coloca una casilla con valor 2 en una posición aleatoria. El método "conectMatrixView" actualiza la interfaz de usuario reflejando el estado actual del tablero.
Los métodos de movimiento, como "up", "down", "left" y "right", desplazan y fusionan las casillas según la dirección especificada. El método "repaint" agrega una nueva casilla al tablero y actualiza la interfaz de usuario después de cada movimiento.

La clase también incluye métodos para deshacer movimientos ("undo"), determinar si el juego se ha perdido ("gameLost") o ganado ("gameWon"), y obtener la puntuación actual del juego ("getScore").

### Casilla
Cada instancia de esta clase representa una casilla en el tablero del juego, y su valor puede ser 0 o algún valor potencia de 2 (2, 4, 8, 16, etc.).

