# Aplicación de Juegos
## Introducción
Este documento representa el proyecto final de la asignatura Interficies Móviles del Grado Superior DAM, en la que se ha desarrollado una aplicación utilizando Android Studio y Java. 
Se incluyen dos juegos: 2048 y Senku, y además, se almacenan las puntuaciones obtenidas en una base de datos SQLite y se tiene acceso a un apartado específico de configuraciones.
Las imágenes de la aplicación se han obtenido desde la página web flaticon.com y los sonidos extras desde pizabay.com de manera libre y gratuita.
La dinámica del 2048 consiste en desplazar bloques numéricos en un tablero de dimensiones 4x4, combinándolos para formar bloques de mayor valor y tratando de llegar a la casilla 2048.
Asimismo, en el Senku el objetivo es eliminar piezas del tablero saltando sobre ellas con otra pieza adyacente. Se han de eliminar todas las piezas, dejando solo una en el centro del tablero para poder ganar.

La aplicación cuenta con un sistema de puntuación para cada juego. En el Senku, se guarda el tiempo que se ha necesitado para completarlo. Y en el 2048 se guarda la puntuación máxima que se ha obtenido, pudiendo ganar solo si se obtiene la casilla con el número 2048, tal como su nombre indica.

## Funcionamiento general de la aplicación
### Se adjuntan las fotos correspondientes para poder sumergirse en la app paso por paso.

En primer lugar podemos visualizar una splashActivity que se ejecuta al iniciar la aplicación 
con un sonido correspondiente de bombilla encendida.

