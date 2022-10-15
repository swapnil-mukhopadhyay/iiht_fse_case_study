package com.digitalbooks.reader.functions;

import java.util.function.Function;

import com.digitalbooks.reader.entities.db.TblReaderInfo;
import com.digitalbooks.reader.entities.dto.ReaderDto;

public abstract class ReaderFunctions {

	public static final Function<TblReaderInfo, ReaderDto> TBLREADERINFO_TO_READERDTO = tblReaderInfo -> {
		ReaderDto readerDto = new ReaderDto();
		readerDto.setReaderId(tblReaderInfo.getReaderId());
		readerDto.setName(tblReaderInfo.getName());
		readerDto.setEmailId(tblReaderInfo.getEmailId());
		return readerDto;
	};
	
	private ReaderFunctions() {

	}

}
