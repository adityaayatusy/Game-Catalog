@file:Suppress("SpellCheckingInspection")

package com.aditya.dicoding.gamecatalog.core.utils

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Suppress("SpellCheckingInspection")
class ExtensionTest {

    @Test
    fun `when extends afterComa should return float`(){
        val data = 40.123325f.afterComa("#.#")
        Assert.assertEquals(data, 40.1f)
    }

    @Test
    fun `when extends dateFormat should return format date`(){
        val date = Date.from(LocalDate.of(2023, 4, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()).dateFormat()
        Assert.assertEquals(date, "Apr 05, 2023")
    }

    @Test
    fun `when extends has should return boolean`(){
        val data = "Test kalimat".has("kalimat")
        Assert.assertTrue(data)
    }

}