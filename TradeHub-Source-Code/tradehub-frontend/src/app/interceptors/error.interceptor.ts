import { inject } from '@angular/core';
import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from '../components/dialog/error-dialog/error-dialog.component';
import { catchError, throwError } from 'rxjs';

export const ErrorInterceptor: HttpInterceptorFn = (req, next) => {
  console.log("Error interceptor called!");
  const dialog = inject(MatDialog);

  console.log("Error detected. ")

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let message = 'An unexpected error occurred.';

      if (error.status === 0) {
        message = 'Network error: unable to reach the server (CORS/connection issue).';
      } else if (error.error instanceof ErrorEvent) {
        message = `Client-side error: ${error.error.message}`;
      } else if (typeof error.error === 'string') {
        message = error.error; 
      } else if (error.error?.message) {
        message = error.error.message; 
      } else {
        message = `Server returned ${error.status} – ${error.statusText}`;
      }

      dialog.open(ErrorDialogComponent, { data: { message }, disableClose: true });
      return throwError(() => error);
    })
  );
};
