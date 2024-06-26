/*
 * Course: CSC1120A 121
 * Spring 2023
 * Lab 8 - JUnit testing
 * Name: Michael Wood
 * Created: 3/7/2024
 */
package woodm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to ensure all implementations of the AutoCompleter interface work properly.
 */
class AutoCompleterTest {
    private AutoCompleter autoCompleter;

    @BeforeEach
    void setUp() {
        autoCompleter = new HashTable(new ArrayList<>());
    }

    @AfterEach
    void tearDown() {
        autoCompleter = null;
    }

    @Test
    void getBackingClassTest() {
        autoCompleter = new UnorderedList(new ArrayList<>());
        assertEquals("java.util.ArrayList", autoCompleter.getBackingClass());
        autoCompleter = new OrderedList(new LinkedList<>());
        assertEquals("java.util.LinkedList", autoCompleter.getBackingClass());
        autoCompleter = new BinarySearchTree(new ArrayList<>());
        assertEquals("java.util.TreeSet", autoCompleter.getBackingClass());
        autoCompleter = new Trie(new ArrayList<>());
        assertEquals("woodm.ListMap", autoCompleter.getBackingClass());
        autoCompleter = new HashTable(new ArrayList<>());
        assertEquals("java.util.HashSet", autoCompleter.getBackingClass());
    }

    @Test
    void addTest() {
        assertTrue(autoCompleter.add("Hello"));
        assertFalse(autoCompleter.add("Hello"));
        assertTrue(autoCompleter.add("hello"));
        assertTrue(autoCompleter.add("help"));
        assertThrows(IllegalArgumentException.class, () -> autoCompleter.add(""));
        assertThrows(IllegalArgumentException.class, () -> autoCompleter.add(null));
    }

    @Test
    void sizeTest() {
        assertEquals(0, autoCompleter.size());
        autoCompleter.add("Hello");
        assertEquals(1, autoCompleter.size());
        autoCompleter.add("Hello");
        assertEquals(1, autoCompleter.size());
        autoCompleter.add("help");
        assertEquals(2, autoCompleter.size());
    }

    @Test
    void exactMatchTest() {
        autoCompleter.add("Hello");
        assertTrue(autoCompleter.exactMatch("Hello"));
        assertFalse(autoCompleter.exactMatch("hello"));
        autoCompleter.add("help");
        assertTrue(autoCompleter.exactMatch("help"));
        assertFalse(autoCompleter.exactMatch(null));
        assertFalse(autoCompleter.exactMatch(""));
    }

    @Test
    void allMatchesTest() {
        autoCompleter.add("hello");
        autoCompleter.add("hello world");
        autoCompleter.add("Hello");
        autoCompleter.add("hi");
        autoCompleter.add("help!");
        autoCompleter.add("h");
        String[] helMatches = {"hello", "hello world", "help!"};
        String[] hMatches = {"hello", "hello world", "hi", "help!", "h"};
        String[] matches = {"hello", "hello world", "Hello", "hi", "help!", "h"};
        Arrays.sort(helMatches);
        Arrays.sort(hMatches);
        Arrays.sort(matches);
        String[] actualHelMatches = autoCompleter.allMatches("hel");
        String[] actualHMatches = autoCompleter.allMatches("h");
        String[] actualAllMatches = autoCompleter.allMatches("");
        Arrays.sort(actualHelMatches);
        Arrays.sort(actualHMatches);
        Arrays.sort(actualAllMatches);
        assertArrayEquals(helMatches, actualHelMatches);
        assertArrayEquals(hMatches, actualHMatches);
        assertArrayEquals(new String[0], autoCompleter.allMatches(null));
        assertArrayEquals(new String[0], autoCompleter.allMatches("HI"));
        assertArrayEquals(matches, actualAllMatches);
    }

    @Test
    void formatTest() {
        final long test1 = 192_720_000_000_000L;
        final long test2 = 51_728_000_000_000L;
        final long test3 = 2_575_300_000_000L;
        final long test4 = 18_800_000_000L;
        final long test5 = 998_800_000;
        final long test6 = 318_800;
        final long test7 = 7;
        final long test8 = 998_799_980;
        final long test9 = 51_727_999_999_980L;
        long nanoseconds = test1;
        assertEquals("2 day(s) 5 hour(s) 32 minute(s)", AutoCompleter.format(nanoseconds));
        nanoseconds = test2;
        assertEquals("14 hour(s) 22 minute(s) 8 second(s)", AutoCompleter.format(nanoseconds));
        nanoseconds = test3;
        assertEquals("42 minute(s) 55.3 second(s)", AutoCompleter.format(nanoseconds));
        nanoseconds = test4;
        assertEquals("18.8 second(s)", AutoCompleter.format(nanoseconds));
        nanoseconds = test5;
        assertEquals("998.8 millisecond(s)", AutoCompleter.format(nanoseconds));
        nanoseconds = test6;
        assertEquals("318.8 microsecond(s)", AutoCompleter.format(nanoseconds));
        nanoseconds = test7;
        assertEquals("7 nanosecond(s)", AutoCompleter.format(nanoseconds));
        nanoseconds = test8;
        assertEquals("998.8 millisecond(s)", AutoCompleter.format(nanoseconds));
        nanoseconds = test9;
        assertEquals("14 hour(s) 22 minute(s) 8 second(s)", AutoCompleter.format(nanoseconds));
    }

    @Test
    void getStringTest() {
        assertThrows(IllegalArgumentException.class, () -> AutoCompleter.getString(0));
        assertThrows(IllegalArgumentException.class, () -> AutoCompleter.getString(-1));
        assertEquals(1, AutoCompleter.getString(1).length());
        final int length = 100;
        String str = AutoCompleter.getString(length);
        boolean isChar = true;
        for(int i = 0; isChar && i < length; i++) {
            if(!Character.isLetter(str.charAt(i))) {
                isChar = false;
            }
        }
        assertTrue(isChar);
    }
}