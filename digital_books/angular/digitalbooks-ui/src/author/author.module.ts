import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';



@NgModule({
  declarations: [
    SignupComponent,
    LoginComponent,
    CreateComponent,
    EditComponent
  ],
  imports: [
    CommonModule
  ]
})
export class AuthorModule { }
