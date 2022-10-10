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
    readerDto: {
      readerId: 0,
      name: '',
      emailId: ''
    },
    notifications: [],
    bookDtoList: []
  }
  constructor(private _notificationsService: NotificationsService) {
    this.emailForm = new FormGroup({
      emailId: new FormControl("", [Validators.required, Validators.email])
    })
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
