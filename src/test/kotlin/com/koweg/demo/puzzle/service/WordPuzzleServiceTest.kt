package com.koweg.demo.puzzle.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WordPuzzleServiceTest {

    @Autowired
    private lateinit var wordPuzzleService: WordPuzzleService

    @Test
    fun `should load word puzzles from text file`(){
        val puzzle = wordPuzzleService.generatePuzzle();
        assertThat(puzzle).isNotNull

    }
}