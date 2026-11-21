package com.example.eva2

import com.example.eva2.repository.ValidadorFormulario
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun validarNombreYCorreo() {
        assertNull(ValidadorFormulario.validarNombre("Esteban Perez"))
        assertEquals(
            "Ingresa tu nombre",
            ValidadorFormulario.validarNombre("")
        )
        assertNull(ValidadorFormulario.validarCorreo("estudiante@gmail.com"))
        assertEquals(
            "El correo no tiene un formato válido",
            ValidadorFormulario.validarCorreo("estudiantegmail.com")
        )
    }

    @Test
    fun validarContrasenas() {
        assertNull(ValidadorFormulario.validarContrasenaRegistro("Clave1234"))
        assertEquals(
            "Debe tener al menos 8 caracteres",
            ValidadorFormulario.validarContrasenaRegistro("Ab123")
        )
        assertEquals(
            "Debe tener al menos una mayúscula",
            ValidadorFormulario.validarContrasenaRegistro("clave1234")
        )
        assertEquals(
            "Debe tener al menos un número",
            ValidadorFormulario.validarContrasenaRegistro("ClaveSegura")
        )
        assertNull(
            ValidadorFormulario.validarConfirmacion("Clave1234", "Clave1234")
        )
        assertEquals(
            "Las contraseñas no coinciden",
            ValidadorFormulario.validarConfirmacion("Clave1234", "Clave5678")
        )
    }
}
