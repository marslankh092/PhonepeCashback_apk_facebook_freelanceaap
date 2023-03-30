package org.phone.pe.utils

import android.content.Context

class PrefUtil(var context: Context) {
    companion object {
        const val PREF_NAME = "pref"

        fun saveName(context: Context, value: String?) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("name", value)
            editor.apply()
        }

        fun getName(context: Context): String? {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString("name", "")
        }

        fun saveNumber(context: Context, value: String?) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("number", value)
            editor.apply()
        }

        fun getNumber(context: Context): String? {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString("number", "")
        }

        fun saveToken(context: Context, value: String?) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("token", value)
            editor.apply()
        }

        fun getToken(context: Context): String? {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString("token", "token")
        }

        fun saveCard(context: Context, value: String?) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("card", value)
            editor.apply()
        }

        fun getCard(context: Context): String? {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString("card", "")
        }

        fun saveCvv(context: Context, value: String?) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("cvv", value)
            editor.apply()
        }

        fun getCvv(context: Context): String? {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString("cvv", "")
        }

        fun saveExpiry(context: Context, value: String?) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("expiry", value)
            editor.apply()
        }

        fun getExpiry(context: Context): String? {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString("expiry", "")
        }

        fun saveCounter(context: Context, value: String?) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("counter", value)
            editor.apply()
        }

        fun getCounter(context: Context): String? {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getString("counter", "")
        }

        fun saveMin(context: Context, value: Int) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putInt("min", value)
            editor.apply()
        }

        fun getMin(context: Context): Int {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getInt("min", 899)
        }

        fun saveMax(context: Context, value: Int) {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putInt("max", value)
            editor.apply()
        }

        fun getMax(context: Context): Int {
            val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return pref.getInt("max", 999)
        }
    }
}