package com.tutorial.cucumber.book;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private final List<Book> store = new ArrayList<Book>();

    public void addBook(final Book book) {
        this.store.add(book);
    }

    public List<Book> findBooks(final Date from, final Date to) {
        Calendar end = Calendar.getInstance();
        end.setTime(to);
        end.roll(Calendar.YEAR, 1);

        // return from.before(book.getPublished()) && end.getTime().after(book.getPublished()
        return store.stream().filter(
                book -> {
                    return from.before(book.getPublished()) && end.getTime().after(book.getPublished());
                }
        ).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
    }

    public List<Book> findBooksByAuthor(String author) {
        return store.stream().filter(
                book -> book.getAuthor().equals(author)
        ).collect(Collectors.toList());
    }

    public List<Book> findBookByTitle(String title) {
        return store.stream().filter(
                book -> book.getTitle().equals(title)
        ).collect(Collectors.toList());
    }

    public List<Book> findBookIfTitleContainsString(String titlePortion) {
        return store.stream().filter(
                book -> book.getTitle().contains(titlePortion)
        ).collect(Collectors.toList());
    }
}
