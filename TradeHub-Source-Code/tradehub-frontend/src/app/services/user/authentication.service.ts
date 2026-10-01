import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BuyerRegisterDto } from '../../dtos/user/buyer/buyer-register.dto';
import { SellerRegisterDto } from '../../dtos/user/seller/seller-register.dto';
import { UserLoginDto } from '../../dtos/user/user-login.dto';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { tap, map } from 'rxjs';
import {unwrap} from '../../utils/api-response.operators';
import {NotificationService} from '../success-dialog.service';
import {ApiResponse} from '../../dtos/api-response.dto';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  authUrl: string = `${environment.backendUrl}/auth`

  constructor(private http: HttpClient,
  private notificationService: NotificationService) { }

  private withoutEmptyFields<T extends object>(dto: T): Partial<T> {
    return Object.fromEntries(
      Object.entries(dto).filter(([, value]) => value !== "")
    ) as Partial<T>;
  }

  registerBuyer(registerDto: BuyerRegisterDto) : Observable<void> {
    return this.http.post<ApiResponse<string>>(`${this.authUrl}/register/buyer`,
      this.withoutEmptyFields(registerDto))
      .pipe(
        unwrap<string>(this.notificationService),
        tap((token: string) =>
          localStorage.setItem('authToken', token)),
        map(() => undefined)
      );
  }

  registerSeller(registerDto: SellerRegisterDto) : Observable<void> {
    return this.http.post<ApiResponse<string>>(`${this.authUrl}/register/seller`,
      this.withoutEmptyFields(registerDto))
      .pipe(
      unwrap<string>(this.notificationService),
      tap((token: string) =>
        localStorage.setItem('authToken', token)),
      map(() => undefined)
    );
  }

  login(loginDto: UserLoginDto) : Observable<void> {
    return this.http.post<ApiResponse<string>>(`${this.authUrl}/login`, loginDto)
      .pipe(
        unwrap<string>(this.notificationService),
        tap((token: string) =>
          localStorage.setItem('authToken', token)),
        map(() => undefined)
      );
  }

  logout() {
    localStorage.removeItem("authToken");
  }

  getToken() {
    return localStorage.getItem('authToken');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
