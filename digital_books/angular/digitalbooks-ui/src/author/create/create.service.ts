import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthorPayload } from 'src/models/author.payload';

@Injectable({
  providedIn: 'root'
})
export class CreateService {

  private create_url: string = "http://localhost:8888/api/v1/digitalbooks/author/books";
  constructor(private http: HttpClient) { }

  createBook(authorPayload: AuthorPayload, authorToken: string) {
    const headers = new HttpHeaders()
      .set('content-type', 'application/json')
      .set('Access-Control-Allow-Origin', '*')
      .set('Authorization', 'Bearer ' + authorToken);
    console.log("Created Create URL : ", this.create_url)
    return this.http.post(this.create_url, authorPayload, {'headers': headers});
  }
}
