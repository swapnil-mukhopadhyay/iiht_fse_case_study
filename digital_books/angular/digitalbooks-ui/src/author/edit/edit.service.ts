import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthorPayload } from 'src/models/author.payload';
import { authorurl } from 'src/urls/author.url';

@Injectable({
  providedIn: 'root'
})
export class EditService {

  private author_url = authorurl.url;
  private edit_url: string = this.author_url + "/books/";
  constructor(private http: HttpClient) { }

  editBook(authorPayload: AuthorPayload, authorToken: string, bookId: number) {
    const headers = new HttpHeaders()
      .set('content-type', 'application/json')
      .set('Access-Control-Allow-Origin', '*')
      .set('Authorization', 'Bearer ' + authorToken);
    var url = this.edit_url + bookId;
    console.log("Created Create URL : ", this.edit_url)
    return this.http.put(url, authorPayload, { 'headers': headers });
  }
}
