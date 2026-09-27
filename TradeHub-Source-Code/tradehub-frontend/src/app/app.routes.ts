import { Routes } from '@angular/router';
import { RegisterComponent } from './components/user/register/register.component';
import { LoginComponent } from './components/user/login/login.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },

  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LoginComponent},

  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: '**', redirectTo: 'home', pathMatch: 'full'}
];
