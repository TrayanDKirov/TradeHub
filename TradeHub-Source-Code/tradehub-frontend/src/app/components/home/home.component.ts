import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../../services/user/authentication.service';

@Component({
  selector: 'app-home',
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  constructor(private router: Router,
    private route: ActivatedRoute,
    private authService: AuthenticationService
  ) { }

  logout() {
    this.authService.logout();

    this.router.navigate([`/login`]);
  }
}
