import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CredentialPayload } from 'src/models/credential.payload';
import { JwtResponse } from 'src/models/jwt.response';
import { SignupService } from '../signup/signup.service';
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  credentialsForm: FormGroup
  constructor(private _loginService: LoginService, private router: Router) {
    this.credentialsForm = new FormGroup({
      username: new FormControl("", [Validators.required]),
      password: new FormControl("", [Validators.required])
    })
  }

  ngOnInit(): void {
    localStorage.clear();
  }

  login(username: string, password: string) {
    var credentialPayload: CredentialPayload = {
      username: username,
      password: password
    }
    this._loginService.login(credentialPayload).subscribe({
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
