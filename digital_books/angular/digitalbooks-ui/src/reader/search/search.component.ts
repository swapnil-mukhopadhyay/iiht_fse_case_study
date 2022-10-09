import { Component} from '@angular/core';
import{BookPayload} from '../../models/book.payload';
import{BookDto} from '../../models/book.dto';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent{

  searchForm:FormGroup;

  constructor() { 
    this.searchForm=new FormGroup({
      price: new FormControl(0, [Validators.min(0)])
  })
  }

  ngOnInit(): void {
  }

}
