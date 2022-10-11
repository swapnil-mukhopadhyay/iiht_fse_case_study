import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private router:Router) {
    if(!this.isAuthor()){
      this.router.navigate(["author/login"])
    }
   }

  isAuthor(){
    var author = localStorage.getItem('authorToken')
    if (author) {
      return true
    } else {
      return false
    }
  }

  ngOnInit(): void {
  }

  logout(){
    localStorage.clear();
    this.router.navigate(['home'])
  }

  goToHomePage(){
    this.router.navigate(['home'])
  }

}
