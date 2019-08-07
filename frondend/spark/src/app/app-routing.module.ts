import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { MainPageComponent } from './main-page/main-page.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { MessageComponent } from './message/message.component';
import { MatchComponent } from './match/match.component';
import { RatingPictureComponent } from './rating-picture/rating-picture.component';

const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'edit-profile', component: EditProfileComponent},
  {path: 'message', component: MessageComponent},
  {path: 'match', component: MatchComponent},
  {path: 'rate', component: RatingPictureComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
