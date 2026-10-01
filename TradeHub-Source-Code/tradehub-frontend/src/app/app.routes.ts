import { Routes } from '@angular/router';
import { BuyerRegisterComponent } from './components/user/register/buyer-register/buyer-register.component';
import { SellerRegisterComponent } from './components/user/register/seller-register/seller-register.component';
import { LoginComponent } from './components/user/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import {HomeComponent} from './components/home/home.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },

  { path: 'register', redirectTo: 'register/buyer', pathMatch: 'full' },
  { path: 'register/buyer', component: BuyerRegisterComponent },
  { path: 'register/seller', component: SellerRegisterComponent },
  { path: 'login', component: LoginComponent},

  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: '**', redirectTo: 'home', pathMatch: 'full'}
];
