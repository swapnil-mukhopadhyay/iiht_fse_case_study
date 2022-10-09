import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BookPayload } from 'src/models/book.payload';
import { PaymentInvoicePayload } from 'src/models/payment.invoice.payload';
import { SubscribeService } from './subscribe.service';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.scss']
})
export class SubscribeComponent {

  purchaseForm: FormGroup;

  bookPayload: BookPayload = {
    bookDtoList: []
  }

  paymentInvoicePayload: PaymentInvoicePayload = {
    paymentId: 0,
    bookDto: {
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
    },
    paymentDateTime: ''
  }

  constructor(private _subscribeService:SubscribeService) {
    this.purchaseForm = new FormGroup({
      name: new FormControl("", [Validators.required]),
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
  }

  buyBook(name: string, emailId: string) {

  }


}
