import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthenticationSerivce } from '../services/user/authentication.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthenticationSerivce, 
    private router: Router
  ) {}

  canActivate(): boolean {
    if (this.auth.isLoggedIn()) {
      console.log(`User is logged in, token ${this.auth.getToken()}`);
      return true;
    }

    this.router.navigate(['/login']);
    return false;
  }
}
