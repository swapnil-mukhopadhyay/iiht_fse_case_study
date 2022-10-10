import { ReaderDto } from './reader.dto';

export interface  BookPurchasePayload{
    bookId:number,
    paymentId?:number,
    readerDto:ReaderDto
}