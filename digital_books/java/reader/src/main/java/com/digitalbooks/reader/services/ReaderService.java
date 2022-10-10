package com.digitalbooks.reader.services;

import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.ALREADY_SUBSCRIBED;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.ERROR_RETRIEVING_BOOK_CONTENT;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.INVALID_PAYLOAD;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.INVALID_PAYMENT_ID;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.NOT_SUBSCRIBED;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.SOMETHING_WENT_WRONG;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.STATUS_CODE_ALREADY_SUBSCRIBED;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.STATUS_CODE_ERROR_RETRIEVING_BOOK_CONTENT;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.STATUS_CODE_INVALID_PAYLOAD;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.STATUS_CODE_INVALID_PAYMENT_ID;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.STATUS_CODE_NOT_SUBSCRIBED;
import static com.digitalbooks.reader.constants.DigitalBooksExceptionConstants.STATUS_CODE_SOMETHING_WENT_WRONG;
import static com.digitalbooks.reader.functions.ReaderFunctions.TBLREADERINFO_TO_READERDTO;
import static com.digitalbooks.reader.predicates.ReaderPredicates.EMAIL_ID_MATCHES;
import static com.digitalbooks.reader.predicates.ReaderPredicates.IS_VALID_ACCOUNT_INFO;
import static com.digitalbooks.reader.predicates.ReaderPredicates.IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITHOUT_CONTENT;
import static com.digitalbooks.reader.predicates.ReaderPredicates.IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITH_CONTENT;
import static com.digitalbooks.reader.predicates.ReaderPredicates.IS_VALID_BOOK_PURCHASE_PAYLOAD;
import static com.digitalbooks.reader.predicates.ReaderPredicates.IS_VALID_FIRST_TIME_BOOK_PURCHASE_PAYLOAD;
import static com.digitalbooks.reader.predicates.ReaderPredicates.READER_ALREADY_SUBSCRIBED;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.common.models.NotificationPayload;
import com.digitalbooks.reader.clients.BookClient;
import com.digitalbooks.reader.entities.db.TblReaderInfo;
import com.digitalbooks.reader.entities.db.TblReaderNotification;
import com.digitalbooks.reader.entities.db.TblReaderPayment;
import com.digitalbooks.reader.entities.dto.ReaderDto;
import com.digitalbooks.reader.entities.payload.BookPayload;
import com.digitalbooks.reader.entities.payload.BookPurchasePayload;
import com.digitalbooks.reader.entities.payload.PaymentInvoicePayload;
import com.digitalbooks.reader.entities.payload.ReaderPayload;
import com.digitalbooks.reader.exceptions.DigitalBooksException;
import com.digitalbooks.reader.interfaces.ReaderIf;
import com.digitalbooks.reader.predicates.ReaderPredicates;
import com.digitalbooks.reader.repositories.TblReaderInfoRepository;
import com.digitalbooks.reader.repositories.TblReaderNotificationRepository;
import com.digitalbooks.reader.repositories.TblReaderPaymentRepository;

import feign.FeignException;

@Service
@Transactional
public class ReaderService implements ReaderIf {

	@Autowired
	private BookClient bookClient;

	@Autowired
	private TblReaderInfoRepository tblReaderInfoRepository;

	@Autowired
	private TblReaderPaymentRepository tblReaderPaymentRepository;

	@Autowired
	private TblReaderNotificationRepository tblReaderNotificationRepository;

