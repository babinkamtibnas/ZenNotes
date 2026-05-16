package com.example.zennotes

import com.example.zennotes.data.Note
import org.junit.Assert.assertEquals
import org.junit.Test

class NoteEntityTest {
    @Test
    fun createNote_isCorrect() {
        val timestamp = System.currentTimeMillis()
        val note = Note(id = 1, title = "Test Title", content = "Test Content", timestamp = timestamp)

        assertEquals(1, note.id)
        assertEquals("Test Title", note.title)
        assertEquals("Test Content", note.content)
        assertEquals(timestamp, note.timestamp)
    }
}
