import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CredentialPayload } from 'src/models/credential.payload';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private login_url: string = "http://localhost:8888/api/v1/digitalbooks/author/login";

  login(credentialPayload: CredentialPayload) {
    console.log("Created Signup URL : ",this.login_url)
    return this.http.post(this.login_url,credentialPayload);
  }

  constructor(private http:HttpClient) { }
}