	@Override
	public PaymentInvoicePayload buyBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {

		PaymentInvoicePayload paymentInvoicePayload = new PaymentInvoicePayload();

		if (IS_VALID_FIRST_TIME_BOOK_PURCHASE_PAYLOAD.test(bookPurchasePayload)) {

			Optional<TblReaderInfo> tblReaderInfoOptional = tblReaderInfoRepository
					.findByEmailId(bookPurchasePayload.getReaderDto().getEmailId());

			TblReaderInfo tblReaderInfo = (tblReaderInfoOptional.isPresent()) ? tblReaderInfoOptional.get()
					: new TblReaderInfo();
			List<TblReaderPayment> tblReaderPaymentList = new ArrayList<>();
			try {
				tblReaderPaymentList = populateTblReaderPaymentList(bookPurchasePayload, tblReaderInfoOptional,
						tblReaderInfo);
			} catch (DigitalBooksException digitalBooksException) {
				rethrowDigitalBooksException(digitalBooksException);
			}

			tblReaderInfo.setTblReaderPaymentList(tblReaderPaymentList);
			tblReaderInfoRepository.saveAndFlush(tblReaderInfo);
			bookPurchasePayload.getReaderDto().setReaderId(tblReaderInfo.getReaderId());
			BookPayload bookPayload = new BookPayload();
			try {
				bookPayload = bookClient.buyBook(bookPurchasePayload);
			} catch (FeignException feignException) {
				throw new DigitalBooksException(STATUS_CODE_SOMETHING_WENT_WRONG, feignException.getMessage());
			}
			if (IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITHOUT_CONTENT.test(bookPayload)) {
				setPaymentInvoice(bookPurchasePayload, paymentInvoicePayload, tblReaderInfo, bookPayload);
			} else {
				throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD, INVALID_PAYLOAD);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD, INVALID_PAYLOAD);
		}

