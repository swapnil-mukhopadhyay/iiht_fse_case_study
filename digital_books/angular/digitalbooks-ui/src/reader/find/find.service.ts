import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FindService {

  private reader_url_prefix: string = "http://localhost:8888/api/v1/digitalbooks/readers/";

  constructor(private http: HttpClient) { }

  findBookByPaymentId(emailId:string,paymentId:number){
    var url = this.reader_url_prefix+emailId+"/books/find"
    console.log("Constructed Find By Payment URL : " + url)
    return this.http.post(url,paymentId);
  }
}
