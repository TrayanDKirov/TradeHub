import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {Router, RouterLink} from '@angular/router';
import { SellerRegisterDto } from '../../../../dtos/user/seller-register.dto';
import { AuthenticationService } from '../../../../services/user/authentication.service';
import { NotificationService } from '../../../../services/success-dialog.service';

@Component({
  selector: 'app-seller-register',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, FormsModule, RouterLink],
  templateUrl: './seller-register.component.html',
  styleUrl: '../register-form.css'
})
export class SellerRegisterComponent {
  hide = signal(true);
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  registerDto: SellerRegisterDto = {
    username: "", email: "", phone: "", password: "",
    displayName: "", contactEmail: "", contactPhone: "", description: ""
  };

  constructor(private service: AuthenticationService,
    private notificationDialog: NotificationService,
    private router: Router
  ) { }

  onSubmit() {
    this.service.registerSeller(this.registerDto).subscribe(responseDto => {
      this.notificationDialog.showSuccess(responseDto);

      this.router.navigate(["/login"]);
    });
  }
}
