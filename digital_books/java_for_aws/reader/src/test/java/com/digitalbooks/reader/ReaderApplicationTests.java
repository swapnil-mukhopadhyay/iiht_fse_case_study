package com.digitalbooks.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.digitalbooks.reader.clients.BookClient;
import com.digitalbooks.reader.entities.db.TblReaderInfo;
import com.digitalbooks.reader.entities.dto.BookDto;
import com.digitalbooks.reader.entities.payload.BookPayload;
import com.digitalbooks.reader.entities.payload.ReaderPayload;
import com.digitalbooks.reader.exceptions.DigitalBooksException;
import com.digitalbooks.reader.repositories.TblReaderInfoRepository;
import com.digitalbooks.reader.services.ReaderService;

@SpringBootTest
class ReaderApplicationTests {

	@Autowired
	private ReaderService readerService;

	@MockBean
	private TblReaderInfoRepository tblReaderInfoRepository;

	@MockBean
	private BookClient bookClient;

	@Test
	void testGetSubscribedBooks() throws DigitalBooksException {
		TblReaderInfo tblReaderInfo = dummyTblReaderInfoSupplier.get();
		BookPayload bookPayload = dummyBookPayloadSupplier.get();
		when(tblReaderInfoRepository.findByEmailId(anyString())).thenReturn(Optional.ofNullable(tblReaderInfo));
		when(bookClient.getSubscribedBooks(anyLong())).thenReturn(bookPayload);
		ReaderPayload readerPayload = readerService.getSubscribedBooks("abc@gmail.com");
		assertEquals(1L, readerPayload.getBookDtoList().get(0).getBookId());
		assertEquals("author", readerPayload.getBookDtoList().get(0).getAuthor());
		assertEquals(true, readerPayload.getBookDtoList().get(0).getActive());
	}

	private Supplier<BookPayload> dummyBookPayloadSupplier = () -> {
		BookPayload bookPayload = new BookPayload();
		BookDto bookDto = new BookDto();
		bookDto.setActive(true);
		bookDto.setBookId(1L);
		bookDto.setAuthor("author");
		List<BookDto> bookDtoList = new ArrayList<>();
		bookDtoList.add(bookDto);
		bookPayload.setBookDtoList(bookDtoList);
		return bookPayload;
	};

	private Supplier<TblReaderInfo> dummyTblReaderInfoSupplier = () -> {
		TblReaderInfo tblReaderInfo = new TblReaderInfo();
		tblReaderInfo.setReaderId(1L);
		tblReaderInfo.setEmailId("abc@gmail.com");
		tblReaderInfo.setName("abcde");
		return tblReaderInfo;
	};

}
