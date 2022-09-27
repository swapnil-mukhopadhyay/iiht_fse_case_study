package com.digitalbooks.book.services;

import static com.digitalbooks.book.constants.DigitalBooksExceptionConstants.BOOK_BLOCKED_OR_DOESNT_EXIST;
import static com.digitalbooks.book.constants.DigitalBooksExceptionConstants.INVALID_PAYLOAD;
import static com.digitalbooks.book.constants.DigitalBooksExceptionConstants.STATUS_CODE_BOOK_BLOCKED_OR_DOESNT_EXIST;
import static com.digitalbooks.book.constants.DigitalBooksExceptionConstants.STATUS_CODE_INVALID_PAYLOAD;
import static com.digitalbooks.book.functions.BookFunctions.BOOKDTO_TO_TBLBOOKINFO;
import static com.digitalbooks.book.functions.BookFunctions.TBLBOOKINFO_TO_BOOKDTO;
import static com.digitalbooks.book.predicates.BookPredicates.IS_ACTIVE_BOOK;
import static com.digitalbooks.book.predicates.BookPredicates.IS_NOT_NULL;
import static com.digitalbooks.book.predicates.BookPredicates.IS_NULL_OR_BLANK_STRING;
import static com.digitalbooks.book.predicates.BookPredicates.IS_VALID_AUTHOR;
import static com.digitalbooks.book.predicates.BookPredicates.IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITH_CONTENT;
import static com.digitalbooks.book.predicates.BookPredicates.IS_VALID_BOOK_PURCHASE_PAYLOAD;
import static com.digitalbooks.book.predicates.BookPredicates.IS_VALID_PURCHASE;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.digitalbooks.book.entities.db.TblBookContent;
import com.digitalbooks.book.entities.db.TblBookInfo;
import com.digitalbooks.book.entities.db.TblBookSub;
import com.digitalbooks.book.entities.dto.BookDto;
import com.digitalbooks.book.entities.payload.BookPayload;
import com.digitalbooks.book.entities.payload.BookPurchasePayload;
import com.digitalbooks.book.exceptions.DigitalBooksException;
import com.digitalbooks.book.interfaces.BookIf;
import com.digitalbooks.book.repositories.TblBookInfoRepository;
import com.digitalbooks.book.repositories.TblBookSubRepository;
import com.digitalbooks.common.models.NotificationPayload;

@Service
@Transactional
public class BookService implements BookIf{
	
	@Autowired
	private TblBookInfoRepository tblBookInfoRepository;
	
	@Autowired
	private TblBookSubRepository tblBookSubRepository;
	
	@Autowired
	private KafkaTemplate<String, NotificationPayload> kafkaTemplate;
	
	private static final String READER_NOTIFICATION_TOPIC= "notification";

	@Override
	public BookPayload searchBooks(String category, String author, Double price, String publisher) {
		
		List<BookDto> bookDtoList= tblBookInfoRepository.findAll().stream()
				.filter(TblBookInfo::getActive)
				.filter(tblBookInfo->IS_NULL_OR_BLANK_STRING.test(category) || tblBookInfo.getCategory().equalsIgnoreCase(category))
				.filter(tblBookInfo-> IS_NULL_OR_BLANK_STRING.test(author) || tblBookInfo.getAuthor().equalsIgnoreCase(author))
				.filter(tblBookInfo-> IS_NOT_NULL.test(price) || tblBookInfo.getPrice()<=price)
				.filter(tblBookInfo-> IS_NULL_OR_BLANK_STRING.test(publisher) || tblBookInfo.getPublisher().equalsIgnoreCase(publisher))
				.map(TBLBOOKINFO_TO_BOOKDTO)
				.collect(Collectors.toList());
		
		NotificationPayload notificationPayload = new NotificationPayload();
		notificationPayload.setBookId(1l);
		notificationPayload.setMessage("hello");
		return setBookPayload(bookDtoList);
	}

