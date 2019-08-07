import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { User } from '../classes/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url: string = 'user';

  constructor(
    private httpService: HttpService
  ) { }

  public login(user: User){
    this.httpService.post<User>(this.url + '/login', user).catch(e => {
      console.log(e)
    });
  }

  public resitration(user: User){
    this.httpService.post<User>(this.url + '/register', user).catch();
  }

  public equals(userA: User, userB: User): boolean{
      if(!userA || !userB) return false;
      if(userA.id !== userB.id) return false;
      if(userA.fullName !== userB.fullName) return false;
      if(userA.password !== userB.password) return false;
      if(userA.email !== userB.email) return false;
      if(userA.description !== userB.description) return false;
      if(userA.lastOnline !== userB.lastOnline) return false;
      if(userA.gender !== userB.gender) return false;
      if(userA.sexuality !== userB.sexuality) return false;
      if(userA.role !== userB.role) return false;
      return true;
  }
}
