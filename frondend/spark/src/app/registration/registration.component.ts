import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../classes/user';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  private regForm = new FormGroup({
    name: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    rePassword: new FormControl('', Validators.required)
  })

  constructor(
    private snackBar: MatSnackBar,
    private userService: UserService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  async onSubmit(){
    const name: string = this.regForm.get('name').value;
    const email: string = this.regForm.get('email').value;
    const password: string = this.regForm.get('password').value;
    const rePassword: string = this.regForm.get('rePassword').value;

    if(!name || !email || !password || !rePassword) return;

    if(password !== rePassword){
      this.snackBar.open('Nem egyeznek a jelszavak!', '', {
        duration: 1500
      });
      return;
    }

    const user = new User();
    user.fullName = name;
    user.email = email;
    user.password = password;

    this.userService.resitration(user);

    await this.authService.login(user);

    this.snackBar.open('Sikeres regisztráció!', '', {
      duration: 1500
    });
    
    this.router.navigate(['']);
  }

}
