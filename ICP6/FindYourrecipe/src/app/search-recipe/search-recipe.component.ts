import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList = [];
  recipeList = [];
  formattedAddress = [];

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  recepieApi = 'https://api.edamam.com/search?q=';
  recepieAppid = '&app_id=ba18fdc5';
  recepieKey = '&app_key=588b400e488e39a56644f2a2e8094f12';


  constructor(private _http: HttpClient) {
  }

  ngOnInit() {
//for getting the users current location
    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }
//this method get the locations and we are only storing the label, icons and url and showing it on the html page.
  getVenues() {
    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    //for the recipes we are limiting the results to 4 recipes
    if (this.recipeValue !== null) {
      this._http.get("https://api.edamam.com/search?q=" +this.recipeValue + "&app_id=ec4c3e7f\n" +
          "\n&app_key=\n" +
          "89610c7324b6ed3177973db357da3f11&to=4")
          .subscribe((result: any) => {
        console.log(result);
        this.recipeList = Object.keys(result.hits).map(function (k) {
          const i = result.hits[k].recipe;
          return {name: i.label, icon: i.image, url: i.url};
        });

      });

    }
//for the location search we are limiting the location to 5 results
    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      this._http.get("https://api.foursquare.com/v2/venues/search?client_id=NW5OT2YFU0P5TXXLREWVJLWXKXJFJ2BAEBCYPVFDAS1XLW1Y\n" +
          "\n" +
          "&client_secret=XLI5YMVPLEAJFITFSFDGJDRU0J022IA0SQMNLQD1DQKHB5CR&v=20180323&limit=5&near=" +this.placeValue).subscribe((result: any) => {
        console.log(result);
        this.venueList = Object.keys(result.response.venues).map(function (k) {
          const i = result.response.venues[k];
          return {name: i.name, currentLat: '39.0349657', currentLong: '-94.5787524', formattedAddress: i.location.formattedAddress};
        });
      });
    }
  }
}
