/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
public class SetOfNaturalsTest {
    private SetOfNaturals setA;
    private SetOfNaturals setB;
    private SetOfNaturals setC;
    private SetOfNaturals setD;

    @BeforeEach
    public void setUp() {
        setA = new SetOfNaturals();
        setB = SetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});

        setC = new SetOfNaturals();
        for (int i = 5; i < 50; i++) {
            setC.add(i * 10);
        }
        setD = SetOfNaturals.fromArray(new int[]{30, 40, 50, 60, 10, 20});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        setB.add(11);
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(7, setB.size(), "add: elements count not as expected.");
    }

    @Test
    public void testAddBadArray() {
        int[] elems = new int[]{10, 20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void testAddArrayDuplicates() {
        int[] elems = new int[]{11, 12, 13, 14, 11};

        /**
         * it is forbidden to have 2 same values on a set
         */

        assertThrows(IllegalArgumentException.class, () -> setB.add(elems),
                "duplicate numbers are forbidden on a set (add)");
    }

    @Test
    public void testCreateArrayDuplicates() {
        int[] elems = new int[]{11, 12, 13, 14, 11};

        assertThrows(IllegalArgumentException.class, () -> SetOfNaturals.fromArray(elems),
                "duplicate numbers are forbidden on a set (fromArray)");
    }

    @Test
    public void testAddDuplicate() {
        /**
         * the test above only tests if the set allows negative numbers to be added
         * so, it\'s necessary to check if duplicate numbers can be added
         */
        assertThrows(IllegalArgumentException.class, () -> setB.add(10),
                "Duplicate numbers shouldn\'t be allowed");

    }



    @Test
    public void testIntersectForNoIntersection() {
        assertFalse(setA.intersects(setB), "no intersection but was reported as existing");

    }

    @Test
    public void testIntersectForIntersection() {
        /**
         * verify if intersection is noted
         * it was necessary to implemnt the method on class SetOfNaturals because it wasn\'t implemented
         */
        assertTrue(setC.intersects(setB), "intersection but was reported as not existing");
    }


}
