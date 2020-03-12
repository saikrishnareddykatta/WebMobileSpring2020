import { Component } from '@angular/core';
import { KnowledgeService } from './knowledge.service'; /*Importing Service named KnowledgeService from knowledge.service.ts*/

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'knowledge-api'; /*title*/
  apiResponse: any; /*any type represents any javascript value with no constraints*/

  constructor(private knowledgeService : KnowledgeService) { /*Dependency Injection*/
  }

  getApiValue(value) { /* this function gets called from the app.component.html file with value as a parameter in it*/
    console.log('Entered value...',value); /*prints the entered value in console just for the debugging purpose*/
    this.knowledgeService.knowledgeApi(value).subscribe( res => { /* as we are working with ajax to get an response we have to use a service*/
      /* here the service name knowledge, this line of code waits for the response*/
      /*here subscribe is an observable and waits for response and executes the code when it receives an response*/
      console.log('Api response.....', res); /*prints the response in the console*/
      if (res) { /*if there is any response then excute the below code*/
        this.apiResponse = res; /* if we get an response, store it in apiResponse*/
        // .itemListElement[0].result
      }
    })
  }
}
