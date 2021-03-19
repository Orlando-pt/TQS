package stack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundedTqsStackTest {

    private BoundedTqsStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new BoundedTqsStack<String>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void maxBoundedStackException() {
        for (char a = 'a'; ; a++) {
            if (stack.size() == 20)
                break;
            stack.push(String.valueOf(a));
        }

        assertThrows(IllegalStateException.class, () -> stack.push("z"), "Pushing onto a full stack does throw an IllegalStateException");
    }
}