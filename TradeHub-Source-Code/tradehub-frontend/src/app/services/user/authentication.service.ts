import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserRegisterDto } from '../../dtos/user/user-register.dto';
import { UserLoginDto } from '../../dtos/user/user-login.dto';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TokenResponseDto } from '../../dtos/user/token-response.dto';
import { MessageResponseDto } from '../../dtos/message.dto';
import { tap, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationSerivce {
  authUrl: string = `${environment.backendUrl}/auth`

  constructor(private http: HttpClient) { }

  register(registerDto: UserRegisterDto) : Observable<MessageResponseDto> {
    return this.http.post<MessageResponseDto>(`${this.authUrl}/register`, registerDto);
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
