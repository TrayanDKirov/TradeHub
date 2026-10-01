import {ChangeDetectorRef, Component} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../../services/user/authentication.service';
import {UserService} from '../../services/user/user.service';
import {UserPreviewDto} from '../../dtos/user/user-preview.dto';

@Component({
  selector: 'app-home',
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  userPreview: UserPreviewDto = {username: "username"};

  constructor(private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthenticationService,
    private cdr: ChangeDetectorRef
  ) { }

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
