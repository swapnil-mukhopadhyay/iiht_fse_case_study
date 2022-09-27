package com.digitalbooks.author.services;

import static com.digitalbooks.author.constants.DigitalBooksExceptionConstants.BOOK_BLOCKED_OR_DOESNT_EXIST;
import static com.digitalbooks.author.constants.DigitalBooksExceptionConstants.INVALID_AUTHOR_ID;
import static com.digitalbooks.author.constants.DigitalBooksExceptionConstants.INVALID_PAYLOAD;
import static com.digitalbooks.author.constants.DigitalBooksExceptionConstants.STATUS_CODE_BOOK_BLOCKED_OR_DOESNT_EXIST;
import static com.digitalbooks.author.constants.DigitalBooksExceptionConstants.STATUS_CODE_INVALID_AUTHOR_ID;
import static com.digitalbooks.author.constants.DigitalBooksExceptionConstants.STATUS_CODE_INVALID_PAYLOAD;
import static com.digitalbooks.author.constants.DigitalBooksExceptionConstants.STATUS_CODE_SOMETHING_WENT_WRONG;
import static com.digitalbooks.author.predicates.AuthorPredicates.IS_VALID_AUTHOR_PAYLOAD;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.author.clients.BookClient;
import com.digitalbooks.author.constants.DigitalBooksExceptionConstants;
import com.digitalbooks.author.entities.db.TblAuthorBook;
import com.digitalbooks.author.entities.db.TblAuthorCredential;
import com.digitalbooks.author.entities.db.TblAuthorInfo;
import com.digitalbooks.author.entities.payload.AuthorPayload;
import com.digitalbooks.author.entities.payload.BookPayload;
import com.digitalbooks.author.exceptions.DigitalBooksException;
import com.digitalbooks.author.interfaces.AuthorIf;
import com.digitalbooks.author.repositories.TblAuthorBookRepository;
import com.digitalbooks.author.repositories.TblAuthorCredentialRepository;
import com.digitalbooks.author.repositories.TblAuthorInfoRepository;
import com.digitalbooks.author.security.config.PasswordEncoderConfig;
import com.digitalbooks.author.security.payload.CredentialPayload;

import feign.FeignException;

@Service
@Transactional
public class AuthorService implements AuthorIf {

	@Autowired
	private BookClient bookClient;

	@Autowired
	private TblAuthorInfoRepository tblAuthorInfoRepository;

	@Autowired
	private TblAuthorCredentialRepository tblAuthorCredentialRepository;

	@Autowired
	private TblAuthorBookRepository tblAuthorBookRepository;

	@Autowired
	private PasswordEncoderConfig passwordEncoderConfig;

	@Override
	public AuthorPayload signup(CredentialPayload credentialPayload) throws DigitalBooksException {

		AuthorPayload authorPayload = new AuthorPayload();
		Optional<TblAuthorCredential> tblAuthorCredentialOptional = tblAuthorCredentialRepository
				.findByUsername(credentialPayload.getUsername());

		if (tblAuthorCredentialOptional.isEmpty()) {
			TblAuthorCredential tblAuthorCredential = new TblAuthorCredential();
			tblAuthorCredential.setUsername(credentialPayload.getUsername());
			tblAuthorCredential
					.setPassword(passwordEncoderConfig.passwordEncoder().encode(credentialPayload.getPassword()));
			tblAuthorCredentialRepository.saveAndFlush(tblAuthorCredential);
			TblAuthorInfo tblAuthorInfo = new TblAuthorInfo();
			tblAuthorInfo.setName(credentialPayload.getUsername());
			authorPayload.setAuthorId(tblAuthorInfoRepository.saveAndFlush(tblAuthorInfo).getAuthorId());
			authorPayload.setName(tblAuthorInfo.getName());
		} else {
			throw new DigitalBooksException(DigitalBooksExceptionConstants.STATUS_CODE_USERNAME_TAKEN,
					DigitalBooksExceptionConstants.USERNAME_TAKEN);
		}
		return authorPayload;
	}

