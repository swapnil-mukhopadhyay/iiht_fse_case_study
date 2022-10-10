import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

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

  constructor() {
    this.invoiceForm = new FormGroup({
      paymentId: new FormControl([Validators.min(0), isNaN]),
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
  }

  findInvoice(paymentId: string, emailId: string) {
    
  }

}