	@Override
	public BookPayload buyBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {
		List<BookDto> bookDtoList = new ArrayList<>();
		if (IS_VALID_BOOK_PURCHASE_PAYLOAD.test(bookPurchasePayload)) {
			Optional<TblBookInfo> optionalTblBookInfo = tblBookInfoRepository.findById(bookPurchasePayload.getBookId());
			if (optionalTblBookInfo.isPresent() && IS_ACTIVE_BOOK.test(optionalTblBookInfo.get())) {
				TblBookInfo tblBookInfo = createTblBookSubEntry(bookPurchasePayload, optionalTblBookInfo.get());
				bookDtoList.add(TBLBOOKINFO_TO_BOOKDTO.apply(tblBookInfo));
			} else {
				throw new DigitalBooksException(STATUS_CODE_BOOK_BLOCKED_OR_DOESNT_EXIST,BOOK_BLOCKED_OR_DOESNT_EXIST);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD,INVALID_PAYLOAD);
		}
		return setBookPayload(bookDtoList);
	}

	private TblBookInfo createTblBookSubEntry(BookPurchasePayload bookPurchasePayload, TblBookInfo tblBookInfo) {
		TblBookSub tblBookSub = new TblBookSub();
		tblBookSub.setReaderId(bookPurchasePayload.getReaderDto().getReaderId());
		tblBookSub.setReaderName(bookPurchasePayload.getReaderDto().getName());
		tblBookSub.setReaderEmail(bookPurchasePayload.getReaderDto().getEmailId());
		tblBookSub.setParentTblBookInfo(tblBookInfo);
		tblBookSubRepository.saveAndFlush(tblBookSub);
		return tblBookInfo;
	}

	@Override
	public BookPayload getSubscribedBooks(Long readerId) {
		List<BookDto> bookDtoList=tblBookSubRepository.findByReaderId(readerId).stream()
		.map(TblBookSub::getParentTblBookInfo)
		.filter(IS_ACTIVE_BOOK)
		.map(TBLBOOKINFO_TO_BOOKDTO)
		.collect(Collectors.toList());
		
		return setBookPayload(bookDtoList);
	}

