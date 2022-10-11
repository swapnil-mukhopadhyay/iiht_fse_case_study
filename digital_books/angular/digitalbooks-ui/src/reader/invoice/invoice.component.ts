import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.scss']
})
export class InvoiceComponent implements OnInit {

  paymentInvoiceHtml: any = {
    heading: "",
    title: "",
    bookId: "",
    paymentId: "",
    paymentDate: "",
    readerName: "",
    readerEmail: ""
  }

  constructor(private router:Router) { 
    if(!this.isReader()){
      this.router.navigate(["home"])
    }
    var invoice=localStorage.getItem('invoice')
    if(invoice){
      this.paymentInvoiceHtml=JSON.parse(invoice)
    }else{
      this.router.navigate(['reader/find'])
    }
    localStorage.removeItem('invoice')
  }

  isReader(){
    var author = localStorage.getItem('authorToken')
    if (author) {
      return false
    } else {
      return true
    }
  }

  ngOnInit(): void {
  }

}
