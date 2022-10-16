import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { authorurl } from 'src/urls/author.url';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private author_url = "http://" + authorurl.host + ":" + authorurl.port + authorurl.prefix
  private my_books_url = this.author_url + "/"

  constructor(private http: HttpClient) { }

  getAllBooksForAuthor(authorName: string, authorToken: string) {
    const headers = new HttpHeaders()
      .set('content-type', 'application/json')
      .set('Access-Control-Allow-Origin', '*')
      .set('Authorization', 'Bearer ' + authorToken);
    var url = this.my_books_url + authorName + "/all"
    return this.http.get(url, { 'headers': headers })
  }
}
