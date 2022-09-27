package com.digitalbooks.author.predicates;

import java.util.Objects;
import java.util.function.Predicate;

import com.digitalbooks.author.entities.dto.BookDto;
import com.digitalbooks.author.entities.payload.AuthorPayload;
import com.digitalbooks.author.entities.payload.BookPayload;

public abstract class AuthorPredicates {

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

	public static final Predicate<BookDto> IS_VALID_BOOKDTO_WITHOUT_CONTENT = bookDto -> IS_NOT_BLANK_OR_EMPTY_STRING
			.test(bookDto.getTitle()) && IS_NOT_BLANK_OR_EMPTY_STRING.test(bookDto.getAuthor())
			&& IS_NOT_BLANK_OR_EMPTY_STRING.test(bookDto.getCategory())
			&& IS_POSITIVE_NON_NULL_DOUBLE.test(bookDto.getPrice())
			&& IS_NOT_BLANK_OR_EMPTY_STRING.test(bookDto.getPublisher())
			&& IS_NON_NULL_BOOLEAN.test(bookDto.getActive());

	public static final Predicate<BookPayload> IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITHOUT_CONTENT = bookPayload -> IS_BOOK_PAYLOAD_WITH_ONE_BOOK
			.test(bookPayload) && IS_VALID_BOOKDTO_WITHOUT_CONTENT.test(bookPayload.getBookDtoList().get(0));

	public static final Predicate<BookDto> IS_VALID_BOOKDTO_WITH_CONTENT = bookDto -> IS_NOT_NULL_OR_BLANK_STRING
			.test(bookDto.getTitle()) && IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getAuthor())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getCategory())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getContent())
			&& IS_POSITIVE_NON_NULL_DOUBLE.test(bookDto.getPrice())
			&& IS_NOT_NULL_OR_BLANK_STRING.test(bookDto.getPublisher())
			&& IS_NON_NULL_BOOLEAN.test(bookDto.getActive());

	public static final Predicate<BookPayload> IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITH_CONTENT = bookPayload -> IS_BOOK_PAYLOAD_WITH_ONE_BOOK
			.test(bookPayload) && IS_VALID_BOOKDTO_WITH_CONTENT.test(bookPayload.getBookDtoList().get(0));

	public static final Predicate<AuthorPayload> IS_VALID_AUTHOR_PAYLOAD = authorPayload -> IS_NOT_NULL
			.test(authorPayload.getAuthorId()) && 1 == authorPayload.getBookDtoList().size()
			&& IS_VALID_BOOKDTO_WITH_CONTENT.test(authorPayload.getBookDtoList().get(0));

	private AuthorPredicates() {

	}
}