	@Override
	public AuthorPayload createBook(AuthorPayload authorPayload) throws DigitalBooksException {
		if (IS_VALID_AUTHOR_PAYLOAD.test(authorPayload)) {
			Optional<TblAuthorInfo> tblAuthorInfoOptional = tblAuthorInfoRepository
					.findById(authorPayload.getAuthorId());
			if (tblAuthorInfoOptional.isPresent()) {
				try {
					createNewBook(authorPayload, tblAuthorInfoOptional);
				} catch (DigitalBooksException digitalBooksException) {
					rethrowDigitalBooksException(digitalBooksException);
				}
			} else {
				throw new DigitalBooksException(STATUS_CODE_INVALID_AUTHOR_ID, INVALID_AUTHOR_ID);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD, INVALID_PAYLOAD);
		}
		return authorPayload;
	}

	private void createNewBook(AuthorPayload authorPayload, Optional<TblAuthorInfo> tblAuthorInfoOptional)
			throws DigitalBooksException {
		TblAuthorInfo tblAuthorInfo = tblAuthorInfoOptional.get();
		BookPayload bookPayload = new BookPayload();
		bookPayload.setBookDtoList(authorPayload.getBookDtoList());
		try {
			bookPayload = bookClient.createBook(bookPayload);
		} catch (FeignException feignException) {
			throw new DigitalBooksException(STATUS_CODE_SOMETHING_WENT_WRONG, feignException.getMessage());
		}
		if (null != bookPayload.getBookDtoList().get(0).getBookId()) {
			TblAuthorBook tblAuthorBook = new TblAuthorBook();
			tblAuthorBook.setBookId(bookPayload.getBookDtoList().get(0).getBookId());
			tblAuthorBook.setParentTblAuthorInfo(tblAuthorInfo);
			tblAuthorBookRepository.saveAndFlush(tblAuthorBook);
			authorPayload.setBookDtoList(bookPayload.getBookDtoList());
		} else {
			throw new DigitalBooksException(DigitalBooksExceptionConstants.STATUS_CODE_SOMETHING_WENT_WRONG,
					DigitalBooksExceptionConstants.SOMETHING_WENT_WRONG);
		}
	}

	private void rethrowDigitalBooksException(DigitalBooksException digitalBooksException)
			throws DigitalBooksException {
		throw new DigitalBooksException(digitalBooksException.getStatusCode(), digitalBooksException.getMessage());
	}

	@Override
	public AuthorPayload editBook(AuthorPayload authorPayload, Long authorId, Long bookId)
			throws DigitalBooksException {
		if (IS_VALID_AUTHOR_PAYLOAD.test(authorPayload)) {
			Optional<TblAuthorInfo> tblAuthorInfoOptional = tblAuthorInfoRepository.findById(authorId);
			if (tblAuthorInfoOptional.isPresent()) {
				Optional<TblAuthorBook> tblAuthorBookOptional = tblAuthorBookRepository.findByBookId(bookId);
				try {
					editBookIfValid(authorPayload, authorId, tblAuthorBookOptional);
				} catch (DigitalBooksException digitalBooksException) {
					rethrowDigitalBooksException(digitalBooksException);
				}
			} else {
				throw new DigitalBooksException(STATUS_CODE_INVALID_AUTHOR_ID, INVALID_AUTHOR_ID);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_INVALID_PAYLOAD, INVALID_PAYLOAD);
		}
		return authorPayload;
	}

	private void editBookIfValid(AuthorPayload authorPayload, Long authorId,
			Optional<TblAuthorBook> tblAuthorBookOptional) throws DigitalBooksException {
		if (tblAuthorBookOptional.isPresent()) {
			TblAuthorBook tblAuthorBook = tblAuthorBookOptional.get();
			if (authorId.equals(tblAuthorBook.getParentTblAuthorInfo().getAuthorId())) {
				BookPayload bookPayload = new BookPayload();
				bookPayload.setBookDtoList(authorPayload.getBookDtoList());
				try {
					bookClient.editBook(bookPayload);
				} catch (FeignException feignException) {
					throw new DigitalBooksException(STATUS_CODE_SOMETHING_WENT_WRONG, feignException.getMessage());
				}
			} else {
				throw new DigitalBooksException(STATUS_CODE_INVALID_AUTHOR_ID, INVALID_AUTHOR_ID);
			}
		} else {
			throw new DigitalBooksException(STATUS_CODE_BOOK_BLOCKED_OR_DOESNT_EXIST, BOOK_BLOCKED_OR_DOESNT_EXIST);
		}
	}

}
