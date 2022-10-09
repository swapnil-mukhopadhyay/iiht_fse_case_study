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

const routes:Routes = [
  { path: "search", component: SearchComponent },
  { path: "subscribe", component: SubscribeComponent },
  { path: "subscriptions", component: SubscriptionsComponent },
  { path: "notifications", component: NotificationsComponent },
  { path: "refund", component: RefundComponent,
},
];

@NgModule({
  declarations: [
    SearchComponent,
    RefundComponent,
    SubscribeComponent,
    SubscriptionsComponent,
    NotificationsComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    HttpClientModule
  ]
})
export class ReaderModule { }