	@Override
	public BookPayload readBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {

		List<BookDto> bookDtoList = new ArrayList<>();
		if (IS_VALID_BOOK_PURCHASE_PAYLOAD.test(bookPurchasePayload)) {
			Optional<TblBookInfo> optionalTblBookInfo = tblBookInfoRepository.findById(bookPurchasePayload.getBookId());
			if (optionalTblBookInfo.isPresent() && IS_ACTIVE_BOOK.test(optionalTblBookInfo.get())) {
				if (IS_VALID_PURCHASE.test(bookPurchasePayload, optionalTblBookInfo.get())) {
					retrieveBookContent(bookDtoList, optionalTblBookInfo.get());
				} else {
					throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD,INVALID_PAYLOAD);
				}
			} else {
				throw new DigitalBooksException(STATUS_CODE_BOOK_BLOCKED_OR_DOESNT_EXIST,BOOK_BLOCKED_OR_DOESNT_EXIST);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD,INVALID_PAYLOAD);
		}
		return setBookPayload(bookDtoList);
	}

	private void retrieveBookContent(List<BookDto> bookDtoList, TblBookInfo tblBookInfo) {
		BookDto bookDto = TBLBOOKINFO_TO_BOOKDTO.apply(tblBookInfo);
		if (IS_NOT_NULL.test(tblBookInfo.getChildTblBookContent()))
			bookDto.setContent(tblBookInfo.getChildTblBookContent().getContent());
		bookDtoList.add(bookDto);
	}

	@Override
	public BookPayload createBook(BookPayload bookPayload) throws DigitalBooksException {
		if (IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITH_CONTENT.test(bookPayload)) {
			BookDto bookDto = bookPayload.getBookDtoList().get(0);
			TblBookInfo tblBookInfo = BOOKDTO_TO_TBLBOOKINFO.apply(bookDto);
			TblBookContent tblBookContent = new TblBookContent();
			tblBookContent.setParentTblBookInfo(tblBookInfo);
			tblBookContent.setContent(bookDto.getContent());
			tblBookInfo.setChildTblBookContent(tblBookContent);
			tblBookInfoRepository.saveAndFlush(tblBookInfo);
			bookDto.setBookId(tblBookInfo.getBookId());
			bookDto.setPublishedDate(tblBookInfo.getPublishedDate());
		}else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD,INVALID_PAYLOAD);
		}
		return bookPayload;
	}

	@Override
	public BookPayload editBook(BookPayload bookPayload) throws DigitalBooksException {

		if (IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITH_CONTENT.test(bookPayload)) {
			BookDto bookDto = bookPayload.getBookDtoList().get(0);
			Optional<TblBookInfo> optionalTblBookInfo = tblBookInfoRepository.findById(bookDto.getBookId());
			if (optionalTblBookInfo.isPresent() && IS_VALID_AUTHOR.test(bookDto, optionalTblBookInfo.get())) {
				TblBookInfo tblBookInfo=optionalTblBookInfo.get();
				Boolean isActiveStatusChanged = updateBookDetails(bookDto, tblBookInfo);
				if (isActiveStatusChanged.booleanValue()) {
					notifyReader(tblBookInfo);
				}
			} else {
				throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD,INVALID_PAYLOAD);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD,INVALID_PAYLOAD);
		}

		return bookPayload;
	}

	private Boolean updateBookDetails(BookDto bookDto, TblBookInfo tblBookInfo) {
		Boolean isActiveStatusChanged = !tblBookInfo.getActive().equals(bookDto.getActive());
		tblBookInfo.setActive(bookDto.getActive());
		tblBookInfo.setCategory(bookDto.getCategory());
		tblBookInfo.setLogo(bookDto.getLogo());
		tblBookInfo.setPrice(bookDto.getPrice());
		tblBookInfo.setPublishedDate(LocalDate.now());
		tblBookInfo.setPublisher(bookDto.getPublisher());
		tblBookInfo.setTitle(bookDto.getTitle());
		TblBookContent tblBookContent = tblBookInfo.getChildTblBookContent();
		if (IS_NOT_NULL.test(tblBookContent)) {
			tblBookContent.setContent(bookDto.getContent());
		}
		tblBookInfoRepository.saveAndFlush(tblBookInfo);
		return isActiveStatusChanged;
	}

	private void notifyReader(TblBookInfo tblBookInfo) {
		String notificationMessage = new StringBuilder().append(tblBookInfo.getAuthor()).append(" has ")
				.append((IS_ACTIVE_BOOK.test(tblBookInfo)) ? "UNBLOCKED" : "BLOCKED")
				.append(" your subscribed book: ").append(tblBookInfo.getTitle()).append(".").toString();
		NotificationPayload notificationPayload = new NotificationPayload();
		notificationPayload.setBookId(tblBookInfo.getBookId());
		notificationPayload.setMessage(notificationMessage);
		kafkaTemplate.send(READER_NOTIFICATION_TOPIC, notificationPayload);
	}
	
	@Override
	public BookPayload unsubscribeBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {
		List<BookDto> bookDtoList = new ArrayList<>();
		if (IS_VALID_BOOK_PURCHASE_PAYLOAD.test(bookPurchasePayload)) {
			Optional<TblBookSub> tblBookSubOptional = findTblBookSubByReaderIdAndBookId(bookPurchasePayload);
			if (tblBookSubOptional.isPresent()) {
				bookDtoList.add(TBLBOOKINFO_TO_BOOKDTO.apply(tblBookSubOptional.get().getParentTblBookInfo()));
				tblBookSubRepository.delete(tblBookSubOptional.get());
			} else {
				throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD,INVALID_PAYLOAD);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD,INVALID_PAYLOAD);
		}
		return setBookPayload(bookDtoList);
	}

	private Optional<TblBookSub> findTblBookSubByReaderIdAndBookId(BookPurchasePayload bookPurchasePayload) {
		return tblBookSubRepository
				.findByReaderId(bookPurchasePayload.getReaderDto().getReaderId()).stream()
				.filter(tblBookSub -> tblBookSub.getParentTblBookInfo().getBookId()
						.equals(bookPurchasePayload.getBookId()))
				.findFirst();
	}
	
	private BookPayload setBookPayload(List<BookDto> bookDtoList) {
		BookPayload bookPayload=new BookPayload();
		bookPayload.setBookDtoList(bookDtoList);
		return bookPayload;
	}


}
