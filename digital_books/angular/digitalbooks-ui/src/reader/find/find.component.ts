import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { isEmpty } from 'rxjs';
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
    readerDto: {
      name: "",
      emailId: ""
    },
    paymentDateTime: ''
  }

  constructor(private _findService: FindService, private router: Router) {

    if(!this.isReader()){
      this.router.navigate(["home"])
    }
    this.invoiceForm = new FormGroup({
      paymentId: new FormControl([Validators.min(0), isNaN]),
      emailId: new FormControl("", [Validators.required, Validators.email])
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

  viewInvoice(paymentInvoiceHtml:any){
    localStorage.setItem('invoice',JSON.stringify(paymentInvoiceHtml))
    this.router.navigate(['reader/invoice'])
  }

  invoiceGenerated:boolean=false


  findInvoice(paymentId: string, emailId: string) {
    this._findService.findBookByPaymentId(emailId, Number(paymentId)).subscribe({
      next: (res: any) => {
        if (!!res.statusCode) {
          alert(res.message)
        } else {
          this.paymentInvoicePayload = res;
          console.log(this.paymentInvoicePayload);
          this.paymentInvoiceHtml.heading = "Payment Invoice"
          this.paymentInvoiceHtml.title = "Title Purchased : " + this.paymentInvoicePayload.bookDto.title
          this.paymentInvoiceHtml.bookId = "Book ID : " + this.paymentInvoicePayload.bookDto.bookId
          this.paymentInvoiceHtml.paymentId = "Payment ID : " + this.paymentInvoicePayload.paymentId
          this.paymentInvoiceHtml.paymentDate = "Payment Done On : " + this.paymentInvoicePayload.paymentDateTime
          this.paymentInvoiceHtml.readerName = "Name of Reader : " + this.paymentInvoicePayload.readerDto.name
          this.paymentInvoiceHtml.readerEmail = "Reader Email ID : " + this.paymentInvoicePayload.readerDto.emailId
          this.invoiceGenerated=true
        }
      },
      error: (err: any) => {
        console.log(err)
      }
    })
  }

}
