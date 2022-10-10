import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {ReaderModule} from '../reader/reader.module';
import {AuthorModule} from '../author/author.module';
import { RouterModule, Routes } from '@angular/router';
import { HeaderComponent } from 'src/components/header/header.component';
import { FooterComponent } from 'src/components/footer/footer.component';
import { HomeModule } from 'src/home/home.module';


const routes:Routes = [
  { path: "reader", loadChildren: () => import("../reader/reader.module").then(m=>m.ReaderModule) },
  { path: "author", loadChildren: () => import("../author/author.module").then(m=>m.AuthorModule) },
  { path: "**", redirectTo: "home" }
];


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    ReaderModule,
    AuthorModule,
    HomeModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
