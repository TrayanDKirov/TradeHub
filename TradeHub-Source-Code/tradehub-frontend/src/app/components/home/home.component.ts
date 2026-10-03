import {ChangeDetectorRef, Component} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthenticationService } from '../../services/user/authentication.service';
import {UserService} from '../../services/user/user.service';
import {UserPreviewDto} from '../../dtos/user/user-preview.dto';

@Component({
  selector: 'app-home',
  imports: [MatIconModule, MatButtonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  userPreview: UserPreviewDto = {username: "username", role: ""};

  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private cdr: ChangeDetectorRef
  ) { }

  openProfile() {
    const role = this.userPreview.role?.toLowerCase();

    if (role === "buyer") {
      this.router.navigate(["/profile/buyer"]);
    } else if (role === "seller") {
      this.router.navigate(["/profile/seller"]);
    }
  }

  logout() {
    this.authService.logout();

    this.router.navigate([`/login`]);
  }

  ngOnInit() {
    this.userService.getUserPreview().subscribe(
      userPreview => {
        this.userPreview = userPreview;
        this.cdr.markForCheck();
      }
    );
  }
}
