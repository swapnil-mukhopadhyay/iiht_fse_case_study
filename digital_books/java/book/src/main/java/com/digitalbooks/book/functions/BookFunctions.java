package com.digitalbooks.book.functions;

import java.time.LocalDate;
import java.util.function.Function;

import com.digitalbooks.book.entities.db.TblBookInfo;
import com.digitalbooks.book.entities.dto.BookDto;

public abstract class BookFunctions {

	public static final Function<BookDto, TblBookInfo> BOOKDTO_TO_TBLBOOKINFO = bookDto -> {
		TblBookInfo tblBookInfo = new TblBookInfo();
		tblBookInfo.setAuthorId(bookDto.getAuthorId());
		tblBookInfo.setLogo(bookDto.getLogo());
		tblBookInfo.setTitle(bookDto.getTitle());
		tblBookInfo.setCategory(bookDto.getCategory());
		tblBookInfo.setPrice(bookDto.getPrice());
		tblBookInfo.setAuthor(bookDto.getAuthor());
		tblBookInfo.setPublisher(bookDto.getPublisher());
		tblBookInfo.setPublishedDate(LocalDate.now());
		tblBookInfo.setActive(bookDto.getActive());
		return tblBookInfo;
	};

	public static final Function<TblBookInfo, BookDto> TBLBOOKINFO_TO_BOOKDTO = tblBookInfo -> {
		BookDto bookDto = new BookDto();
		bookDto.setBookId(tblBookInfo.getBookId());
		bookDto.setLogo(tblBookInfo.getLogo());
		bookDto.setTitle(tblBookInfo.getTitle());
		bookDto.setCategory(tblBookInfo.getCategory());
		bookDto.setPrice(tblBookInfo.getPrice());
		bookDto.setAuthor(tblBookInfo.getAuthor());
		bookDto.setAuthorId(tblBookInfo.getAuthorId());
		bookDto.setPublisher(tblBookInfo.getPublisher());
		bookDto.setPublishedDate(tblBookInfo.getPublishedDate());
		bookDto.setActive(tblBookInfo.getActive());
		return bookDto;
	};

	private BookFunctions() {

	}
}
