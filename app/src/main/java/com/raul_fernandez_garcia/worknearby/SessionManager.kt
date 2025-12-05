package com.raul_fernandez_garcia.worknearby

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_USER_ID = "user_id"
        const val KEY_USER_ROLE = "user_role"
        const val KEY_USER_NAME = "user_name"
    }

    // Guardar datos al entrar
    fun guardarSesion(id: Int, rol: String, nombre: String) {
        val editor = prefs.edit()
        editor.putInt(KEY_USER_ID, id)
        editor.putString(KEY_USER_ROLE, rol)
        editor.putString(KEY_USER_NAME, nombre)
        editor.apply()
    }

    // Obtener ID (Devuelve 0 si no hay nadie)
    fun obtenerIdUsuario(): Int {
        return prefs.getInt(KEY_USER_ID, 0)
    }

    // Obtener Rol ("cliente" o "trabajador")
    fun obtenerRol(): String {
        return prefs.getString(KEY_USER_ROLE, "") ?: ""
    }

    // Cerrar sesion
    fun borrarSesion() {
        prefs.edit().clear().apply()
    }
}