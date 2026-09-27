import { HttpInterceptorFn, HttpErrorResponse, HttpRequest, HttpHandlerFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { AuthenticationSerivce } from '../services/user/authentication.service';

export const AuthInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn) => {
  console.log("Auth interceptor called!");
  const authService = inject(AuthenticationSerivce);
  const router = inject(Router);

  const token = authService.getToken();
  let cloned = req;

  if (token) {
    cloned = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }

  return next(cloned).pipe(
    catchError((error: HttpErrorResponse) => {
      console.log(`Request falled error code: ${error.status}`);
      if (error.status === 401 || error.status === 403) {
        authService.logout();
        router.navigate(['/login'], { queryParams: { sessionExpired: true } });
      }
      return throwError(() => error);
    })
  );
};
