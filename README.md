# RetoHelloWorld
### 🚀 Instrucciones de ejecución

1. **Requisitos previos:**

   * Tener instalado **Java 8 o superior**.
   * Tener instalado **MySQL Server**.
   * Importar el script `retoholamundo.sql` en MySQL:

     ```bash
     mysql -u root -p < retoholamundo.sql
     ```

2. **Ejecutar la aplicación:**

   * Asegúrese de que la carpeta `lib/` con el conector MySQL está junto al `.jar`.
   * Desde la terminal:

     ```bash
     java -jar dist/ProyectoLogin.jar
     ```

3. **Opciones de login:**

   * **Login SQL:** conecta contra la base de datos `retoholamundo`.
   * **Login Fichero:** usa el fichero `usuarios.dat` (si no existe, se puede precargar con `precargarUsuarios()`).

### 🌍 Repositorio online

El código fuente se encuentra disponible en el siguiente repositorio:

👉 [https://github.com/Debetiesto/RetoHelloWorld.git](https://github.com/Debetiesto/RetoHelloWorld.git)

### 📌 Notas

* Si se elimina `usuarios.dat`, el programa puede generarlo de nuevo con usuarios de ejemplo.
* Para evitar errores, revise la configuración del **usuario/contraseña MySQL** en la clase `DaoImplementacionMySql`.

