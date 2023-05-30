import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./register/register.component";
import {ConfirmEmailComponent} from "./confirm-email/confirm-email.component";
import {MainComponent} from "./main/main.component";


const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: '/main', pathMatch: 'full' },
  {path: 'main', component: MainComponent},
  { path: 'confirm-email', component: ConfirmEmailComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
