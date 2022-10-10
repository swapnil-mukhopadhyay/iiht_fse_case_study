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
    readerDto: {
      readerId: 0,
      name: '',
      emailId: ''
    },
    notifications: [],
    bookDtoList: []
  }

  constructor(private _subscriptionService: SubscriptionsService, private router: Router) {
    this.emailForm = new FormGroup({
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
  }

  getSubscriptions(emailId: string) {
    this._subscriptionService.getSubscriptions(emailId).subscribe({
      next: (res: any) => {
        if (!!res.statusCode) {
          alert(res.message)
        } else {
          this.readerPayload = res;
          console.log(this.readerPayload);
        }
      },
      error: (err: any) => {
        console.log(err)
      }
    })
  }

  readBook(bookDto: BookDto, emailId: string) {
    var readUrl = emailId + "/books/" + bookDto.bookId;
    localStorage.setItem("read", readUrl);
    this.router.navigate(["/reader/read"]);

  }

  refundBook(bookDto: BookDto, emailId: string) {
    var refundUrl = emailId + "/books/" + bookDto.bookId + "/refund";
    localStorage.setItem("refund", refundUrl);
    var bookPurchasePayload: BookPurchasePayload = {
      bookId: bookDto.bookId,
      readerDto: this.readerPayload.readerDto
    }
    localStorage.setItem("refundPayload", JSON.stringify(bookPurchasePayload))
    this.router.navigate(["/reader/refund"]);
  }

}
