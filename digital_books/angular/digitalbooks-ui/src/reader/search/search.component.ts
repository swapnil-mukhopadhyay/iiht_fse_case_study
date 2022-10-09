import { Component } from '@angular/core';
import { BookPayload } from '../../models/book.payload';
import { BookDto } from '../../models/book.dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { SearchService } from './search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent {

  searchForm: FormGroup;
  bookPayload:BookPayload={
    bookDtoList:[]
  }

  constructor(private _searchService: SearchService) {
    this.findAllBooks();
    this.searchForm = new FormGroup({
      price: new FormControl([Validators.min(0), isNaN])
    })
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

  findAllBooks() {
    this._searchService.findAllBooks().subscribe({
      next: (res: any) => {
        this.bookPayload = res;
        console.log(this.bookPayload);
      },
      error: (err: any) => {
        console.log(err)
        this.bookPayload.bookDtoList=[
          {bookId:1,logo:'booklogo.png',title:'The Best Book',category:'Education',price:100,authorId:1,author:'Author1',publisher:'Penguin', publishedDate:'2121',active:true}
        ]
      }
    })
  }
}
