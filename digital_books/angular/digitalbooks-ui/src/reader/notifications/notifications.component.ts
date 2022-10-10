import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ReaderPayload } from 'src/models/reader.payload';
import { NotificationsService } from './notifications.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent {

  emailForm: FormGroup;
  readerPayload: ReaderPayload = {
    readerDto:{
      readerId:0,
      name:'',
      emailId:''
    },
    notifications:[],
    bookDtoList: []
  }
  constructor(private _notificationsService:NotificationsService) { 
    this.emailForm = new FormGroup({
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
  }

  getNotifications(emailId:string){
    this._notificationsService.getNotifications(emailId).subscribe({
      next: (res: any) => {
        this.readerPayload = res;
        console.log(this.readerPayload);
      },
      error: (err: any) => {
        console.log(err)
        this.readerPayload.readerDto={
          readerId:1,
          name:'reader',
          emailId:'reader@gmail'
        }
        this.readerPayload.bookDtoList = [
          { bookId: 1, logo: 'booklogo.png', title: 'The Best Book', category: 'Education', price: 100, authorId: 1, author: 'Author1', publisher: 'Penguin', publishedDate: '02/02/2021', active: true },
          { bookId: 2, logo: 'worstbooklogo.png', title: 'The Worst Book', category: 'Education', price: 150, authorId: 2, author: 'Author2', publisher: 'Dolphin', publishedDate: '02/03/2021', active: true }
        ]
        this.readerPayload.notifications=["abc","def","ghi"]
      }
    })
  }

}
