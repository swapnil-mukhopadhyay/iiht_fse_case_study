import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BookDto } from 'src/models/book.dto';
import { BookPayload } from 'src/models/book.payload';
import { SubscriptionsService } from './subscriptions.service';

@Component({
  selector: 'app-subscriptions',
  templateUrl: './subscriptions.component.html',
  styleUrls: ['./subscriptions.component.scss']
})

export class SubscriptionsComponent {
  emailForm: FormGroup;
  bookPayload: BookPayload = {
    bookDtoList: []
  }

  constructor(private _subscriptionService: SubscriptionsService) {
    this.emailForm = new FormGroup({
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
  }

  getSubscriptions(emailId: string) {
    this._subscriptionService.getSubscriptions(emailId).subscribe({
      next: (res: any) => {
        this.bookPayload = res;
        console.log(this.bookPayload);
      },
      error: (err: any) => {
        console.log(err)
        this.bookPayload.bookDtoList = [
          { bookId: 1, logo: 'booklogo.png', title: 'The Best Book', category: 'Education', price: 100, authorId: 1, author: 'Author1', publisher: 'Penguin', publishedDate: '02/02/2021', active: true },
          { bookId: 2, logo: 'worstbooklogo.png', title: 'The Worst Book', category: 'Education', price: 150, authorId: 2, author: 'Author2', publisher: 'Dolphin', publishedDate: '02/03/2021', active: true }
        ]
      }
    })
  }

  viewBook(bookDto: BookDto) {
    console.log("Viewing Book", bookDto)
  }

}
