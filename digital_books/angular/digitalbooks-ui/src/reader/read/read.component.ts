import { Component, OnInit } from '@angular/core';
import { ReaderPayload } from 'src/models/reader.payload';
import { ReadService } from './read.service';

@Component({
  selector: 'app-read',
  templateUrl: './read.component.html',
  styleUrls: ['./read.component.scss']
})
export class ReadComponent implements OnInit{

  readerPayload: ReaderPayload = {
    readerDto:{
      readerId:0,
      name:'',
      emailId:''
    },
    notifications:[],
    bookDtoList: []
  }

  constructor(private _readService:ReadService) { }

  readBook(url:string){
    this._readService.readBook(url).subscribe({
      next: (res: any) => {
        this.readerPayload = res;
        console.log(this.readerPayload);
      },
      error: (err: any) => {
        console.log(err)
        this.readerPayload.bookDtoList = [
          { bookId: 1, logo: 'booklogo.png', title: 'The Best Book', category: 'Education', price: 100, authorId: 1, author: 'Author1', publisher: 'Penguin', publishedDate: '02/02/2021', active: true },
          { bookId: 2, logo: 'worstbooklogo.png', title: 'The Worst Book', category: 'Education', price: 150, authorId: 2, author: 'Author2', publisher: 'Dolphin', publishedDate: '02/03/2021', active: true }
        ]
      }
    })
  }

  ngOnInit(): void {
    var readUrl=localStorage.getItem('read');
    if(readUrl){
      this.readBook(readUrl);
      localStorage.removeItem('read')
    }
  }

}
