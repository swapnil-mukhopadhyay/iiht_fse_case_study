import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { readerurl } from 'src/urls/reader.url';

@Injectable({
  providedIn: 'root'
})
export class ReadService {

  private reader_url = "http://" + readerurl.host + ":" + readerurl.port + readerurl.prefix;
  private reader_url_prefix: string = this.reader_url+"/";

  constructor(private http:HttpClient) { }

  readBook(url:string){
    console.log("constructed Read URL : " + this.reader_url_prefix+url)
    return this.http.get(this.reader_url_prefix+url);
  }

}
