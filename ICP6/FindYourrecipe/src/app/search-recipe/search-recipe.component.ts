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

//for the location search we are limiting the location to 5 results
    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      this._http.get("https://kgsearch.googleapis.com/v1/entities:search?query="+this.recipeValue+"&key=AIzaSyBOsDrC0v4kLuLsVqodqyRICdQBfRZAaVU&limit=1&indent=True").subscribe((result: any) => {
        console.log(result,"apppppppppppppppppppppppp");
        /*this.venueList getVenues= Object.keys(result.response.venues).map(function (k) {
          const i = result.response.venues[k];
          return {name: i.name, currentLat: '39.0349657', currentLong: '-94.5787524', formattedAddress: i.location.formattedAddress};
        });*/
      });
    }
  }
}
