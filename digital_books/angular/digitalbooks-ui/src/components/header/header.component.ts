import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  
  isAuthorLoggedIn(): boolean {
    var token=localStorage.getItem('authorToken')
    if(token){
      return true
    }else{
      return false
    }
  }

  constructor() { }

  ngOnInit(): void {
  }

}
