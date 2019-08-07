import { Component, OnInit } from '@angular/core';
import { Match } from '../classes/match';
import { HttpService } from '../services/http.service';
import { User } from '../classes/user';
import { UserService } from '../services/user.service';
import { Message } from '../classes/message';
import { FormGroup, FormControl } from '@angular/forms';
import { MatchService } from '../services/match.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  private messageForm = new FormGroup({
    message: new FormControl('')
  })

  private matches: Match[];
  private me: User;
  private actualMessages: Message[];
  private actualUser: User;
  private actualMach: Match;

  private interval;

  constructor(
    private httpService: HttpService,
    private userService: UserService,
    private matchService: MatchService
  ) { }

  async ngOnInit() {
    
    this.init().then(() => {
      this.actualMessages = this.matches[0].messages;
      this.actualMach = this.matches[0];
      this.loadMessage(this.matches[0]);
    })
    this.startTimer();
    
  }

  async init(){
    this.me = await this.httpService.get<User>('user/me');
    this.me.password = '$2a$10$feZYzOIejt/Odd/lCNqlN.c7NrRe77ktkFrAjByoW8POJwCUgU5MS'; // tesztelésre
    this.matches = await this.httpService.get<Match[]>('match/me').then((matches: Match[]) => {
      if(this.actualMach){
        for(let match of matches){
          if(match.id === this.actualMach.id){
            this.loadMessage(match);
            break;
          }
        }
      }
      return matches
    });
  }

  getFullName(match: Match): string{
    if(this.userService.equals(this.me, match.userA)){
      return match.userB.fullName;
    } else {
      return match.userA.fullName;
    }
  }

  getLastMessageDate(match: Match): Date{
    if(match.messages.length === 0) return;
    return match.messages[match.messages.length - 1].sendTime;
  }

  getLastMessage(match: Match): string{
    let size = match.messages.length;
    if(size === 0) return;
    let lastMess: string = match.messages[size - 1].message;
    lastMess = lastMess.split('\n').join();
    return (lastMess.length > 30 ? (lastMess.substring(0, 30) + ' ....') : lastMess);
  }

  messageSend(){
    let mess: string= this.messageForm.controls['message'].value;
    mess = mess.trim();
    if(!mess) return;

    let message = new Message();
    message.message = mess;

    console.log(message)

    this.matchService.sendMessage(message, this.actualMach);
    this.messageForm.controls['message'].setValue('');

    this.init();
  }

  getSide(message: Message){
    if(this.userService.equals(this.me, message.sender)){
      return "right-message-container";
    } else {
      return "left-message-container";
    }
  }

  loadMessage(match: Match){
    if(this.userService.equals(this.me, match.userA)){
      this.actualUser =  match.userB;
    } else {
      this.actualUser =  match.userA;
    }
    this.actualMessages = match.messages;
    this.actualMach = match;
  }

  activePlayer(match: Match): boolean{
    if(this.userService.equals(this.actualUser, match.userA)){
      return true;
    } else if(this.userService.equals(this.actualUser, match.userB)) {
      return true;
    } else {
      return false;
    }
  }

  startTimer() {
    this.interval = setInterval(() => {
        this.init()
    },10000)
  }

}

