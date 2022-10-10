import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private author_prefix_url: string = "http://localhost:8888/api/v1/digitalbooks/author/";
  constructor(private http: HttpClient) { }

  getAllBooksForAuthor(authorName: string, authorToken: string) {
    const headers = new HttpHeaders()
      .set('content-type', 'application/json')
      .set('Access-Control-Allow-Origin', '*')
      .set('Authorization', 'Bearer ' + authorToken);
    var url = this.author_prefix_url + authorName + "/all"
    return this.http.get(url, { 'headers': headers })
  }
}
