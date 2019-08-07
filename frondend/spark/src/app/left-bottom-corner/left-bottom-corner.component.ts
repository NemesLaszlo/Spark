import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-left-bottom-corner',
  templateUrl: './left-bottom-corner.component.html',
  styleUrls: ['./left-bottom-corner.component.css']
})
export class LeftBottomCornerComponent implements OnInit {

  public login: boolean = false;
  public registration: boolean = false;
  public message: boolean = false;
  public match: boolean = false;

  constructor(
    public router: Router
  ) { }

  ngOnInit() {

    switch(this.router.url){
      case '/login':
        this.registration = true;
        break;
      case '/registration':
        this.login = true;
        break;
      case '/match':
        this.message = true;
        break;
      case '/rate':
        this.match = true;
        break;
    }
  }

  onClick(){
    switch(this.router.url){
      case '/login':
        this.router.navigate(['/registration']);
        break;
      case '/registration':
        this.router.navigate(['/login']);
        break;
      case '/match':
        this.router.navigate(['/message']);
        break;
      case '/rate':
        this.router.navigate(['/match']);
        break;
    }
  }

}
