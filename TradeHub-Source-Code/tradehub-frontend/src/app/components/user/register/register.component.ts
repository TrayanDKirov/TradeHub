import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { UserRegisterDto } from '../../../dtos/user/user-register.dto';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AuthenticationSerivce } from '../../../services/user/authentication.service';
import { NotificationService } from '../../../services/success-dialog.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, 
    MatIconModule, FormsModule, RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  hide = signal(true);
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
      event.stopPropagation();
  }

  registerDto: UserRegisterDto = {"username": "", "password": ""};

  constructor(private service: AuthenticationSerivce,
    private notificationDialog: NotificationService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  onSubmit() {
    this.service.register(this.registerDto).subscribe(responseDto => {
      this.notificationDialog.showSuccess(responseDto);

      this.router.navigate(["/login"], {relativeTo: this.route});
    });
  }
}