		return paymentInvoicePayload;
	}

	private void setPaymentInvoice(BookPurchasePayload bookPurchasePayload, PaymentInvoicePayload paymentInvoicePayload,
			TblReaderInfo tblReaderInfo, BookPayload bookPayload) {
		Optional<TblReaderPayment> tblReaderPaymentOptional = tblReaderInfo.getTblReaderPaymentList().stream()
				.filter(tblReaderPayment -> !tblReaderPayment.getIsRefunded()
						&& bookPurchasePayload.getBookId().equals(tblReaderPayment.getBookId()))
				.findFirst();
		if (tblReaderPaymentOptional.isPresent()) {
			setReaderDtoInPaymentInvoice(paymentInvoicePayload, tblReaderPaymentOptional);
			paymentInvoicePayload.setBookDto(bookPayload.getBookDtoList().get(0));
			paymentInvoicePayload.setPaymentDateTime(tblReaderPaymentOptional.get().getPaymentDateTime());
			paymentInvoicePayload.setPaymentId(tblReaderPaymentOptional.get().getPaymentId());
		}
	}

	private void setReaderDtoInPaymentInvoice(PaymentInvoicePayload paymentInvoicePayload,
			Optional<TblReaderPayment> tblReaderPaymentOptional) {
		ReaderDto readerDto=new ReaderDto();
		readerDto.setName(tblReaderPaymentOptional.get().getParentTblReaderInfo().getName());
		readerDto.setEmailId(tblReaderPaymentOptional.get().getParentTblReaderInfo().getEmailId());
		readerDto.setReaderId(tblReaderPaymentOptional.get().getParentTblReaderInfo().getReaderId());
		paymentInvoicePayload.setReaderDto(readerDto);
	}

	private void rethrowDigitalBooksException(DigitalBooksException digitalBooksException)
			throws DigitalBooksException {
		throw new DigitalBooksException(digitalBooksException.getStatusCode(), digitalBooksException.getMessage());
	}

	private List<TblReaderPayment> populateTblReaderPaymentList(BookPurchasePayload bookPurchasePayload,
			Optional<TblReaderInfo> tblReaderInfoOptional, TblReaderInfo tblReaderInfo) throws DigitalBooksException {
		List<TblReaderPayment> tblReaderPaymentList = new ArrayList<>();
		if (tblReaderInfoOptional.isPresent()) {
			if (IS_VALID_ACCOUNT_INFO.test(bookPurchasePayload, tblReaderInfo)) {
				try {
					tblReaderPaymentList = setReaderPaymentListForExistingReader(bookPurchasePayload, tblReaderInfo);
				} catch (DigitalBooksException digitalBooksException) {
					rethrowDigitalBooksException(digitalBooksException);
				}
			} else {
				throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD, INVALID_PAYLOAD);
			}
		} else {
			tblReaderPaymentList = setReaderPaymentListForNewReader(bookPurchasePayload, tblReaderInfo);
		}
		return tblReaderPaymentList;
	}

	private List<TblReaderPayment> setReaderPaymentListForNewReader(BookPurchasePayload bookPurchasePayload,
			TblReaderInfo tblReaderInfo) {
		List<TblReaderPayment> tblReaderPaymentList = new ArrayList<>();
		tblReaderInfo.setName(bookPurchasePayload.getReaderDto().getName());
		tblReaderInfo.setEmailId(bookPurchasePayload.getReaderDto().getEmailId());
		TblReaderPayment tblReaderPayment = setNewTblReaderPayment(bookPurchasePayload, tblReaderInfo);
		tblReaderPaymentList.add(tblReaderPayment);
		return tblReaderPaymentList;
	}

	private List<TblReaderPayment> setReaderPaymentListForExistingReader(BookPurchasePayload bookPurchasePayload,
			TblReaderInfo tblReaderInfo) throws DigitalBooksException {
		List<TblReaderPayment> tblReaderPaymentList = tblReaderInfo.getTblReaderPaymentList();
		if (READER_ALREADY_SUBSCRIBED.test(tblReaderPaymentList, bookPurchasePayload.getBookId())) {
			throw new DigitalBooksException(STATUS_CODE_ALREADY_SUBSCRIBED, ALREADY_SUBSCRIBED);
		} else {
			TblReaderPayment tblReaderPayment = setNewTblReaderPayment(bookPurchasePayload, tblReaderInfo);
			tblReaderPaymentList.add(tblReaderPayment);
		}
		return tblReaderPaymentList;
	}

	private TblReaderPayment setNewTblReaderPayment(BookPurchasePayload bookPurchasePayload,
			TblReaderInfo tblReaderInfo) {
		TblReaderPayment tblReaderPayment = new TblReaderPayment();
		tblReaderPayment.setBookId(bookPurchasePayload.getBookId());
		tblReaderPayment.setParentTblReaderInfo(tblReaderInfo);
		tblReaderPayment.setPaymentDateTime(LocalDateTime.now());
		tblReaderPayment.setIsRefunded(false);
		return tblReaderPayment;
	}

	@Override
	public ReaderPayload getSubscribedBooks(String emailId) throws DigitalBooksException {
		ReaderPayload readerPayload = new ReaderPayload();
		Optional<TblReaderInfo> tblReaderInfoOptional = tblReaderInfoRepository.findByEmailId(emailId);
		if (tblReaderInfoOptional.isPresent()) {
			TblReaderInfo tblReaderInfo = tblReaderInfoOptional.get();
			try {
				readerPayload
						.setBookDtoList(bookClient.getSubscribedBooks(tblReaderInfo.getReaderId()).getBookDtoList());
			} catch (FeignException feignException) {
				throw new DigitalBooksException(STATUS_CODE_SOMETHING_WENT_WRONG, feignException.getMessage());
			}
			ReaderDto readerDto = new ReaderDto();
			readerDto.setReaderId(tblReaderInfo.getReaderId());
			readerDto.setName(tblReaderInfo.getName());
			readerDto.setEmailId(tblReaderInfo.getEmailId());
			readerPayload.setReaderDto(readerDto);
		} else {
			throw new DigitalBooksException(STATUS_CODE_NOT_SUBSCRIBED, NOT_SUBSCRIBED);
		}
		return readerPayload;
	}

	@Override
	public ReaderPayload readBook(String emailId, Long bookId) throws DigitalBooksException {

		ReaderPayload readerPayload = new ReaderPayload();
		Optional<TblReaderInfo> tblReaderInfoOptional = tblReaderInfoRepository.findByEmailId(emailId);
		if (tblReaderInfoOptional.isPresent()) {
			TblReaderInfo tblReaderInfo = tblReaderInfoOptional.get();
			if (READER_ALREADY_SUBSCRIBED.test(tblReaderInfo.getTblReaderPaymentList(), bookId)) {
				BookPurchasePayload bookPurchasePayload = new BookPurchasePayload();
				bookPurchasePayload.setBookId(bookId);
				ReaderDto readerDto = TBLREADERINFO_TO_READERDTO.apply(tblReaderInfo);
				readerPayload.setReaderDto(readerDto);
				bookPurchasePayload.setReaderDto(readerDto);
				BookPayload bookPayload = new BookPayload();
				try {
					bookPayload = bookClient.readBook(bookPurchasePayload);
				} catch (FeignException feignException) {
					throw new DigitalBooksException(STATUS_CODE_SOMETHING_WENT_WRONG, feignException.getMessage());
				}
				if (IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITH_CONTENT.test(bookPayload)) {
					readerPayload.setBookDtoList(bookPayload.getBookDtoList());
				} else {
					throw new DigitalBooksException(STATUS_CODE_ERROR_RETRIEVING_BOOK_CONTENT,
							ERROR_RETRIEVING_BOOK_CONTENT);
				}
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_NOT_SUBSCRIBED, NOT_SUBSCRIBED);
		}
		return readerPayload;
	}

	@Override
	public PaymentInvoicePayload findBookByPaymentId(String emailId, Long paymentId) throws DigitalBooksException {
		PaymentInvoicePayload paymentInvoicePayload=new PaymentInvoicePayload();
		Optional<TblReaderPayment> tblReaderPaymentOptional = tblReaderPaymentRepository.findById(paymentId);
		if (tblReaderPaymentOptional.isPresent()) {
			TblReaderInfo tblReaderInfo = tblReaderPaymentOptional.get().getParentTblReaderInfo();
			if (EMAIL_ID_MATCHES.test(tblReaderInfo, emailId)) {
				paymentInvoicePayload.setPaymentId(tblReaderPaymentOptional.get().getPaymentId());
				paymentInvoicePayload.setPaymentDateTime(tblReaderPaymentOptional.get().getPaymentDateTime());
				paymentInvoicePayload.setReaderDto(TBLREADERINFO_TO_READERDTO.apply(tblReaderInfo));
				BookPayload bookPayload = new BookPayload();
				try {
					bookPayload = bookClient.getSubscribedBooks(tblReaderInfo.getReaderId());
				} catch (FeignException feignException) {
					throw new DigitalBooksException(STATUS_CODE_SOMETHING_WENT_WRONG, feignException.getMessage());
				}
				if (IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITHOUT_CONTENT.test(bookPayload)) {
					paymentInvoicePayload.setBookDto(bookPayload.getBookDtoList().get(0));
				} else {
					throw new DigitalBooksException(STATUS_CODE_ERROR_RETRIEVING_BOOK_CONTENT,
							ERROR_RETRIEVING_BOOK_CONTENT);
				}
			} else {
				throw new DigitalBooksException(STATUS_CODE_INVALID_PAYMENT_ID, INVALID_PAYMENT_ID);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYMENT_ID, INVALID_PAYMENT_ID);
		}
		return paymentInvoicePayload;
	}

	@Override
	public ReaderPayload refundBook(BookPurchasePayload bookPurchasePayload) throws DigitalBooksException {
		ReaderPayload readerPayload = new ReaderPayload();

		if (IS_VALID_BOOK_PURCHASE_PAYLOAD.test(bookPurchasePayload)) {
			Optional<TblReaderInfo> tblReaderInfoOptional = tblReaderInfoRepository
					.findByEmailId(bookPurchasePayload.getReaderDto().getEmailId());
			if (tblReaderInfoOptional.isPresent()
					&& IS_VALID_ACCOUNT_INFO.test(bookPurchasePayload, tblReaderInfoOptional.get())) {
				TblReaderInfo tblReaderInfo = tblReaderInfoOptional.get();
				bookPurchasePayload.getReaderDto().setReaderId(tblReaderInfo.getReaderId());
				readerPayload.setReaderDto(TBLREADERINFO_TO_READERDTO.apply(tblReaderInfo));
				Optional<TblReaderPayment> tblReaderPaymentOptional = tblReaderInfo.getTblReaderPaymentList().stream()
						.filter(tblReaderPayment -> bookPurchasePayload.getBookId().equals(tblReaderPayment.getBookId())
								&& !tblReaderPayment.getIsRefunded())
						.findFirst();
				try {
					unsubscribeBookIfSubscribed(bookPurchasePayload, readerPayload, tblReaderInfo,
							tblReaderPaymentOptional);
				} catch (DigitalBooksException digitalBooksException) {
					rethrowDigitalBooksException(digitalBooksException);
				}
			} else {
				throw new DigitalBooksException(STATUS_CODE_NOT_SUBSCRIBED, NOT_SUBSCRIBED);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD, INVALID_PAYLOAD);
		}
		return readerPayload;
	}

	private void unsubscribeBookIfSubscribed(BookPurchasePayload bookPurchasePayload, ReaderPayload readerPayload,
			TblReaderInfo tblReaderInfo, Optional<TblReaderPayment> tblReaderPaymentOptional)
			throws DigitalBooksException {
		if (tblReaderPaymentOptional.isPresent()
				&& ReaderPredicates.IS_WITHIN_REFUND_DURATION.test(tblReaderPaymentOptional.get())) {
			TblReaderPayment tblReaderPayment = tblReaderPaymentOptional.get();
			BookPayload bookPayload = new BookPayload();
			try {
				bookPayload = bookClient.unsubscribeBook(bookPurchasePayload);
			} catch (FeignException feignException) {
				throw new DigitalBooksException(STATUS_CODE_SOMETHING_WENT_WRONG, feignException.getMessage());
			}

			if (IS_VALID_BOOK_PAYLOAD_WITH_ONE_BOOK_WITHOUT_CONTENT.test(bookPayload)) {
				tblReaderPayment.setIsRefunded(true);
				readerPayload.setBookDtoList(bookPayload.getBookDtoList());
				tblReaderInfoRepository.saveAndFlush(tblReaderInfo);
			} else {
				throw new DigitalBooksException(STATUS_CODE_SOMETHING_WENT_WRONG, SOMETHING_WENT_WRONG);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_NOT_SUBSCRIBED, NOT_SUBSCRIBED);
		}
	}

	@Override
	public void postNotification(NotificationPayload notificationPayload) {

		List<TblReaderNotification> tblReaderNotificationList = new ArrayList<>();
		List<TblReaderPayment> tblReaderPaymentList = tblReaderPaymentRepository
				.findByBookId(notificationPayload.getBookId());

		tblReaderPaymentList.stream().filter(tblReaderPayment -> !tblReaderPayment.getIsRefunded())
				.map(TblReaderPayment::getParentTblReaderInfo).forEach(tblReaderInfo -> {
					TblReaderNotification tblReaderNotification = new TblReaderNotification();
					tblReaderNotification.setParentTblReaderInfo(tblReaderInfo);
					tblReaderNotification.setMessage(notificationPayload.getMessage());
					tblReaderNotification.setNotificationDateTime(LocalDateTime.now());
					tblReaderNotificationList.add(tblReaderNotification);
				});
		tblReaderNotificationRepository.saveAllAndFlush(tblReaderNotificationList);
	}

	@Override
	public ReaderPayload getNotifications(String emailId) {
		ReaderPayload readerPayload = new ReaderPayload();
		Optional<TblReaderInfo> tblReaderInfoOptional = tblReaderInfoRepository.findByEmailId(emailId);
		if (tblReaderInfoOptional.isPresent()) {
			TblReaderInfo tblReaderInfo = tblReaderInfoOptional.get();
			readerPayload.setReaderDto(TBLREADERINFO_TO_READERDTO.apply(tblReaderInfo));
			readerPayload.setNotifications(tblReaderInfo.getTblReaderNotificationList().stream()
					.map(TblReaderNotification::getMessage).collect(Collectors.toList()));
		}
		return readerPayload;
	}

}
