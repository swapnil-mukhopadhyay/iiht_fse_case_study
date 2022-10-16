import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { readerurl } from 'src/urls/reader.url';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionsService {

  private reader_url = readerurl.url;
  private reader_url_prefix: string = this.reader_url+"/";

  constructor(private http: HttpClient) { }

  getSubscriptions(emailId: string) {
    var url = this.reader_url_prefix + emailId + "/books";
    console.log("Constructed Subscriptions URL : " + url)
    return this.http.get(url);
  }
}
