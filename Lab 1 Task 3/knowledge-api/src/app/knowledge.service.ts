import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class KnowledgeService {

  url: string = 'https://kgsearch.googleapis.com/v1/entities:search?query='; /*url used for knowledge graph API*/
  // taylor+swift&key=AIzaSyBOsDrC0v4kLuLsVqodqyRICdQBfRZAaVU&limit=1&indent=True';
  apiKey: string = "AIzaSyBOsDrC0v4kLuLsVqodqyRICdQBfRZAaVU"; /*Key generated for knowledge graph API*/
  constructor(private http: HttpClient) { } /*HttpClient is used to send and receive responses*/

  knowledgeApi(value) {
    return this.http.get(this.url + value + '&key=' + this.apiKey + '&limit=1&indent=True'); /*we are using get method to get response from the API, returns the response to app.component.ts*/
  }
}
