import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {KnowledgeService} from './knowledge.service'; /*Importing Service named KnowledgeService from knowledge.service.ts*/
import { HttpClientModule } from '@angular/common/http'; /*Importing HttpClientModule to request for an response*/

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [KnowledgeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