![splashactivity](https://github.com/yaracsoto88/appGames/assets/114931679/7be3e66e-fd5e-47cc-8d51-d029af1eaa73)

Esto nos lleva al apartado de usuario para poder acceder correctamente. Nos logueamos con las credenciales nombre de usuario: yara y contraseña: yara2

![user](https://github.com/yaracsoto88/appGames/assets/114931679/79bb8b1b-c920-4498-a003-89381a128a79)

Al darle click al botón de LogIn podemos observar que el usuario todavía no existe y nos pide que primero nos registremos. Por lo tanto, hay que clicar el botón de SignIn para crearnos el usuario y que se guarde en la base de datos.

![signin](https://github.com/yaracsoto88/appGames/assets/114931679/28ecdcc5-68f9-4dd0-995c-210bf0abdd3f)

Una vez completada esta tarea, le damos click al botón de LogIn y aparece un mensaje de registro completado satisfactoriamente.

![login](https://github.com/yaracsoto88/appGames/assets/114931679/dc0aac30-66ec-4b6b-b94a-0b1902906c8e)

Inmediatamente, accedemos al menu principal de la aplicación que está compuesto por un recyclerView que va mostrando los cuatro elementos del mismo.
Están los apartados de los dos juegos, el apartado de guardar las puntuaciones y el de configuraciones. 
Además, observamos que cada usuario tiene su foto de perfil y hay un mensaje que lo recibe con "Welcome: +nombreUsuario"

![menu](https://github.com/yaracsoto88/appGames/assets/114931679/a8dd0984-8746-41d8-902e-2a587241c024)

Accedemos al primer juego, el 2048, compuesto por una board de 4x4 que mientras se hacen movimientos va generando casillas, primero el número 2 aleatoriamente, y luego a medida que vamos jugando va juntando y sumando sus potencias. 

![2048_v1](https://github.com/yaracsoto88/appGames/assets/114931679/478cc6c1-dc83-4de3-9a05-aea933f2964e)


También se ha programado un temporizador, un botón de volver al menú, otro botón para deshacer un movimiento, un botón para resetear el juego, dos TextViews donde se guarda la puntuación actual y la mejor que haya jugado el usuario registrado, respectivamente.

![2048_v2](https://github.com/yaracsoto88/appGames/assets/114931679/0759ff9d-edfe-433d-a107-f40684bc74d0)

Si accedemos al siguiente juego, el Senku, podemos observar que, al igual que en el 2048, está el botón de volver al menú, el botón de deshacer movimiento, el de empezar desde el principio el rompecabezas, y el TextView con la respectiva mejor puntuación. Además, se ha añadido alguna ImageView para mejorar la estética.
He innovado y la casilla central, en vez de ser blanca, es una pequeña flor azul.

![senku_v1](https://github.com/yaracsoto88/appGames/assets/114931679/aed7a275-f460-4654-8f1e-d2a74b9e2801)

Una vez jugado y hecho todos los movimiento, caben las posibilidades de perder o ganar. En este caso, se ha perdido y aparece un Toast informando con un sonido de gameOver.

![senku_v2](https://github.com/yaracsoto88/appGames/assets/114931679/410cff7b-f62c-4546-ba33-6ebeeb068eb4)

En la siguiente pantalla se ha accedido al apartado donde se guardan las puntuaciones del usuario registrado.
Se compone de dos Recyclerviews, cada uno para cada juego, y de tres botones. Dos para ordenar las puntuaciones y otro para borrar todos los datos de la misma.

![score](https://github.com/yaracsoto88/appGames/assets/114931679/6e55ee03-6ba4-41ee-b4a4-b9875d628e7b)

Si hacemos clic en ordenar scores, podemos observar que en el 2048 se ordenan de mayor a menor, la máxima puntuación obtenida. En cambio, en el Senku, se ordena de menor a mayor, ya que si se completa el juego en menos tiempo quiere decir que lo ha hecho mejor.

![scoresordenado](https://github.com/yaracsoto88/appGames/assets/114931679/51ee02dd-ff26-4a73-867f-59c30ebc18aa)

La última pantalla a la que se puede acceder en esta aplicación es la de configuración. 

![config](https://github.com/yaracsoto88/appGames/assets/114931679/ad453a55-4b9f-422d-9d3f-11ecdff28a9a)

En primer lugar, se permite configurar la contraseña del usuario y cambiarla a una nueva. Si esto es así, aparece un mensaje de control que informa que se ha modificado correctamente.

![passw_update](https://github.com/yaracsoto88/appGames/assets/114931679/487e54d3-9f59-465a-85c5-5d38a2939ebb)

En segundo lugar, se puede cambiar la foto de perfil, accediendo a la galería del usuario y seleccionando una diferente. Una vez completado este proceso, aparece un mensaje informando de que todo ha ido bien y se guarda la foto en una ImageView del apartado de configuraciones, además de cambiarse en el menú principal.

![profile_update](https://github.com/yaracsoto88/appGames/assets/114931679/8e966375-8bf6-4ff6-a1e8-dfad20fd07ab)

Comprobamos que se ha modificado la foto con éxito.

![mnu_v2](https://github.com/yaracsoto88/appGames/assets/114931679/9371fed4-32c6-4c7a-bc9f-c3f7d54366f3)

Por último, si le damos click al botón de cerrar sesión, nos llevará a la pantalla inicial de registro de usuario y cerrará la conexión con el actual, mostrando previamente un mensaje de si se está de acuerdo con la acción.

![closesession](https://github.com/yaracsoto88/appGames/assets/114931679/10fbaa90-9af2-4e67-96f7-356290c0093d)


## Clases generales y su propósito
### Menu
Esta actividad representa el menú principal. Utiliza un RecyclerView para mostrar elementos del menú, accede a preferencias compartidas para obtener el nombre de usuario, y realiza animaciones al iniciar la actividad. Además, carga la imagen del usuario desde una base de datos local y la muestra en un ImageView.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/10cea4b2-dfc7-4b34-941a-c8e75a0701e7)

### MenuAdapter
Esta clase un adaptador del RecyclerView diseñado para la clase Menu. Su propósito principal es gestionar la visualización de elementos del menú mediante la carga de datos desde un ArrayList de objetos MenuItem. Además, al hacer clic en un elemento, se inicia una nueva actividad correspondiente al elemento seleccionado.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/acfb8bb2-9ac2-483a-92a1-dd2252c9ae2d)

### MenuItem
Esta clase define objetos que representan elementos del menú. Cada objeto tiene un título, un recurso de imagen y la clase de actividad asociada. La función getMenu() devuelve un ArrayList de objetos MenuItem predefinidos para los elementos del menú, como "2048", "Senku", "Score" y "Settings", con sus respectivos recursos de imagen y clases de actividad asociadas.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/251c0961-0d55-4129-858b-54041eed6c37)


### SplashActivity
SplashActivity es la actividad de bienvenida que muestra una animación al inicio de la aplicación. Durante la animación, se reproduce un sonido, y al finalizar, se inicia la actividad User y se finaliza la actividad actual. Además, se asegura de liberar los recursos del reproductor de medios en el método onDestroy para evitar posibles fugas de memoria.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/5e5bbbf3-4210-4733-b7c7-76259ab81aba)


![image](https://github.com/yaracsoto88/appGames/assets/114931679/2665dddb-5aba-45af-a819-35f56119b77a)


### User
Esta actividad representa el inicio de sesión y registro de los usuarios. Permite ingresar unos determinados credenciales o bien registrarse. Al hacer clic en los botones de inicio de sesión o registro, verifica los datos en la base de datos local, DBHelper, y muestra mensajes de alerta correspondientes. Si las credenciales son correctas durante el inicio de sesión, se almacena el usuario activo en las preferencias compartidas y se inicia la actividad Menu. 

![image](https://github.com/yaracsoto88/appGames/assets/114931679/52748fd5-145a-4f44-9b91-29a43fd06f9e)


### DBHelper
Esta clase gestiona la creación, actualización y operaciones de inserción/consulta para las tablas relacionadas con puntuaciones de juegos y datos de usuario. 
Las tablas que se crean son "ScoreData2048", "ScoreDataSenku" y "UserData". Gracias a esta clase se puede insertar de datos de puntuación, datos de usuario, actualizar fotos de usuario, verificar credenciales de usuario, consultar datos de las puntuaciones, eliminar puntuaciones y actualizar contraseñas. Además, incluye métodos específicos para obtener la mejor puntuación en cada juego.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/a1aff3b8-97a7-4cc9-a506-f7c02e9e0f39)

![image](https://github.com/yaracsoto88/appGames/assets/114931679/8a931e44-525d-444d-9056-f5f478fe95a1)


### ScoreActivity
Esta actividad muestra las puntuaciones. Permite ordenarlas y visualizarlas, borrarlas y volver al menú principal. Utiliza RecyclerViews y adaptadores ScoresAdapter para mostrar las puntuaciones. Además, incluye funcionalidades como el deslizamiento para eliminar puntuaciones y la actualización dinámica de las listas al ordenarlas. La clase utiliza un DBHelper para gestionar las operaciones de base de datos y obtener los datos de puntuación.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/62365633-bde5-4adf-8712-d32d19a709b4)


### ScoresAdapter
Esta clase es un adaptador de RecyclerView diseñado para mostrar las puntuaciones en ScoreActivity. Utiliza un diseño personalizado ("list_layout.xml") para cada elemento de la lista, mostrando el nombre del jugador y la puntuación. La clase gestiona la creación de vistas y la vinculación de datos para cada elemento, permitiendo así la visualización de las puntuaciones en un RecyclerView.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/5b899c28-3bd5-43f1-80a4-2499f2a6279d)

![image](https://github.com/yaracsoto88/appGames/assets/114931679/f1678495-9c61-4554-8b7d-d980e87df2fc)

### Score
Esta clase proporciona una estructura de datos para representar las puntuaciones de los juegos, y se utiliza en ScoreActivity y en ScoresAdapter para mostrar y gestionar las puntuaciones en la interfaz de usuario, así como en la clase DBHelper para interactuar con la base de datos. Incluye un método formatScore que convierte la puntuación de segundos a un formato más legible en minutos y segundos.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/138fbcdf-eff0-4e34-8e52-2d2081a55aad)

![image](https://github.com/yaracsoto88/appGames/assets/114931679/5661f475-ef2f-4100-bc1d-b26db63716bd)

### Setting
Esta clase se encarga proporcionar funcionalidades relacionadas con la configuración del perfil. Utiliza la clase DBHelper para interactuar con la base de datos local gestionando la carga y almacenamiento de la foto de perfil del usuario, así como la actualización de la contraseña en la base de datos. Además, puede redirigir al usuario a la actividad de inicio de sesión al cerrar la sesión y de esta manera, registrarse con una nueva cuenta.
Utiliza el ActivityResultContracts para lanzar la galería de imágenes, y cuando se selecciona una imagen y se devuelve el resultado, verifica si la operación fue exitosa. Si es así, obtiene la Uri de la imagen seleccionada, comprueba su tamaño, guarda la imagen en la base de datos con un nombre de usuario específico, y luego carga la imagen actualizada, mostrando un mensaje de éxito. En caso de que la imagen sea demasiado grande, muestra un mensaje de error.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/168111c9-813f-45e5-b3ba-6d8a4566de3e)

## Las clases de los Juegos
### Senku
Utiliza un GridLayout para representar visualmente el tablero y ofrece funcionalidades como temporizador, controles táctiles, botones para deshacer movimientos y reiniciar el juego. A través de DBHelper se almacenan y recuperan las mejores puntuaciones de los usuarios. Además, incorpora efectos de sonido y mensajes visuales para notificar eventos clave del juego, como el fin del juego o la victoria.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/9071834b-4d60-47aa-8618-ae8b936617fd)


### SenkuTable
Representa la lógica y estado del tablero. Lo inicializa con fichas y casillas vacías, y permite validar y realizar movimientos según las reglas. Además, ofrece funciones para deshacer movimientos, verificar la existencia de movimientos posibles y determinar el estado del juego (victoria, derrota o en progreso). La clase interactúa con la clase Senku a través de llamadas de métodos para coordinar la lógica y mantener la coherencia entre el modelo y la vista.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/1fd7143f-5683-426a-a156-b52b4199ed87)

![image](https://github.com/yaracsoto88/appGames/assets/114931679/eb2d5b45-16d4-426a-9dbd-0ce0ec2ae064)

### game2048
Utiliza un objeto de la clase Tablero para gestionar el estado del mismo y la interacción con la interfaz de usuario. La clase controla la detección de gestos táctiles para realizar movimientos y muestra información como el tiempo transcurrido, puntuación actual y mejor puntuación obtenida. 
Además, gestiona eventos como iniciar un nuevo juego, deshacer movimientos, salir del juego y actualiza el temporizador. 
Incluye la reproducción de efectos de sonido al ganar o perder el juego.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/b841ef19-f106-428e-8c59-d2c174803a0d)


### Tablero
Esta clase gestiona la matriz de casillas, realiza movimientos y fusiones de casillas, y actualiza la interfaz de usuario. Además, controla el estado del juego, como la puntuación, si el juego se ha ganado o perdido, y proporciona métodos para deshacer movimientos.
La inicialización del tablero se realiza mediante el método initBoard, que crea una matriz de casillas y coloca una casilla con valor 2 en una posición aleatoria.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/47145b49-3440-4864-a085-119b65c0b127)

El método conectMatrixView actualiza la interfaz de usuario reflejando el estado actual del tablero.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/e4effa44-3e55-4f01-9056-b194378e4609)

Los métodos de movimiento, como "up", "down", "left" y "right", desplazan y fusionan las casillas según la dirección especificada. 

![image](https://github.com/yaracsoto88/appGames/assets/114931679/dd207d25-00dc-406a-84fc-f6b745bcf398)


El método repaint agrega una nueva casilla al tablero y actualiza la interfaz de usuario después de cada movimiento.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/2babd004-9296-4c1e-bb5b-6415c8da400f)


La clase también incluye métodos para deshacer movimientos undo, determinar si el juego se ha perdido gameLost, o ganado gameWon.

![image](https://github.com/yaracsoto88/appGames/assets/114931679/3524634e-dfb5-4659-9394-5d3fc2e8f3b9)

![image](https://github.com/yaracsoto88/appGames/assets/114931679/7b3f052d-e2f8-4a29-ac98-8fddc4702d7b)

![image](https://github.com/yaracsoto88/appGames/assets/114931679/e282a355-1954-4f84-b8a4-236fbbcde3bd)

### Casilla
Cada instancia de esta clase representa una casilla en el tablero del juego, y su valor puede ser 0 o algún valor potencia de 2 (2, 4, 8, 16, etc...)

![image](https://github.com/yaracsoto88/appGames/assets/114931679/7e05a23d-515e-4e7c-8257-947002bc3d53)

![image](https://github.com/yaracsoto88/appGames/assets/114931679/4dec037a-2219-4a2d-a5a1-393aa9177a08)



