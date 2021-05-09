package deti.tqs.BookTestContainer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class BookTestContainerApplicationTests {

	Book book;

	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer()
			.withUsername("orlando")
			.withPassword("orlando")
			.withDatabaseName("test");

	@Autowired
	private BookRepository bookRepository;

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

	@Test
	@Order(1)
	void contextLoads() {
		book = new Book();
		book.setName("O Perfume");

		System.out.println("Context loads!");
	}

	@Test
	@Order(2)
	void createBookTest() {
		this.bookRepository.save(book);
	}

	@Test
	@Order(2)
	void verifyBookTest() {
		Assertions.assertThat(
				this.bookRepository.findAll()
		).contains(book);
	}

}
