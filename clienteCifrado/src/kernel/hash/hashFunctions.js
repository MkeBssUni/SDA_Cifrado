/* La Web Cryptography API (API de Criptografía Web) es una especificación que define una interfaz de programación de aplicaciones (API)
para realizar operaciones criptográficas básicas en aplicaciones web. Es una parte importante del conjunto de tecnologías web proporcionadas
por los navegadores modernos, permitiendo a los desarrolladores realizar operaciones seguras de cifrado, descifrado, firma digital, y generación
de hashes directamente en el navegador, sin necesidad de plugins o bibliotecas externas. */

async function getKeyMaterial() {
    const secretKey = 'S4AUC3B0YZS3ND0'; // Clave secreta estática
    const encoder = new TextEncoder(); // Utilizado para convertir la clave secreta en un array de bytes.
    // Aplica hash SHA-256 a la clave secreta.
    const hash = await window.crypto.subtle.digest(
        {
            name: "SHA-256",
        },
        encoder.encode(secretKey)
    );

    // Utiliza solo los primeros 128 bits (16 bytes)
    return new Uint8Array(hash).slice(0, 16);
}

export async function decrypt(ciphertext) {
    const decoder = new TextDecoder(); // Utilizado para decodificar el array de bytes resultante a texto.
    const keyMaterial = await getKeyMaterial(); // Obtiene el material de clave.
    // Importa el material de clave como una clave de criptografía para usar en la operación de descifrado.
    const key = await window.crypto.subtle.importKey(
        "raw",
        keyMaterial,
        { name: "AES-CBC" },
        false,
        ["decrypt"]
    );
    const iv = new Uint8Array(16); // IV estático, debe ser el mismo que se usó durante el cifrado.
    // Convierte el texto cifrado de base64 a un array de bytes.
    const encryptedData = Uint8Array.from(atob(ciphertext), c => c.charCodeAt(0));
    // Realiza la operación de descifrado.
    const decrypted = await window.crypto.subtle.decrypt(
        {
            name: "AES-CBC",
            iv,
        },
        key,
        encryptedData
    );
    // Devuelve el texto descifrado.
    return decoder.decode(decrypted);
}

export async function encrypt(plaintext) {
    const encoder = new TextEncoder(); // Utilizado para convertir el texto plano en un array de bytes.
    const keyMaterial = await getKeyMaterial();
    const key = await window.crypto.subtle.importKey(
        "raw",
        keyMaterial,
        { name: "AES-CBC" },
        false,
        ["encrypt"]
    );
    const iv = new Uint8Array(16); // IV estático, en este caso todo ceros.
    // Realiza la operación de cifrado.
    const encrypted = await window.crypto.subtle.encrypt(
        {
            name: "AES-CBC",
            iv,
        },
        key,
        encoder.encode(plaintext)
    );
    // Devuelve el texto cifrado en base64.
    return window.btoa(String.fromCharCode.apply(null, new Uint8Array(encrypted)));
}