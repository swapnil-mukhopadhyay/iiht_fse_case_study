import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionsService {

  private reader_url_prefix: string = "http://localhost:8888/api/v1/digitalbooks/readers/";

  constructor(private http: HttpClient) { }

  getSubscriptions(emailId: string) {
    var url = this.reader_url_prefix;
    url = url + emailId + "/books";
    console.log("Constructed Subscriptions URL : " + url)
    return this.http.get(url);
  }
}
