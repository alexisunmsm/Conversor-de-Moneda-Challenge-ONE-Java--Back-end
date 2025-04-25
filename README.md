# Conversor de Moneda - Challenge ONE

Una aplicación de consola Java que permite convertir entre diferentes monedas utilizando tasas de cambio en tiempo real de la API ExchangeRate.

## Características

- Conversión entre múltiples monedas (ARS, BOB, BRL, CLP, COP, USD)
- Obtención de tasas de cambio en tiempo real desde ExchangeRate API
- Visualización de tasas de cambio actuales
- Interfaz de usuario interactiva basada en consola
- Soporte completo en español
- Actualizaciones automáticas de tasas de cambio

## Requisitos Previos

- Java 11 o superior
- Maven
- Conexión a Internet para obtener las tasas de cambio en tiempo real

## Configuración

1. Clona este repositorio
2. La API key ya está configurada y lista para usar
3. Compila el proyecto usando Maven:

```bash
mvn clean package
```

4. Ejecuta la aplicación:

```bash
java -jar target/currency-converter-1.0-SNAPSHOT-jar-with-dependencies.jar

```

## Estructura del Proyecto

- `Main.java`: Punto de entrada de la aplicación
- `api/ExchangeRateClient.java`: Cliente para realizar solicitudes a la API de ExchangeRate
- `model/Currency.java`: Enumeración que representa las monedas soportadas
- `model/ExchangeRateResponse.java`: Clase modelo para la respuesta de la API
- `service/CurrencyConverterService.java`: Servicio para operaciones de conversión de moneda
- `ui/ConsoleMenu.java`: Interfaz de usuario basada en consola
- `util/ApiKeyLoader.java`: Utilidad para cargar claves de API

## Uso

La aplicación presenta un menú con las siguientes opciones:

1. Convertir monedas
   - Selecciona la moneda de origen
   - Selecciona la moneda de destino
   - Ingresa el monto a convertir
   - Visualiza el resultado de la conversión

2. Ver tasas de cambio actuales
   - Muestra todas las tasas de cambio disponibles
   - Indica la fecha de última actualización

3. Actualizar tasas de cambio
   - Obtiene las últimas tasas de cambio de la API
   - Confirma la actualización exitosa

0. Salir
   - Cierra la aplicación

## Monedas Soportadas

- ARS - Peso Argentino
- BOB - Boliviano
- BRL - Real Brasileño
- CLP - Peso Chileno
- COP - Peso Colombiano
- USD - Dólar Estadounidense

## Características Técnicas

- Uso de Java 11 para mejor rendimiento y características modernas
- Implementación de HttpClient para conexiones API eficientes
- Manejo de JSON con la biblioteca Gson
- Arquitectura modular para fácil mantenimiento
- Manejo robusto de errores y excepciones
- Formateo de moneda con precisión decimal
- Interfaz de usuario intuitiva y amigable

## Documentación de la API

Este proyecto utiliza la API ExchangeRate para obtener tasas de cambio en tiempo real. La documentación completa de la API se puede encontrar en [https://www.exchangerate-api.com/docs/overview](https://www.exchangerate-api.com/docs/overview).

## Solución de Problemas

Si encuentras algún problema al ejecutar la aplicación:

1. Verifica tu conexión a Internet
2. Asegúrate de tener Java 17 o superior instalado
3. Confirma que Maven está correctamente instalado
## Licencia

Este proyecto es parte del Challenge ONE - Java - Back end.

## Contribuciones

Este proyecto es parte de un desafío educativo y no acepta contribuciones directas. Sin embargo, siéntete libre de hacer un fork y modificarlo para tu uso personal.