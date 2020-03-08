import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {ApiService} from '../api.service';
import {FormControl, FormGroupDirective, FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {

  bookForm: FormGroup;
  isbn: string = '';
  title: string = '';
  description: string = '';
  author: string = '';
  publisher: string = '';
  published_year: string = '';

  constructor(private router: Router, private route: ActivatedRoute, private api: ApiService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {

    this.bookForm = this.formBuilder.group({
      'isbn': '',
      'title': '',
      'description': '',
      'author': '',
      'publisher': '',
      'published_year': ''
    });
    this.api.getBook(this.route.snapshot.params['id'])

      .subscribe(data => {

        console.log(data.isbn);
        this.bookForm = this.formBuilder.group({
          'isbn': data.isbn,
          'title': data.title,
          'description': data.description,
          'author': data.author,
          'publisher': data.publisher,
          'published_year': data.published_year
        });
      });
  }
  onFormSubmit(form: NgForm) {
    this.api.updateBook(this.route.snapshot.params['id'], form)
      .subscribe(res => {
        let id = this.route.snapshot.params['id'];
        this.router.navigate(['/book-details', id]);
      }, (err) => {
        console.log(err);
      });
  }
}
