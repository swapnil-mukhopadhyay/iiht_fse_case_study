import { BookDto } from './book.dto';

export interface PaymentInvoicePayload{
    paymentId:number,
    paymentDateTime:string,
    bookDto:BookDto
}