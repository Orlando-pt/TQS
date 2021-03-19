package stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTest {

    private TqsStack<String> stack;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        stack = new TqsStack<String>();
        assertTrue(stack.isEmpty(), "A stack is empty on construction");
        assertEquals(0, stack.size(), "A stack is size 0 on construction");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @DisplayName("Check the push of 3 items")
    @Test
    void push3Items() {
        stack.push("Aveiro");
        stack.push("Coimbra");
        stack.push("Lisboa");

        assertFalse(stack.isEmpty(), "After n pushes the stack is not empty");
        assertEquals(3, stack.size(), "After n pushes, the stack should have size equals 3");
    }

    @Test
    void push() {
        stack.push("Aveiro");
        assertEquals("Aveiro", stack.pop(), "If one pushes x then pops, the value popped is x");
    }

    @Test
    void pop() {
    }

    @Test
    void peek() {
        stack.push("Aveiro");
        assertEquals("Aveiro", stack.peek(), "If one pushes x then peeks, the value returned is x");
        assertEquals(1, stack.size(), "If one pushes x then peeks, the size stays the same");
    }

    @Test
    void size() {
        stack.push("Aveiro");
        stack.push("Coimbra");
        stack.push("Lisboa");

        assertEquals(3, stack.size(), "After n pushes, the size is n");

        stack.pop();
        stack.pop();
        stack.pop();

        assertEquals(0, stack.size(), "After n pops, the size is 0");
    }

    @Test
    void isEmpty() {
        stack.push("Aveiro");
        stack.push("Lisboa");
        assertFalse(stack.isEmpty(), "After n consecutive pushes, the stack is not empty");
    }

    @Test
    void popFromEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.pop(), "Popping from an empty stack does throw a NoSuchElementException");
    }

    @Test
    void peekFromEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.peek(), "Peeking from an empty stack does throw a NoSuchElementException");
    }
}