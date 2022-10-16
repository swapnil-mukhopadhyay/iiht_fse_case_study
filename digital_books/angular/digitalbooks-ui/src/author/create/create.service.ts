import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthorPayload } from 'src/models/author.payload';
import { authorurl } from 'src/urls/author.url';

@Injectable({
  providedIn: 'root'
})
export class CreateService {

  private author_url = authorurl.url;
  private create_url:string=this.author_url+"/books"
  constructor(private http: HttpClient) { }

  createBook(authorPayload: AuthorPayload, authorToken: string) {
    const headers = new HttpHeaders()
      .set('content-type', 'application/json')
      .set('Access-Control-Allow-Origin', '*')
      .set('Authorization', 'Bearer ' + authorToken);
    return this.http.post(this.create_url, authorPayload, {'headers': headers});
  }
}
