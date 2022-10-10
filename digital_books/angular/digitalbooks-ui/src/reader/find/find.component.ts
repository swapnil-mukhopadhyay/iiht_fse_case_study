import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PaymentInvoicePayload } from 'src/models/payment.invoice.payload';
import { ReaderPayload } from 'src/models/reader.payload';
import { FindService } from './find.service';

@Component({
  selector: 'app-find',
  templateUrl: './find.component.html',
  styleUrls: ['./find.component.scss']
})
export class FindComponent {

  invoiceForm: FormGroup;

  paymentInvoiceHtml: any = {
    heading: "",
    title: "",
    bookId: "",
    paymentId: "",
    paymentDate: "",
    readerName: "",
    readerEmail: ""
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
    readerDto:{
      name:"",
      emailId:""
    },
    paymentDateTime: ''
  }

  constructor(private _findService: FindService, private router: Router) {
    this.invoiceForm = new FormGroup({
      paymentId: new FormControl([Validators.min(0), isNaN]),
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
  }

  findInvoice(paymentId: string, emailId: string) {
    this._findService.findBookByPaymentId(emailId, Number(paymentId)).subscribe({
      next: (res: any) => {
        this.paymentInvoicePayload = res;
        console.log(this.paymentInvoicePayload);
        this.paymentInvoiceHtml.heading="Payment Invoice"
        this.paymentInvoiceHtml.title="Title Purchased : "+this.paymentInvoicePayload.bookDto.title
        this.paymentInvoiceHtml.bookId="Book ID : "+this.paymentInvoicePayload.bookDto.bookId
        this.paymentInvoiceHtml.paymentId="Payment ID : "+this.paymentInvoicePayload.paymentId
        this.paymentInvoiceHtml.paymentDate="Payment Done On : "+this.paymentInvoicePayload.paymentDateTime
        this.paymentInvoiceHtml.readerName="Name of Reader : "+this.paymentInvoicePayload.readerDto.name
        this.paymentInvoiceHtml.readerEmail="Reader Email ID : "+this.paymentInvoicePayload.readerDto.emailId
      },
      error: (err: any) => {
        console.log(err)
        this.paymentInvoicePayload.readerDto = {
          readerId: 1,
          name: 'reader',
          emailId: 'reader@gmail'
        }
        this.paymentInvoicePayload.bookDto= 
          { bookId: 1, logo: 'booklogo.png', title: 'The Best Book', category: 'Education', price: 100, authorId: 1, author: 'Author1', publisher: 'Penguin', publishedDate: '02/02/2021', active: true },
        this.paymentInvoiceHtml.heading="Payment Invoice"
        this.paymentInvoiceHtml.title="Title Purchased : "+this.paymentInvoicePayload.bookDto.title
        this.paymentInvoiceHtml.bookId="Book ID : "+this.paymentInvoicePayload.bookDto.bookId
        this.paymentInvoiceHtml.paymentId="Payment ID : "+this.paymentInvoicePayload.paymentId
        this.paymentInvoiceHtml.paymentDate="Payment Done On : "+this.paymentInvoicePayload.paymentDateTime
        this.paymentInvoiceHtml.readerName="Name of Reader : "+this.paymentInvoicePayload.readerDto.name
        this.paymentInvoiceHtml.readerEmail="Reader Email ID : "+this.paymentInvoicePayload.readerDto.emailId
      }
    })
  }

}