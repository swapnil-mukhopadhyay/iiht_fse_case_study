import { ReaderDto } from './reader.dto';
import { BookDto } from './book.dto';

export interface ReaderPayload{
    readerDto:ReaderDto,
    bookDtoList:Array<BookDto>,
    notifications:Array<string>
}