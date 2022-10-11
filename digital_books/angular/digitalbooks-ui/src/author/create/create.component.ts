import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { AuthorPayload } from 'src/models/author.payload';
import { BookDto } from 'src/models/book.dto';
import { CreateService } from './create.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {

  bookForm: FormGroup;
  message: string = ''

  constructor(private _createService: CreateService, private router: Router) {

    if(!this.isAuthor()){
      this.router.navigate(["author/login"])
    }
    this.bookForm = new FormGroup({
      logo: new FormControl("", [Validators.required]),
      title: new FormControl("", [Validators.required]),
      category: new FormControl("", [Validators.required]),
      price: new FormControl([Validators.min(0), isNaN]),
      publisher: new FormControl("", [Validators.required]),
      active: new FormControl("true", [Validators.required]),
      content: new FormControl("", [Validators.required])
    })
  }

  ngOnInit(): void {
  }

  createBook(logo: string, title: string, category: string, price: string, publisher: string, active: string, content: string) {

    if (this.isAuthor()) {
      var bookDto: BookDto = {
        logo: logo,
        title: title,
        category: category,
        price: Number(price),
        author: getAuthor(),
        publisher: publisher,
        active: Boolean(active),
        content: content
      }
      var authorPayload: AuthorPayload = {
        name: getAuthor(),
        bookDtoList: [bookDto]
      }
      this._createService.createBook(authorPayload, getAuthorToken()).subscribe({
        next: (res: any) => {
          if (!!res.statusCode) {
            alert(res.message)
          } else {
            this.message = "Book Has Been Created Successfully"
          }
        },
        error: (err: any) => {
          console.log(err)
          localStorage.clear();
          this.router.navigate(["author/login"])
        }
      })
    } else {
      this.router.navigate(["author/login"])
    }
  }

  isAuthor(){
    var author = localStorage.getItem('authorToken')
    if (author) {
      return true
    } else {
      return false
    }
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

