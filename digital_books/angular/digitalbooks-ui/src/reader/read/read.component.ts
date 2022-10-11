import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookDto } from 'src/models/book.dto';
import { ReaderPayload } from 'src/models/reader.payload';
import { ReadService } from './read.service';

@Component({
  selector: 'app-read',
  templateUrl: './read.component.html',
  styleUrls: ['./read.component.scss']
})
export class ReadComponent implements OnInit {

  readerPayload: ReaderPayload = {
    readerDto: {
      readerId: 0,
      name: '',
      emailId: ''
    },
    notifications: [],
    bookDtoList: []
  }

  bookDto: BookDto = {
    active: true,
    author: "",
    bookId: 0,
    authorId: 0,
    category: "",
    logo: "",
    price: 0,
    publishedDate: "",
    publisher: "",
    title: ""
  }

  constructor(private _readService: ReadService, private router: Router) { 
    if(!this.isReader()){
      this.router.navigate(["home"])
    }
  }

  readBook(url: string) {
    this._readService.readBook(url).subscribe({
      next: (res: any) => {
        if (!!res.statusCode) {
          alert(res.message)
        } else {
          this.readerPayload = res;
          this.bookDto = this.readerPayload.bookDtoList[0]
          console.log(this.readerPayload);
        }
      },
      error: (err: any) => {
        console.log(err)
      }
    })
  }

  isReader(){
    var author = localStorage.getItem('authorToken')
    if (author) {
      return false
    } else {
      return true
    }
  }

  ngOnInit(): void {
    var readUrl = localStorage.getItem('read');
    if (readUrl) {
      this.readBook(readUrl);
      localStorage.removeItem('read')
    } else {
      this.router.navigate(["/reader/subscriptions"]);
    }
  }

}
