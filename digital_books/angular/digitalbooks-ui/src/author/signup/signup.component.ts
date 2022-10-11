import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { CredentialPayload } from 'src/models/credential.payload';
import { JwtResponse } from 'src/models/jwt.response';
import { SignupService } from './signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  credentialsForm: FormGroup
  constructor(private _signupService: SignupService, private router: Router) {

    if(!this.isReader()){
      this.router.navigate(["home"])
    }
    this.credentialsForm = new FormGroup({
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required])
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

  ngOnInit(): void {
    localStorage.clear();
  }

  signup(username: string, password: string) {
    var credentialPayload: CredentialPayload = {
      username: username,
      password: password
    }
    this._signupService.signup(credentialPayload).subscribe({
      next: (res: any) => {
        if (!!res.statusCode) {
          alert(res.message)
        } else {
          var jwt: JwtResponse = res
          localStorage.setItem('authorToken', jwt.token)
          localStorage.setItem('loggedInAuthor', credentialPayload.username)
          console.log('JWT from Server : ', jwt)
          this.router.navigate(["home"])
        }
      },
      error: (err: any) => {
        console.log(err)
      }
    })
  }

}
