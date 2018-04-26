package it.intre

import org.junit.Assert.*
import org.junit.Test

class GameOfLifeKtTest{
    @Test
    fun `an empty world evolves in an empty world`() {
        assertEquals(listOf<Char>(), evolve(listOf()))
    }

}


