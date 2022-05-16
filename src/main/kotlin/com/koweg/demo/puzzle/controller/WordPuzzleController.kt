package com.koweg.demo.puzzle.controller

import com.koweg.demo.puzzle.service.WordPuzzleService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class WordPuzzleController(val wordPuzzleService: WordPuzzleService) {

    @CrossOrigin(origins = ["*"])
    @GetMapping("/api/puzzles", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun generatePuzzle(): PuzzleDTO {
        return wordPuzzleService.generatePuzzle()
    }

    @CrossOrigin(origins = ["*"])
    @PostMapping("/api/puzzles", produces = arrayOf(MediaType.APPLICATION_JSON_VALUE), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun validatePuzzleAnswer(@RequestBody puzzleResponseDTO: PuzzleResponseDTO): PuzzleResultDTO{
        return wordPuzzleService.validateAnswer(puzzleResponseDTO)
    }

}

data class PuzzleDTO(val id: String, val puzzle: String)

data class PuzzleResponseDTO(val id: String, val response: String)

data class PuzzleResultDTO(val isCorrect: Boolean, val result: String)
