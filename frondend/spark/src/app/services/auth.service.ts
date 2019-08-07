import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Router } from '@angular/router';
import { User } from '../classes/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  public isLoggedIn: boolean = false;
  public user: User = null;

  constructor(
    private httpService: HttpService,
    private router: Router
  ) { }

  public async login(lUser: User): Promise<User>{
    try {
      const token = btoa(lUser.email + ':' + lUser.password);
      window.localStorage.setItem('token', token);
      const user: User = await this.httpService.post('user/login', lUser.email) as User;
      this.isLoggedIn = true;
      this.user = user;
      return Promise.resolve(user);
    } catch (e) {
      window.localStorage.setItem('token', '');
      return Promise.reject();
    }
  }

  public logout(){
    this.isLoggedIn = false;
    this.user = null;
    window.localStorage.setItem('token', '');
    this.router.navigate(['/']);
  }

  public loginWithToken(){
    const token = window.localStorage.getItem('token');
    const [username, password] = atob(token).split(':');
    const tokenUser = new User();
    tokenUser.email = username;
    tokenUser.password = password;
    this.login(tokenUser);
  }
  
}
