package com.koweg.demo.puzzle.controller

import com.koweg.demo.puzzle.service.WordPuzzleService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(WordPuzzleController::class)
class WordPuzzleControllerTest(@Autowired val mockMvc: MockMvc){

    @MockkBean
    private lateinit var wordPuzzleService: WordPuzzleService

    @Test
    fun `should generate new spelling puzzle`(){
        val puzzleId = UUID.randomUUID().toString()
        val puzzle = "*e*om*cs"
        every { wordPuzzleService.generatePuzzle() } returns PuzzleDTO(puzzleId, puzzle)

        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/puzzles"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(puzzleId))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.puzzle").value(puzzle))

        verify { wordPuzzleService.generatePuzzle()}
    }

    @Test
    fun `should validate response to puzzle`(){
        val id = UUID.randomUUID().toString()
        val response = "genomics"
        val puzzleResponseDTO = PuzzleResponseDTO(id, response)
        val puzzleResultDTO = PuzzleResultDTO(true, response)

        every { wordPuzzleService.validateAnswer(puzzleResponseDTO) } returns puzzleResultDTO

        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/puzzles")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":\"$id\", \"response\":\"$response\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(containsString("isCorrect")))
            .andExpect(MockMvcResultMatchers.content().string(containsString(response)))

        verify { wordPuzzleService.validateAnswer(any())}

    }

}

