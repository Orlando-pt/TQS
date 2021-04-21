package com.tutorial.cucumber.book;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookSearchSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public Date iso8601Date(String year, String month, String day) {
        return textToDate(year, month, day);
    }

    @Given("(a|another) book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addNewBook(final String title, final String author, final Date published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("the customer searches for books published between {int} and {int}")
    public void setSearchParameters(final int from, final int to) {
        result = library.findBooks(yearToDate(from), yearToDate(to));
    }

    @Then("{int} (books|book) should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        MatcherAssert.assertThat(result.size(), CoreMatchers.equalTo(booksFound));
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        MatcherAssert.assertThat(result.get(position - 1).getTitle(), CoreMatchers.equalTo(title));
    }

    private Date textToDate(String year, String month, String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return calendar.getTime();
    }

    private Date yearToDate(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 0, 0);
        return calendar.getTime();
    }

    // Author search methods
    @Then("the customer searches for the books with the authorship of {string}")
    public void searchForAuthorship(final String author) {
        result = library.findBooksByAuthor(author);
    }

    // Title search methods
    @Then("the customer searches for book with title {string}")
    public void searchForTitle(final String title) {
        result = library.findBookByTitle(title);
    }

    @Then("the customer searches for books with {string} on its title")
    public void searchForTitleWithStringPortion(String stringPortion) {
        result = library.findBookIfTitleContainsString(stringPortion);
    }

}
