import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { readerurl } from 'src/urls/reader.url';

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  private reader_url = readerurl.url;
  private reader_url_prefix = this.reader_url + "/";

  constructor(private http: HttpClient) { }

  getNotifications(emailId: string) {
    var url = this.reader_url_prefix + emailId + "/notification";
    console.log("Constructed Notifications URL : " + url)
    return this.http.get(url);
  }
}
