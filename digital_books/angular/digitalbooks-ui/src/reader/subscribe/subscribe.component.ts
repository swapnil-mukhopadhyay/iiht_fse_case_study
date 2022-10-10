import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookDto } from 'src/models/book.dto';
import { BookPurchasePayload } from 'src/models/book.purchase.payload';
import { PaymentInvoicePayload } from 'src/models/payment.invoice.payload';
import { SubscribeService } from './subscribe.service';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.scss']
})
export class SubscribeComponent implements OnInit{

  purchaseForm: FormGroup;

  bookDto:BookDto={
    bookId:0,
    active:true,
    author:'',
    authorId:0,
    price:0,
    category:'',
    publishedDate:'',
    publisher:'',
    logo:'',
    title:'',
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

  paymentInvoiceHtml:any={
    heading:"",
    title:"",
    bookId:"",
    paymentId:"",
    paymentDate:"",
    readerName:"",
    readerEmail:""
  }

  constructor(private _subscribeService:SubscribeService, private router:Router) {
    this.purchaseForm = new FormGroup({
      name: new FormControl("", [Validators.required]),
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
  }
  ngOnInit(): void {
    var subscribePayloadInString=localStorage.getItem("subscribe");
    if(subscribePayloadInString){
      this.bookDto=JSON.parse(subscribePayloadInString);
    }else{
      this.router.navigate(["/reader/search"]);
    }
  }

  buyBook(name: string, emailId: string) {
    var bookPurchasePayload:BookPurchasePayload={
      bookId:this.bookDto.bookId,
      readerDto:{
        name:name,
        emailId:emailId
      }
    }
    this._subscribeService.buyBook(bookPurchasePayload).subscribe({
      
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
        this.paymentInvoiceHtml.heading="Payment Invoice"
        this.paymentInvoicePayload.bookDto= { bookId: 1, logo: 'booklogo.png', title: 'The Best Book', category: 'Education', price: 100, authorId: 1, author: 'Author1', publisher: 'Penguin', publishedDate: '02/02/2021', active: true}
        this.paymentInvoiceHtml.title="Title Purchased : "+this.paymentInvoicePayload.bookDto.title
        this.paymentInvoiceHtml.bookId="Book ID : "+this.paymentInvoicePayload.bookDto.bookId
        this.paymentInvoiceHtml.paymentId="Payment ID : "+this.paymentInvoicePayload.paymentId
        this.paymentInvoiceHtml.paymentDate="Payment Done On : "+this.paymentInvoicePayload.paymentDateTime
        this.paymentInvoiceHtml.readerName="Name of Reader : "+name
        this.paymentInvoiceHtml.readerEmail="Reader Email ID : "+emailId
      }
    })

  }


}
