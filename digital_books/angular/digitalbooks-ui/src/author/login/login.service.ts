import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CredentialPayload } from 'src/models/credential.payload';
import { authorurl } from 'src/urls/author.url';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private author_url = authorurl.url;
  private login_url: string = this.author_url + "/login"

  login(credentialPayload: CredentialPayload) {
    console.log("Created Signup URL : ", this.login_url)
    return this.http.post(this.login_url, credentialPayload);
  }

  constructor(private http: HttpClient) { }
}
