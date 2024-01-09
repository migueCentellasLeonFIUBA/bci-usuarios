# BCI -Prueba Tecnica Spring boot / Java

##  Ejecucion de la aplicacion

```bash
$ java -jar bci-0.0.1-SNAPSHOT.jar
```

## Documentacion tecnica (Swagger)
Acceso via endpoint local  [swagger](http://localhost:8001/swagger-ui.html)

## Recursos HTTP:

### Creacion de Usuarios (POST)

#### Consideraciones:

Se aplican restricciones al formato de email y password a ingresar:
##### Restriccion a email:
El formato correcto es xxx@dominio.cl , donde la ultima parte esta restringida unicamente a "cl"

##### Restriccion a Password:

+ Contraseña de 8-16 caracteres con al menos un dígito
+ Al menos un digito debe ser letra minúscula, 
+ Al menos un digito debe ser letra mayúscula, 
+ Al menos un digito debe ser caracter especial,
+ La contraseña debe ser sin espacios en blanco.

(e.g) endpoint: http://localhost:8001/api/usuarios/

Ejemplo de un RequestBody para creacion de usuario(email Unico):

```json
{
  "name": "Migue Centellas Leon",
  "email": "miguecentellasleon@bci.cl",
  "password": "Bcia#@#8",
  "phones": [
    {
      "number": "3473771",
      "citycode": "1",
      "countrycode": "5367"
    },
    {
      "number": "8388",
      "citycode": "1",
      "countrycode": "5447"
    }
  ]
}
```

### Consulta de usuario por Id (GET)

(e.g) endpoint: http://localhost:8001/api/usuarios/{idusuario}

### Actualizacion de usuario por Id (PUT)

Solo se acepta actualizar los campos name, email y Password

(e.g) endpoint: http://localhost:8001/api/usuarios/{idusuario}

Ejemplo de un RequestBody para actualizacion de usuario(email Unico):

```json
{
  "name": "Miguel Centellas",
  "email": "miguelleon@bci.cl",
  "password": "Bcia#@#8"
}
```

### Eliminacion de usuario por Id (DELETE)

(e.g) endpoint: http://localhost:8001/api/usuarios/{idusuario}
