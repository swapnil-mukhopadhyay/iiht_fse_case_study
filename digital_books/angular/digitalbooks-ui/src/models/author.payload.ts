import { BookDto } from './book.dto';

export interface AuthorPayload{
    authorId?:number,
    name:string,
    bookDtoList:Array<BookDto>
}