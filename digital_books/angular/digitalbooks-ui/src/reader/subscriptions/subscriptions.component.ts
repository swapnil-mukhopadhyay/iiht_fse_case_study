import { JsonPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookDto } from 'src/models/book.dto';
import { BookPayload } from 'src/models/book.payload';
import { BookPurchasePayload } from 'src/models/book.purchase.payload';
import { ReaderPayload } from 'src/models/reader.payload';
import { SubscriptionsService } from './subscriptions.service';

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.scss']
})

export class SubscriptionsComponent {
  emailForm: FormGroup;
  readerPayload: ReaderPayload = {
    readerDto:{
      readerId:0,
      name:'',
      emailId:''
    },
    notifications:[],
    bookDtoList: []
  }

  constructor(private _subscriptionService: SubscriptionsService, private router:Router) {
    this.emailForm = new FormGroup({
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
  }

  getSubscriptions(emailId: string) {
    this._subscriptionService.getSubscriptions(emailId).subscribe({
      next: (res: any) => {
        this.readerPayload = res;
        console.log(this.readerPayload);
      },
      error: (err: any) => {
        console.log(err)
        this.readerPayload.readerDto={
          readerId:1,
          name:'reader',
          emailId:'reader@gmail'
        }
        this.readerPayload.bookDtoList = [
          { bookId: 1, logo: 'booklogo.png', title: 'The Best Book', category: 'Education', price: 100, authorId: 1, author: 'Author1', publisher: 'Penguin', publishedDate: '02/02/2021', active: true },
          { bookId: 2, logo: 'worstbooklogo.png', title: 'The Worst Book', category: 'Education', price: 150, authorId: 2, author: 'Author2', publisher: 'Dolphin', publishedDate: '02/03/2021', active: true }
        ]
      }
    })
  }

  readBook(bookDto:BookDto,emailId:string){
    var readUrl= emailId+"/books/"+bookDto.bookId;
    localStorage.setItem("read",readUrl);
    this.router.navigate(["/reader/read"]);

  }

  refundBook(bookDto:BookDto,emailId:string){
    var refundUrl= emailId+"/books/"+bookDto.bookId+"/refund";
    localStorage.setItem("refund",refundUrl);
    var bookPurchasePayload:BookPurchasePayload={
      bookId:bookDto.bookId,
      readerDto:this.readerPayload.readerDto
    }
    localStorage.setItem("refundPayload",JSON.stringify(bookPurchasePayload))
    this.router.navigate(["/reader/refund"]);
  }

}
