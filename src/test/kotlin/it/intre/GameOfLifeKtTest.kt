package it.intre

import org.junit.Assert.*
import org.junit.Test

class GameOfLifeKtTest{
    @Test
    fun `an empty world evolves in an empty world`() {
        assertEquals(listOf<Char>(), evolve(listOf()))
    }

    @Test
    fun `a world with a single cell evolves in a world with a dead cell`() {
        assertEquals(listOf('.'), evolve(listOf('.')))
    }
}


