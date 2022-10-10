import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReadService {

  private reader_url_prefix: string = "http://localhost:8888/api/v1/digitalbooks/readers/";

  constructor(private http:HttpClient) { }

  readBook(url:string){
    console.log("constructed Read URL : " + this.reader_url_prefix+url)
    return this.http.get(this.reader_url_prefix+url);
  }

}
