import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BookPurchasePayload } from 'src/models/book.purchase.payload';

@Injectable({
  providedIn: 'root'
})
export class SubscribeService {

  private buy_books_url: string = "http://localhost:8888/api/v1/digitalbooks/readers/books/buy";

  constructor(private http:HttpClient) { }

  buyBook(bookPurchasePayload:BookPurchasePayload){
    console.log("constructed Buy URL : " + this.buy_books_url)
    return this.http.post(this.buy_books_url,bookPurchasePayload);
  }
}
