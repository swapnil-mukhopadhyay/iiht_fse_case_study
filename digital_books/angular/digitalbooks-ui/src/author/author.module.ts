import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';
import { LogoutComponent } from './logout/logout.component';
import { BooksComponent } from './books/books.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { FindComponent } from 'src/reader/find/find.component';
import { InvoiceComponent } from 'src/reader/invoice/invoice.component';
import { NotificationsComponent } from 'src/reader/notifications/notifications.component';
import { ReadComponent } from 'src/reader/read/read.component';
import { RefundComponent } from 'src/reader/refund/refund.component';
import { SearchComponent } from 'src/reader/search/search.component';
import { SubscribeComponent } from 'src/reader/subscribe/subscribe.component';
import { SubscriptionsComponent } from 'src/reader/subscriptions/subscriptions.component';
import { BlockComponent } from './block/block.component';

const routes:Routes = [
  { path: "create", component: CreateComponent },
  { path: "edit", component: EditComponent },
  { path: "login", component: LoginComponent },
  { path: "signup", component: SignupComponent },
  { path: "logout", component: LogoutComponent},
  { path: "books", component: BooksComponent},
  { path: "block", component: BlockComponent},
];

@NgModule({
  declarations: [
    SignupComponent,
    LoginComponent,
    CreateComponent,
    EditComponent,
    LogoutComponent,
    BooksComponent,
    BlockComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    HttpClientModule
  ]
})
export class AuthorModule { }
