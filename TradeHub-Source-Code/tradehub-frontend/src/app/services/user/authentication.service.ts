import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BuyerRegisterDto } from '../../dtos/user/buyer-register.dto';
import { SellerRegisterDto } from '../../dtos/user/seller-register.dto';
import { UserLoginDto } from '../../dtos/user/user-login.dto';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TokenResponseDto } from '../../dtos/user/token-response.dto';
import { MessageResponseDto } from '../../dtos/message.dto';
import { tap, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  authUrl: string = `${environment.backendUrl}/auth`

  constructor(private http: HttpClient) { }

  private withoutEmptyFields<T extends object>(dto: T): Partial<T> {
    return Object.fromEntries(
      Object.entries(dto).filter(([, value]) => value !== "")
    ) as Partial<T>;
  }

  registerBuyer(registerDto: BuyerRegisterDto) : Observable<MessageResponseDto> {
    return this.http.post<MessageResponseDto>(`${this.authUrl}/register/buyer`,
      this.withoutEmptyFields(registerDto));
  }

  registerSeller(registerDto: SellerRegisterDto) : Observable<MessageResponseDto> {
    return this.http.post<MessageResponseDto>(`${this.authUrl}/register/seller`,
      this.withoutEmptyFields(registerDto));
  }

  login(loginDto: UserLoginDto) : Observable<MessageResponseDto> {
    return this.http.post<TokenResponseDto>(`${this.authUrl}/login`, loginDto)
      .pipe(
        tap(responseDto => {
          localStorage.setItem('authToken', responseDto.token);
        }),
        map(responseDto => responseDto.message)
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
