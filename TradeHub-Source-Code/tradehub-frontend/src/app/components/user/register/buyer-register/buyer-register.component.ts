import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {Router, RouterLink} from '@angular/router';
import { BuyerRegisterDto } from '../../../../dtos/user/buyer-register.dto';
import { AuthenticationService } from '../../../../services/user/authentication.service';
import { NotificationService } from '../../../../services/success-dialog.service';

@Component({
  selector: 'app-buyer-register',
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, FormsModule, RouterLink],
  templateUrl: './buyer-register.component.html',
  styleUrl: '../register-form.css'
})
export class BuyerRegisterComponent {
  hide = signal(true);
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  registerDto: BuyerRegisterDto = {
    username: "", email: "", phone: "", password: "",
    fullName: "", shippingStreet: "", shippingCity: "",
    shippingPostalCode: "", shippingCountry: ""
  };

  constructor(private service: AuthenticationService,
    private notificationDialog: NotificationService,
    private router: Router
  ) { }

  onSubmit() {
    this.service.registerBuyer(this.registerDto).subscribe(responseDto => {
      this.notificationDialog.showSuccess(responseDto);

      this.router.navigate(["/login"]);
    });
  }
}
