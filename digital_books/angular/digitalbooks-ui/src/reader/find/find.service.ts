import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { readerurl } from 'src/urls/reader.url';

@Injectable({
  providedIn: 'root'
})
export class FindService {

  private reader_url = "http://" + readerurl.host + ":" + readerurl.port + readerurl.prefix;
  private find_url: string = this.reader_url+"/";

  constructor(private http: HttpClient) { }

  findBookByPaymentId(emailId:string,paymentId:number){
    var url = this.find_url+emailId+"/books/find"
    console.log("Constructed Find By Payment URL : " + url)
    return this.http.post(url,paymentId);
  }
}
