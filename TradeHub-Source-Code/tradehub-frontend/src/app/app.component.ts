import { Component } from '@angular/core';
import { environment } from '../environments/environment';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  logoUrl: string = `${environment.imagesUrl}/tradehub-logo.jpg`;

  protected title = 'tradehub-frontend';
}
