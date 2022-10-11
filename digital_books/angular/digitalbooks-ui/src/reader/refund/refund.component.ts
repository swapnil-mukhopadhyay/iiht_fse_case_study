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
    readerDto: {
      readerId: 0,
      name: '',
      emailId: ''
    },
    notifications: [],
    bookDtoList: []
  }

  refundMessage: string = ''

  constructor(private _refundService: RefundService, private router: Router) { 
    if(!this.isReader()){
      this.router.navigate(["home"])
    }
  }

  isReader(){
    var author = localStorage.getItem('authorToken')
    if (author) {
      return false
    } else {
      return true
    }
  }
  refundBook(url: string, bookPurchasePayload: BookPurchasePayload) {
    this._refundService.refundBook(url, bookPurchasePayload).subscribe({
      next: (res: any) => {
        if (!!res.statusCode) {
          alert(res.message)
        } else {
          this.readerPayload = res;
          var bookTitle = this.readerPayload.bookDtoList[0].title
          this.refundMessage = 'Successfully refunded book : ' + bookTitle
          console.log(this.readerPayload);
        }
      },
      error: (err: any) => {
        console.log(err)
      }
    })
  }

  ngOnInit(): void {
    var refundUrl = localStorage.getItem('refund');
    var refundPayloadInString = localStorage.getItem('refundPayload')
    if (refundUrl && refundPayloadInString) {
      var bookPurchasePayload: BookPurchasePayload = JSON.parse(refundPayloadInString);
      this.refundBook(refundUrl, bookPurchasePayload);
      localStorage.removeItem('refund')
      localStorage.removeItem('refundPayload')
    } else {
      this.router.navigate(["/reader/subscriptions"]);
    }
  }

}
