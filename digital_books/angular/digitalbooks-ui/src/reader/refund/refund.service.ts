import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BookDto } from 'src/models/book.dto';
import { BookPurchasePayload } from 'src/models/book.purchase.payload';

@Injectable({
  providedIn: 'root'
})
export class RefundService {

  private reader_url_prefix: string = "http://localhost:8888/api/v1/digitalbooks/readers/";

  constructor(private http:HttpClient) { }

  refundBook(url:string, bookPurchasePayload:BookPurchasePayload){
    console.log("constructed Refund URL : " + this.reader_url_prefix+url)
    return this.http.post(this.reader_url_prefix+url,bookPurchasePayload);
  }

}
