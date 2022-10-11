import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorPayload } from 'src/models/author.payload';
import { BookDto } from 'src/models/book.dto';
import { BookPayload } from 'src/models/book.payload';
import { EditService } from '../edit/edit.service';
import { BooksService } from './books.service';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent {

  bookPayload: BookPayload = {
    bookDtoList: []
  }

  constructor(private _booksService: BooksService, private router: Router, private _editService: EditService) {
    if(!this.isAuthor()){
      this.router.navigate(["author/login"])
    }
    this.findAllBooksForAuthor(getAuthor(), getAuthorToken())
  }

  isAuthor(){
    var author = localStorage.getItem('authorToken')
    if (author) {
      return true
    } else {
      return false
    }
  }

  editBook(bookDto: BookDto) {
    localStorage.setItem('edit', JSON.stringify(bookDto))
    this.router.navigate(["/author/edit"]);

  }

  toggleActive(bookDto: BookDto) {
    bookDto.active = !bookDto.active
    var authorPayload: AuthorPayload = {
      name: getAuthor(),
      bookDtoList: [bookDto]
    }
    if (bookDto.bookId) {
      this._editService.editBook(authorPayload, getAuthorToken(), bookDto.bookId).subscribe({
        next: (res: any) => {
          if (!!res.statusCode) {
            alert(res.message)
          } else {
            alert(bookDto.title + " has been successfully " + this.getToggleButtonValue(!bookDto.active) + "ed.")
          }
        },
        error: (err: any) => {
          console.log(err)
          localStorage.clear();
          this.router.navigate(["author/login"])
        }
      })
    }

  }

  getToggleButtonValue(active: boolean) {
    if (active) {
      return "Block"
    } else {
      return "Unblock"
    }
  }

  findAllBooksForAuthor(authorName: string, authorToken: string) {
    this._booksService.getAllBooksForAuthor(authorName, authorToken).subscribe({
      next: (res: any) => {
        if (!!res.statusCode) {
          alert(res.message)
        } else {
          this.bookPayload = res
        }
      },
      error: (err: any) => {
        console.log(err)
        this.router.navigate(["author/login"])
      }
    })
  }
}

function getAuthor(): string {
  var author = localStorage.getItem('loggedInAuthor')
  if (author) {
    return author
  } else {
    return ""
  }
}

function getAuthorToken(): string {
  var authorToken = localStorage.getItem('authorToken')
  if (authorToken) {
    return authorToken
  } else {
    return ""
  }
}
