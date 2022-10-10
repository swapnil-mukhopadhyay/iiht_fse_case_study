import { BookDto } from './book.dto';
import { ReaderDto } from './reader.dto';

export interface PaymentInvoicePayload{
    paymentId:number,
    paymentDateTime:string,
    bookDto:BookDto
    readerDto:ReaderDto
}