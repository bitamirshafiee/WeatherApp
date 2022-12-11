package com.ext

import com.weatherapp.ext.format
import org.junit.Test

class Extensions {

    @Test
    fun doubleFormat(){
        val number = 12.1534567890
        val result = number.format(2)
        assert(result == 12.15)
    }
}