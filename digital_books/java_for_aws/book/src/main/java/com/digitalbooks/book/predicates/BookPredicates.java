package com.digitalbooks.book.predicates;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.digitalbooks.book.entities.db.TblBookInfo;
import com.digitalbooks.book.entities.dto.BookDto;
import com.digitalbooks.book.entities.payload.BookPayload;
import com.digitalbooks.book.entities.payload.BookPurchasePayload;

public abstract class BookPredicates {

	public static final Predicate<Object> IS_NOT_NULL = (Objects::nonNull);

	public static final Predicate<Object> IS_NULL = IS_NOT_NULL.negate();

	public static final Predicate<String> IS_NOT_NULL_OR_BLANK_STRING = str -> IS_NOT_NULL.test(str)
			&& !str.trim().isEmpty();

	public static final Predicate<String> IS_NULL_OR_BLANK_STRING = str -> IS_NULL.test(str) || str.trim().isEmpty();

	public static final Predicate<Double> IS_POSITIVE_NON_NULL_DOUBLE = num -> IS_NOT_NULL.test(num) && 0.0 <= num;

	public static final Predicate<Boolean> IS_NON_NULL_BOOLEAN = (IS_NOT_NULL::test);

	public static final Predicate<BookDto> IS_VALID_BOOKDTO_WITH_CONTENT = bookDto -> IS_NOT_NULL_OR_BLANK_STRING
			.test(bookDto.getTitle()) && IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getAuthor())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getCategory())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getContent())
			&& IS_POSITIVE_NON_NULL_DOUBLE.test(bookDto.getPrice())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getPublisher())
			&& IS_NON_NULL_BOOLEAN.test(bookDto.getActive());

	public static final Predicate<BookPayload> IS_BOOK_PAYLOAD_WITH_ONE_BOOK = bookPayload -> IS_NOT_NULL
			.test(bookPayload) && 1 == bookPayload.getBookDtoList().size();

	public static final Predicate<BookPayload> IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITH_CONTENT = bookPayload -> IS_BOOK_PAYLOAD_WITH_ONE_BOOK
			.test(bookPayload) && IS_VALID_BOOKDTO_WITH_CONTENT.test(bookPayload.getBookDtoList().get(0));

	public static final Predicate<BookPurchasePayload> IS_VALID_BOOK_PURCHASE_PAYLOAD = bookPurchasePayload -> IS_NOT_NULL
			.test(bookPurchasePayload.getBookId()) && IS_NOT_NULL.test(bookPurchasePayload.getReaderDto())
			&& IS_NOT_NULL.test(bookPurchasePayload.getReaderDto().getName())
			&& IS_NOT_NULL.test(bookPurchasePayload.getReaderDto().getEmailId())
			&& IS_NOT_NULL.test(bookPurchasePayload.getReaderDto().getReaderId());

	public static final Predicate<TblBookInfo> IS_ACTIVE_BOOK = (TblBookInfo::getActive);

	public static final BiPredicate<BookPurchasePayload, TblBookInfo> IS_VALID_PURCHASE = (bookPurchasePayload,
			tblBookInfo) -> bookPurchasePayload.getBookId().equals(tblBookInfo.getBookId())
					&& tblBookInfo.getTblBookSubList().stream().anyMatch(tblBookSub -> bookPurchasePayload
							.getReaderDto().getReaderId().equals(tblBookSub.getReaderId()));

	public static final BiPredicate<BookDto, TblBookInfo> IS_VALID_AUTHOR = (bookDto,
			tblBookInfo) -> bookDto.getAuthorId().equals(tblBookInfo.getAuthorId())
					&& bookDto.getAuthor().equals(tblBookInfo.getAuthor());

	private BookPredicates() {

	}

}
