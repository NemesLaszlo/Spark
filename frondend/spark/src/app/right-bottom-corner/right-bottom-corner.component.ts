import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-right-bottom-corner',
  templateUrl: './right-bottom-corner.component.html',
  styleUrls: ['./right-bottom-corner.component.css']
})
export class RightBottomCornerComponent implements OnInit {

  private match: boolean = false;
  private rate: boolean = false;

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
    switch(this.router.url){
      case '/message':
        this.match = true;
        break;
      case '/match':
        this.rate = true;
        break;
    }
  }

  onClick(){
    switch(this.router.url){
      case '/message':
        this.router.navigate(['match'])
        break;
      case '/match':
        this.router.navigate(['rate'])
        break;
    }
  }

}
