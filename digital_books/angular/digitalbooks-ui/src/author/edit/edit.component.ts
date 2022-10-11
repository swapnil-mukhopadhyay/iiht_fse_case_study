import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthorPayload } from 'src/models/author.payload';
import { BookDto } from 'src/models/book.dto';
import { EditService } from './edit.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent {

  message: string = ""
  bookForm: FormGroup;
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

  constructor(private _editService: EditService, private router: Router) {

    if(!this.isAuthor()){
      this.router.navigate(["author/login"])
    }
    
    var edit = localStorage.getItem('edit');
    if (edit) {
      this.bookDto = JSON.parse(edit);
    } else {
      this.router.navigate(["/author/books"]);
    }
    this.bookForm = new FormGroup({
      logo: new FormControl(this.bookDto.logo, [Validators.required]),
      title: new FormControl(this.bookDto.title, [Validators.required]),
      category: new FormControl(this.bookDto.category, [Validators.required]),
      price: new FormControl(this.bookDto.price,[Validators.min(0)]),
      publisher: new FormControl(this.bookDto.publisher, [Validators.required]),
      active: new FormControl(this.bookDto.active, [Validators.required]),
      content: new FormControl(this.bookDto.content, [Validators.required])
    })
  }
  editBook(logo: string, title: string, category: string, price: string, publisher: string, active: string, content: string) {
    if (getAuthor && this.bookDto.bookId) {
      var bookDto: BookDto = {
        logo: logo,
        title: title,
        category: category,
        price: Number(price),
        author: getAuthor(),
        publisher: publisher,
        active: Boolean(active),
        content: content,
        bookId:this.bookDto.bookId
      }
      var authorPayload: AuthorPayload = {
        name: getAuthor(),
        bookDtoList: [bookDto]
      }
      this._editService.editBook(authorPayload, getAuthorToken(),this.bookDto.bookId).subscribe({
        next: (res: any) => {
          if (!!res.statusCode) {
            alert(res.message)
          } else {
            this.message = "Book Has Been Edited Successfully"
          }
        },
        error: (err: any) => {
          console.log(err)
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