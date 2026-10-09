import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { AuthenticationService } from '../../../services/user/authentication.service';
import { UserLoginDto } from '../../../dtos/user/user-login.dto';

@Component({
  imports: [FormsModule, MatButtonModule, MatFormFieldModule, MatInputModule, MatIconModule],
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrl: './admin-login.component.css',
})
export class AdminLoginComponent {
  credentials: UserLoginDto = { username: '', password: '' };
  hidePassword = signal(true);
  error = signal('');

  constructor(private auth: AuthenticationService, private router: Router) {}

  login() {
    this.error.set('');
    this.auth.login(this.credentials).subscribe({
      next: () => {
        if (this.auth.isAdmin()) {
          this.router.navigate(['/shops']);
        } else {
          this.auth.logout();
          this.credentials.password = '';
          this.error.set('This account does not have admin privileges.');
        }
      },
      error: () => {
        this.credentials.password = '';
      }
    });
  }
}
