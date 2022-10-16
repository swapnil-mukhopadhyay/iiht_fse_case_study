import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BookPurchasePayload } from 'src/models/book.purchase.payload';
import { readerurl } from 'src/urls/reader.url';

@Injectable({
  providedIn: 'root'
})
export class SubscribeService {

  private reader_url = "http://" + readerurl.host + ":" + readerurl.port + readerurl.prefix;
  private buy_books_url: string = this.reader_url + "/books/buy";

  constructor(private http: HttpClient) { }

  buyBook(bookPurchasePayload: BookPurchasePayload) {
    console.log("constructed Buy URL : " + this.buy_books_url)
    return this.http.post(this.buy_books_url, bookPurchasePayload);
  }
}
