import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { UserLoginDto } from '../../../dtos/user/user-login.dto';
import { AuthenticationSerivce } from '../../../services/user/authentication.service';
import { NotificationService } from '../../../services/success-dialog.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [MatButtonModule, MatIconModule, MatFormFieldModule,
    MatInputModule, FormsModule, RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  hide = signal(true);
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
      event.stopPropagation();
  }

  loginDto: UserLoginDto = {"username": "", "password": ""};

  constructor(private service: AuthenticationSerivce, 
    private notificationDialog: NotificationService,
    private router: Router
  ) { }

  onSubmit() {
    this.service.login(this.loginDto).subscribe({
      next: messageDto => {
        console.log("Login success: ", messageDto.developerMessage);
        this.router.navigate(['/home']);
        this.notificationDialog.showSuccess(messageDto);
      },
      error: err => {
        console.error("Login error:", err);
      },
      complete: () => {
        console.log("Observable completed");
      }
    });
  }
}
