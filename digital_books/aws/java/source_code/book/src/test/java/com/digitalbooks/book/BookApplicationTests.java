package com.digitalbooks.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.digitalbooks.book.entities.db.TblBookInfo;
import com.digitalbooks.book.entities.db.TblBookSub;
import com.digitalbooks.book.entities.dto.ReaderDto;
import com.digitalbooks.book.entities.payload.BookPayload;
import com.digitalbooks.book.entities.payload.BookPurchasePayload;
import com.digitalbooks.book.exceptions.DigitalBooksException;
import com.digitalbooks.book.repositories.TblBookInfoRepository;
import com.digitalbooks.book.repositories.TblBookSubRepository;
import com.digitalbooks.book.services.BookService;

@SpringBootTest
class BookApplicationTests {

	@Autowired
	private BookService bookService;

	@MockBean
	private TblBookInfoRepository tblBookInfoRepository;

	@MockBean
	private TblBookSubRepository tblBookSubRepository;

	@Test
	void testBuyBook() throws DigitalBooksException {
		BookPurchasePayload bookPurchasePayload = dummyBookPurchasePayloadSupplier.get();
		TblBookInfo tblBookInfo = new TblBookInfo();
		tblBookInfo = dummyTblBookInfoSupplier.get();
		when(tblBookInfoRepository.findById(anyLong())).thenReturn(Optional.ofNullable(tblBookInfo));
		when(tblBookSubRepository.saveAndFlush(any(TblBookSub.class))).thenReturn(new TblBookSub());
		BookPayload bookPayload = bookService.buyBook(bookPurchasePayload);

		assertEquals(1L, bookPayload.getBookDtoList().get(0).getBookId());
		assertEquals(true, bookPayload.getBookDtoList().get(0).getActive());
		assertEquals("author", bookPayload.getBookDtoList().get(0).getAuthor());
		assertEquals("Education", bookPayload.getBookDtoList().get(0).getCategory());
	}

	private Supplier<BookPurchasePayload> dummyBookPurchasePayloadSupplier = () -> {
		BookPurchasePayload bookPurchasePayload = new BookPurchasePayload();
		bookPurchasePayload.setBookId(1L);
		bookPurchasePayload.setPaymentId(2L);
		ReaderDto readerDto = new ReaderDto();
		readerDto.setEmailId("abc@gmail.com");
		readerDto.setName("abcde");
		readerDto.setReaderId(1L);
		bookPurchasePayload.setReaderDto(readerDto);
		return bookPurchasePayload;
	};

	private Supplier<TblBookInfo> dummyTblBookInfoSupplier = () -> {
		TblBookInfo tblBookInfo = new TblBookInfo();
		tblBookInfo.setBookId(1L);
		tblBookInfo.setActive(true);
		tblBookInfo.setAuthor("author");
		tblBookInfo.setCategory("Education");
		return tblBookInfo;
	};

}
