import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Message } from '../classes/message';
import { User } from '../classes/user';
import { Match } from '../classes/match';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  private url: string = 'match'
  private me: User;

  constructor(
    private httpService: HttpService,
  ) { }

  public sendMessage(message: Message, match: Match){
    this.httpService.post<Message>(this.url + '/' + match.id + '/message' , message).catch();
  }

}
