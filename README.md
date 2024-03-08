
# SDA_Cifrado

En este repositorio se realiza un cifrado simétrico de datos, este cifrado se realiza mediante una key generada usando un String.


## Versiones

Para el frontend se está usando Vue 2 en su versión: 2.7.7

El backend está desarrollado usando Spring Boot 3.2.2 y Java 21

La base de datos funciona en PostgreSQL 15

## Antes de empezar

En un Gestor de bases de datos PostgreSQL crear una base de datos llamada "cifrado"


## Ejecutar el proyecto

Para ejecutar el backend se puede hacer desde Intellij o VS Code usando la extensión de Spring Boot Extension Pack

## Herramientas utilizadas en backend

Para realizar el cifrado en el backend se utiliza Javax.Cypto, es parte de la extensión criptográfica de Java (Java Cryptography Extension, JCE). Proporciona un marco y una implementación para el cifrado, la generación de claves, los algoritmos de Message Authentication Code (MAC), y los algoritmos de generación de números pseudoaleatorios. javax.crypto es una parte fundamental de la API de seguridad de Java y permite el desarrollo seguro de aplicaciones que necesitan funcionalidades criptográficas.

Las capacidades específicas de javax.crypto incluyen:

>> Cifrado y descifrado de datos: Permite cifrar información para mantenerla segura y descifrarla cuando sea necesario recuperarla en su forma original. Soporta varios algoritmos de cifrado simétrico (como AES, DES, Triple DES) y asimétrico (como RSA).

>> Generación y gestión de claves: Facilita la creación de claves seguras y su almacenamiento para su uso en procesos criptográficos.

>> Generación de números aleatorios y pseudoaleatorios seguros: Proporciona métodos para generar números que pueden usarse en la creación de claves o en algoritmos criptográficos.

>> Autenticación de mensajes: Soporta la generación y verificación de MACs (Message Authentication Codes) para asegurar la integridad y la autenticidad de los mensajes.

## Herramientas utilizadas en frontend

Para realizar el cifrado y descifrado en frontend se utiliza la Web Cryptography API (API de Criptografía Web). Es una especificación del World Wide Web Consortium (W3C) que define una interfaz para realizar operaciones criptográficas básicas en aplicaciones web, como:

>> Cifrado

>> Descifrado

>> Firma digital

>> Generación de hashes

La Web Cryptography API está diseñada para ser segura y eficiente, permitiendo a los desarrolladores web integrar funcionalidades criptográficas en sus aplicaciones sin la necesidad de recurrir a plugins o bibliotecas de terceros. Al operar dentro del contexto seguro del navegador, también ayuda a mitigar ciertos tipos de ataques criptográficos y a proteger la información sensible de los usuarios.