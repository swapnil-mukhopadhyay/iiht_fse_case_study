import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  private reader_url_prefix: string = "http://localhost:8888/api/v1/digitalbooks/readers/";

  constructor(private http:HttpClient) { }

  getNotifications(emailId:string){
    var url = this.reader_url_prefix + emailId + "/notification";
    console.log("Constructed Notifications URL : " + url)
    return this.http.get(url);
  }
}
