import { Component, OnInit } from '@angular/core';
import {environment} from '../../../environments/environment';


@Component({
  selector: 'app-main-idea',
  templateUrl: './main-idea.component.html',
  styleUrls: ['./main-idea.component.scss']
})
export class MainIdeaComponent implements OnInit {

  homeLogo: string;

  constructor() {
    this.homeLogo = environment.images_urls + 'what_is_rest_api.png';
  }

  ngOnInit(): void {
  }

}
