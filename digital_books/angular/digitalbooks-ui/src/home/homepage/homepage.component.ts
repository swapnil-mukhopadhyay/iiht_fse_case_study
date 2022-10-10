import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  isAuthorLoggedIn(): boolean {
    var token=localStorage.getItem('authorToken')
    if(token){
      return true
    }else{
      return false
    }
  }

  getUsername(){
    var username=localStorage.getItem('loggedInAuthor')
    if(username){
      return username
    }else{
      return "an Author"
    }
  }

  constructor() { }

  ngOnInit(): void {
    
  }

}
