package com.koweg.demo.puzzle.service

import com.koweg.demo.puzzle.controller.PuzzleDTO
import com.koweg.demo.puzzle.controller.PuzzleResponseDTO
import com.koweg.demo.puzzle.controller.PuzzleResultDTO
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.PostConstruct
import kotlin.collections.HashMap
import kotlin.random.Random

@Service
class WordPuzzleService {

    companion object {
        private val  MIN_ID = Integer.valueOf(1)
    }
    private val idGenerator = AtomicInteger(MIN_ID)
    private val puzzleRepository = HashMap<Int, Puzzle>()

    fun generatePuzzle() : PuzzleDTO{
        val id = Random.nextInt(MIN_ID,puzzleRepository.size)
        val word = puzzleRepository.get(id)

        return PuzzleDTO(id.toString(), word!!.maskedWord)
    }

    fun validateAnswer(puzzleResponseDTO: PuzzleResponseDTO) : PuzzleResultDTO {
        val puzzle = puzzleRepository.get(puzzleResponseDTO.id.toInt())
        if (puzzleResponseDTO.response.lowercase() == puzzle?.unMaskedWord?.lowercase())
            return PuzzleResultDTO(true, puzzle!!.unMaskedWord)
        return PuzzleResultDTO(false, puzzle!!.unMaskedWord)
    }

    @PostConstruct
    fun loadWordPuzzles(){
        BufferedReader(InputStreamReader(ClassPathResource("data/puzzles.txt").inputStream))
            .readLines()
            .forEach { puzzleRepository.put(idGenerator.getAndIncrement(), Puzzle(it.toString(), maskWord(it.toString(), 3))) }
    }

    fun maskWord(word: String, maskOffset: Int): String {
        val maskedWord = StringBuilder()
        for ((index, value) in word.withIndex()){
            if(index%maskOffset==0) {
                maskedWord.append("*")
            }else{
                maskedWord.append(value)
            }
        }
        return maskedWord.toString()
    }
}

data class Puzzle(val unMaskedWord: String, val maskedWord: String)
