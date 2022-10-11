import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookDto } from 'src/models/book.dto';
import { BookPayload } from 'src/models/book.payload';
import { SearchService } from './search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent {

  searchForm: FormGroup;
  bookPayload: BookPayload = {
    bookDtoList: []
  }

  constructor(private _searchService: SearchService, private router: Router) {

    if(!this.isReader()){
      this.router.navigate(["home"])
    }
    this.findAllBooks();
    this.searchForm = new FormGroup({
      price: new FormControl([Validators.min(0), isNaN])
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

  findBooksByParams(category: string, author: string, price: string, publisher: string) {

    if (!category && !author && !price && !publisher) {
      this.findAllBooks();
    } else {
      this._searchService.findBooksByParams(category, author, Number(price), publisher).subscribe({
        next: (res: any) => {
          this.bookPayload = res;
          console.log(this.bookPayload);
        },
        error: (err: any) => {
          console.log(err)
        }
      })
    }
  }

  subscribe(bookDto: BookDto) {
    console.log(bookDto);
    localStorage.setItem("subscribe", JSON.stringify(bookDto));
    this.router.navigate(["/reader/subscribe"]);
  }

  findAllBooks() {
    this._searchService.findAllBooks().subscribe({
      next: (res: any) => {
        if (!!res.statusCode) {
          alert(res.message)
        } else {
          this.bookPayload = res;
          console.log(this.bookPayload);
        }
      },
      error: (err: any) => {
        console.log(err)
      }
    })
  }
}
