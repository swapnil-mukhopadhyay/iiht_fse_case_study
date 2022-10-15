package com.digitalbooks.author;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.digitalbooks.author.entities.db.TblAuthorInfo;
import com.digitalbooks.author.entities.payload.AuthorPayload;
import com.digitalbooks.author.exceptions.DigitalBooksException;
import com.digitalbooks.author.repositories.TblAuthorCredentialRepository;
import com.digitalbooks.author.repositories.TblAuthorInfoRepository;
import com.digitalbooks.author.security.payload.CredentialPayload;
import com.digitalbooks.author.services.AuthorService;

@SpringBootTest
class AuthorApplicationTests {

	@Autowired
	private AuthorService authorService;

	@MockBean
	private TblAuthorCredentialRepository tblAuthorCredentialRepository;

	@MockBean
	private TblAuthorInfoRepository tblAuthorInfoRepository;

	@Test
	void testSignup() throws DigitalBooksException {
		when(tblAuthorCredentialRepository.findByUsername(anyString())).thenReturn(Optional.empty());
		when(tblAuthorInfoRepository.saveAndFlush(any(TblAuthorInfo.class)))
				.thenReturn(dummyTblAuthorInfoSupplier.get());
		AuthorPayload authorPayload = authorService.signup(dummyCredentialPayloadSupplier.get());
		assertEquals(1L, authorPayload.getAuthorId());
		assertEquals("author", authorPayload.getName());
	}

	private Supplier<TblAuthorInfo> dummyTblAuthorInfoSupplier = () -> {
		TblAuthorInfo tblAuthorInfo = new TblAuthorInfo();
		tblAuthorInfo.setAuthorId(1L);
		return tblAuthorInfo;
	};

	private Supplier<CredentialPayload> dummyCredentialPayloadSupplier = () -> {
		CredentialPayload credentialPayload = new CredentialPayload();
		credentialPayload.setUsername("author");
		credentialPayload.setPassword("author@123");
		return credentialPayload;
	};

}
