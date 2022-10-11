import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
    readerDto: {
      readerId: 0,
      name: '',
      emailId: ''
    },
    notifications: [],
    bookDtoList: []
  }
  constructor(private _notificationsService: NotificationsService,private router:Router) {
    if(!this.isReader()){
      this.router.navigate(["home"])
    }
    this.emailForm = new FormGroup({
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

  getNotifications(emailId: string) {
    this._notificationsService.getNotifications(emailId).subscribe({
      next: (res: any) => {
        if (!!res.statusCode) {
          alert(res.message)
        } else {
          this.readerPayload = res;
          console.log(this.readerPayload);
        }
      },
      error: (err: any) => {
        console.log(err)
      }
    })
  }

}
