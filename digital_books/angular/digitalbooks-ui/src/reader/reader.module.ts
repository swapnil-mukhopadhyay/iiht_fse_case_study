import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchComponent } from './search/search.component';
import { RefundComponent } from './refund/refund.component';
import { SubscribeComponent } from './subscribe/subscribe.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ReadComponent } from './read/read.component';
import { InvoiceComponent } from './invoice/invoice.component';
import { FindComponent } from './find/find.component';

const routes:Routes = [
  { path: "search", component: SearchComponent },
  { path: "subscribe", component: SubscribeComponent },
  { path: "subscriptions", component: SubscriptionsComponent },
  { path: "notifications", component: NotificationsComponent },
  { path: "refund", component: RefundComponent},
  { path: "read", component: ReadComponent},
  { path: "find", component: FindComponent},
  { path: "invoice", component: InvoiceComponent},
];

@NgModule({
  declarations: [
    SearchComponent,
    RefundComponent,
    SubscribeComponent,
    SubscriptionsComponent,
    NotificationsComponent,
    ReadComponent,
    InvoiceComponent,
    FindComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    HttpClientModule
  ]
})
export class ReaderModule { }
