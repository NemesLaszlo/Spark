import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private loginForm = new FormGroup({
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  })

  constructor(
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit() {
  }

  async onSubmit(){
    const email = this.loginForm.controls['email'].value;
    const password = this.loginForm.controls['password'].value;



    try{
      //await this.authService.login(email, password);
      this.router.navigate(['/']);
      this.snackBar.open('Sikeres bejelentkezés!', '', {
        duration: 1500
      });
    } catch (e){
      this.snackBar.open('Sikertelen bejelentkezés!', '', {
        duration: 1500
      });
    }
  }

}
