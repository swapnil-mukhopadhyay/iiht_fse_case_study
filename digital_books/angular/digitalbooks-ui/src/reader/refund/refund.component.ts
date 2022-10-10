import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookDto } from 'src/models/book.dto';
import { BookPurchasePayload } from 'src/models/book.purchase.payload';
import { ReaderPayload } from 'src/models/reader.payload';
import { RefundService } from './refund.service';

@Component({
  selector: 'app-refund',
  templateUrl: './refund.component.html',
  styleUrls: ['./refund.component.scss']
})
export class RefundComponent implements OnInit {

  readerPayload: ReaderPayload = {
    readerDto:{
      readerId:0,
      name:'',
      emailId:''
    },
    notifications:[],
    bookDtoList: []
  }

  refundMessage:string=''

  constructor(private _refundService:RefundService, private router:Router) { }

  refundBook(url:string, bookPurchasePayload:BookPurchasePayload){
    this._refundService.refundBook(url,bookPurchasePayload).subscribe({
      next: (res: any) => {
        this.readerPayload = res;
        var bookTitle=this.readerPayload.bookDtoList[0].title
        this.refundMessage='Successfully refunded book : '+bookTitle
        console.log(this.readerPayload);
      },
      error: (err: any) => {
        console.log(err)
        this.readerPayload.bookDtoList = [
          { bookId: 1, logo: 'booklogo.png', title: 'The Best Book', category: 'Education', price: 100, authorId: 1, author: 'Author1', publisher: 'Penguin', publishedDate: '02/02/2021', active: true },
          { bookId: 2, logo: 'worstbooklogo.png', title: 'The Worst Book', category: 'Education', price: 150, authorId: 2, author: 'Author2', publisher: 'Dolphin', publishedDate: '02/03/2021', active: true }
        ]
        var bookTitle=this.readerPayload.bookDtoList[0].title
        this.refundMessage='Successfully refunded book : '+bookTitle
      }
    })
  }

  ngOnInit(): void {
    var refundUrl=localStorage.getItem('refund');
    var refundPayloadInString=localStorage.getItem('refundPayload')
    if(refundUrl && refundPayloadInString){
      var bookPurchasePayload:BookPurchasePayload=  JSON.parse(refundPayloadInString);
      this.refundBook(refundUrl,bookPurchasePayload);
      localStorage.removeItem('refund')
      localStorage.removeItem('refundPayload')
    }else{
      this.router.navigate(["/reader/subscriptions"]);
    }
  }

}
