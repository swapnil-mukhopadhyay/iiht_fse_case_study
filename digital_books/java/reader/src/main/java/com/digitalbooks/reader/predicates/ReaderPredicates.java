package com.digitalbooks.reader.predicates;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.digitalbooks.reader.entities.db.TblReaderInfo;
import com.digitalbooks.reader.entities.db.TblReaderPayment;
import com.digitalbooks.reader.entities.dto.BookDto;
import com.digitalbooks.reader.entities.payload.BookPayload;
import com.digitalbooks.reader.entities.payload.BookPurchasePayload;

public abstract class ReaderPredicates {
	
	public static final Predicate<Object> IS_NOT_NULL = (Objects::nonNull);

	public static final Predicate<Object> IS_NULL = IS_NOT_NULL.negate();

	public static final Predicate<String> IS_NOT_NULL_OR_BLANK_STRING = str -> IS_NOT_NULL.test(str)
			&& !str.trim().isEmpty();

	public static final Predicate<String> IS_NULL_OR_BLANK_STRING = str -> IS_NULL.test(str) || str.trim().isEmpty();

	public static final Predicate<Double> IS_POSITIVE_NON_NULL_DOUBLE = num -> IS_NOT_NULL.test(num) && 0.0 <= num;

	public static final Predicate<Boolean> IS_NON_NULL_BOOLEAN = (IS_NOT_NULL::test);

	public static final Predicate<String> IS_NOT_BLANK_OR_EMPTY_STRING = str -> null != str && 0 != str.trim().length();
	
	public static final Predicate<BookPayload> IS_BOOK_PAYLOAD_WITH_ONE_BOOK = bookPayload -> IS_NOT_NULL
			.test(bookPayload) && 1 == bookPayload.getBookDtoList().size();


	public static final Predicate<BookDto> IS_VALID_BOOKDTO_WITHOUT_CONTENT = bookDto -> IS_NOT_BLANK_OR_EMPTY_STRING.test(bookDto.getTitle())
			&& IS_NOT_BLANK_OR_EMPTY_STRING.test(bookDto.getAuthor()) && IS_NOT_BLANK_OR_EMPTY_STRING.test(bookDto.getCategory())
			&& IS_POSITIVE_NON_NULL_DOUBLE.test(bookDto.getPrice())
			&& IS_NOT_BLANK_OR_EMPTY_STRING.test(bookDto.getPublisher()) && IS_NON_NULL_BOOLEAN.test(bookDto.getActive());
	
	public static final Predicate<BookPayload> IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITHOUT_CONTENT = bookPayload -> IS_BOOK_PAYLOAD_WITH_ONE_BOOK
			.test(bookPayload) && IS_VALID_BOOKDTO_WITHOUT_CONTENT.test(bookPayload.getBookDtoList().get(0));
	
	public static final BiPredicate<List<TblReaderPayment>, Long> READER_ALREADY_SUBSCRIBED = 
			(tblReaderPaymentList,bookId) -> tblReaderPaymentList.stream()
					.anyMatch(tblReaderPayment -> !tblReaderPayment.getIsRefunded()
							&& bookId.equals(tblReaderPayment.getBookId()));
			
	public static final Predicate<TblReaderPayment> IS_WITHIN_REFUND_DURATION = tblReaderPayment -> 24 > Duration
			.between(tblReaderPayment.getPaymentDateTime(), LocalDateTime.now()).toHours();
	
	public static final Predicate<BookDto> IS_VALID_BOOKDTO_WITH_CONTENT = bookDto -> IS_NOT_NULL_OR_BLANK_STRING
			.test(bookDto.getTitle()) && IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getAuthor())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getCategory())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getContent())
			&& IS_POSITIVE_NON_NULL_DOUBLE.test(bookDto.getPrice())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getPublisher())
			&& IS_NON_NULL_BOOLEAN.test(bookDto.getActive());

	public static final Predicate<BookPayload> IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITH_CONTENT = bookPayload -> IS_BOOK_PAYLOAD_WITH_ONE_BOOK
			.test(bookPayload) && IS_VALID_BOOKDTO_WITH_CONTENT.test(bookPayload.getBookDtoList().get(0));

	public static final Predicate<BookPurchasePayload> IS_VALID_BOOK_PURCHASE_PAYLOAD = bookPurchasePayload -> IS_NOT_NULL
			.test(bookPurchasePayload.getBookId()) && IS_NOT_NULL.test(bookPurchasePayload.getReaderDto())
			&& IS_NOT_NULL.test(bookPurchasePayload.getReaderDto().getName())
			&& IS_NOT_NULL.test(bookPurchasePayload.getReaderDto().getEmailId())
			&& IS_NOT_NULL.test(bookPurchasePayload.getReaderDto().getReaderId());
	
	public static final BiPredicate<BookPurchasePayload, TblReaderInfo> IS_VALID_ACCOUNT_INFO = (bookPurchasePayload,
			tblReaderInfo) -> bookPurchasePayload.getReaderDto().getName().equals(tblReaderInfo.getName())
					&& bookPurchasePayload.getReaderDto().getEmailId().equals(tblReaderInfo.getEmailId());

	private ReaderPredicates() {

	}

}
