package com.quest.etna.service;

import com.quest.etna.model.Book;
import com.quest.etna.model.Erreur;
import com.quest.etna.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}


	public List<Book> getList(Integer page, Integer limit) {
		PageRequest pageable = PageRequest.of(page, limit);
		return bookRepository.getListByPage(pageable);
	}


	public List<Book> getByName(String title) {
		List<Book> books = bookRepository.findByTitle(title);
		return books;
	}
	
	
	  public List<Book> getByAuthor(String author) { List<Book> books =
	  bookRepository.findByAuthor(author); return books; }


	public ResponseEntity<?> createBook(Book book) {
		bookRepository.save(book);
	    return ResponseEntity
                .status(HttpStatus.OK)
                .body(book);
	}


	public ResponseEntity<?> update(int id, Book entity) {
		Optional<Book> book = bookRepository.findById(id);
		Book bookFound = book.get();
		bookFound.setTitle(entity.getTitle());;
		bookFound.setAuthor(entity.getAuthor());;
		bookFound.setImage(entity.getImage());
		bookFound.setDate_publication(entity.getDate_publication());
		bookFound.setDescription(entity.getDescription());
		bookFound.setPages(entity.getPages());
		bookFound.setQuantity(entity.getQuantity());
		bookRepository.save(bookFound);
		return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookFound);
	}
	
	
	public ResponseEntity<?> updateQuantity(int id, int quantity) {
		Optional<Book> book = bookRepository.findById(id);
		Book bookFound = book.get();
		bookFound.setQuantity(bookFound.getQuantity()-quantity);
		bookRepository.save(bookFound);
		return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookFound);
	}


	public ResponseEntity<?> delete(int id) {
		Optional<Book> book = bookRepository.findById(id);
		Book bookFound = book.get();
		bookRepository.delete(bookFound);
		return ResponseEntity
                .status(HttpStatus.OK)
                .body(book);
	}

	public ResponseEntity<List<Book>> checkAllBooks() {
        List<Book> listBook = this.bookRepository.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listBook);
    }

    public ResponseEntity<Book> checkBookById(int id) {
            Optional<Book> book = this.bookRepository.findById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(book.get());
    }

	public ResponseEntity<?> partialUpdate(int id, Map<String, Object> updates) {
		try {
			Optional<Book> bookOptional = bookRepository.findById(id);
			if (!bookOptional.isPresent()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new Erreur("Livre non trouvé"));
			}

			Book book = bookOptional.get();

			// Parcourez les mises à jour et mettez à jour les champs correspondants
			for (Map.Entry<String, Object> entry : updates.entrySet()) {
				String field = entry.getKey();
				Object value = entry.getValue();

				switch (field) {
					case "title":
						book.setTitle((String) value);
						break;
					case "description":
						book.setDescription((String) value);
						break;
					case "author":
						book.setAuthor((String) value);
						break;
					case "pages":
						book.setPages((int) value);
						break;
					case "quantity":
						book.setQuantity((int) value);
						break;
					case "image":
						book.setImage((String) value);
						break;
					case "date_publication":
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						try {
							Date date = dateFormat.parse((String) value);
							book.setDate_publication(date);
						} catch (ParseException e) {
							// Gérez les erreurs de format de date ici
							e.printStackTrace();
						}
						break;
				}
			}

			// Enregistrez le livre mis à jour dans la base de données
			bookRepository.save(book);

			return ResponseEntity.ok(book);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new Erreur("Mauvaise requête"));
		}
	}


}
